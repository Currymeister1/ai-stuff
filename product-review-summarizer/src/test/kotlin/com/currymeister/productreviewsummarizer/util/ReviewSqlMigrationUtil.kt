package com.currymeister.productreviewsummarizer.util

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

/**
 * One-off utility that reads the JSON review files and appends the equivalent
 * INSERT statement to the V6 Flyway migration.
 *
 * It is intentionally isolated: it has its own DTOs and does not depend on any
 * other class in the application. It never connects to the database.
 *
 * Run with:
 * ./gradlew test --tests "com.currymeister.productreviewsummarizer.util.ReviewSqlMigrationUtilTest"
 */
object ReviewSqlMigrationUtil {

    private const val REVIEWS_RESOURCE = "reviews.json"
    private const val REVIEWS_RESULT_RESOURCE = "reviews-result.json"
    private const val MIGRATION_PATH = "src/main/resources/db/migration/V6__load_reviews.sql"
    private const val BEGIN_MARKER = "-- BEGIN generated review data"
    private const val END_MARKER = "-- END generated review data"

    /**
     * Malformed generated result for review 40 contains the beginning of the
     * review text inside review_title, separated by this delimiter.
     */
    private const val MALFORMED_TITLE_DELIMITER = "',' review_text':'"

    private val json = Json { ignoreUnknownKeys = true }

    @JvmStatic
    fun main(args: Array<String>) {
        val rowCount = appendReviewsToMigration()
        println("Appended $rowCount review rows to $MIGRATION_PATH")
    }

    /**
     * Reads both JSON files, builds the INSERT statement, and appends it to the
     * V6 migration file. Returns the number of review rows written.
     */
    fun appendReviewsToMigration(): Int {
        val rows = loadRows()
        val sql = buildInsertStatement(rows)
        val block = "$BEGIN_MARKER\n$sql\n$END_MARKER"

        val migrationFile = File(projectRoot(), MIGRATION_PATH)
        migrationFile.parentFile.mkdirs()
        val existing = if (migrationFile.isFile) migrationFile.readText() else ""
        migrationFile.writeText(mergeWithExisting(existing, block))

        return rows.size
    }

    private fun loadRows(): List<ReviewRow> {
        val reviewFile = readResource(REVIEWS_RESOURCE)
        val resultFile = readResource(REVIEWS_RESULT_RESOURCE)

        val reviews = json.decodeFromString<ReviewsFile>(reviewFile)
        val resultsById = json.decodeFromString<ReviewResultsFile>(resultFile)
            .reviews
            .associateBy { it.reviewId }

        return reviews.reviews
            .flatMap { product ->
                product.reviews.map { review ->
                    val result = resultsById[review.reviewId]
                    val repaired = repairMalformedResult(result)

                    ReviewRow(
                        reviewId = review.reviewId,
                        productId = product.productId,
                        reviewStars = review.reviewStars,
                        reviewerAge = review.reviewerAge,
                        title = repaired?.title,
                        text = repaired?.text,
                    )
                }
            }
            .sortedBy { it.reviewId }
    }

    /**
     * Review 40's generated title embeds the start of its review text. Split it
     * back out and prepend it to the actual review text. Reviews without a
     * generated result keep null title/text.
     */
    private fun repairMalformedResult(result: GeneratedResult?): GeneratedResult? {
        if (result == null) return null
        if (!result.title.contains(MALFORMED_TITLE_DELIMITER)) return result

        val title = result.title.substringBefore(MALFORMED_TITLE_DELIMITER)
        val embeddedText = result.title.substringAfter(MALFORMED_TITLE_DELIMITER)
        val text = listOf(embeddedText, result.text)
            .filter { it.isNotBlank() }
            .joinToString(" ")

        return GeneratedResult(reviewId = result.reviewId, title = title, text = text)
    }

    private fun buildInsertStatement(rows: List<ReviewRow>): String = buildString {
        appendLine("INSERT INTO product_reviews (id, product_id, stars, reviewer_age, title, text)")
        appendLine("VALUES")
        rows.forEachIndexed { index, row ->
            val separator = if (index == rows.lastIndex) ";" else ","
            append("    (")
            append(row.reviewId)
            append(", ")
            append(row.productId)
            append(", ")
            append("'${row.reviewStars}'")
            append(", ")
            append(row.reviewerAge)
            append(", ")
            append(sqlValue(row.title))
            append(", ")
            append(sqlValue(row.text))
            append(")")
            append(separator)
            appendLine()
        }
    }

    private fun mergeWithExisting(existing: String, block: String): String {
        val beginIndex = existing.indexOf(BEGIN_MARKER)
        val endIndex = existing.indexOf(END_MARKER)

        if (beginIndex >= 0 && endIndex > beginIndex) {
            val blockEnd = endIndex + END_MARKER.length
            return existing.substring(0, beginIndex) + block + existing.substring(blockEnd)
        }

        if (existing.isBlank()) return block + "\n"
        return existing.trimEnd() + "\n\n" + block + "\n"
    }

    private fun sqlValue(value: String?): String =
        if (value == null) "NULL" else "'${value.replace("'", "''")}'"

    private fun readResource(name: String): String {
        val inputStream = ReviewSqlMigrationUtil::class.java.classLoader.getResourceAsStream(name)
            ?: error("Resource '$name' not found on the classpath")
        return inputStream.bufferedReader().use { it.readText() }
    }

    private fun projectRoot(): File =
        generateSequence(File(System.getProperty("user.dir"))) { it.parentFile }
            .firstOrNull { File(it, "settings.gradle.kts").isFile }
            ?: error("Could not locate project root from ${System.getProperty("user.dir")}")

    private data class ReviewRow(
        val reviewId: Int,
        val productId: Int,
        val reviewStars: Int,
        val reviewerAge: Int,
        val title: String?,
        val text: String?,
    )

    @Serializable
    private data class ReviewsFile(
        val reviews: List<ProductReviews>,
    )

    @Serializable
    private data class ProductReviews(
        @SerialName("product_id") val productId: Int,
        val reviews: List<ReviewEntry>,
    )

    @Serializable
    private data class ReviewEntry(
        @SerialName("review_id") val reviewId: Int,
        @SerialName("reviewer_age") val reviewerAge: Int,
        @SerialName("review_stars") val reviewStars: Int,
    )

    @Serializable
    private data class ReviewResultsFile(
        val reviews: List<GeneratedResult>,
    )

    @Serializable
    private data class GeneratedResult(
        @SerialName("review_id") val reviewId: Int,
        @SerialName("review_title") val title: String,
        @SerialName("review_text") val text: String,
    )
}

class ReviewSqlMigrationUtilTest {

    @Test
    fun `appends all reviews to the V6 migration`() {
        val rowCount = ReviewSqlMigrationUtil.appendReviewsToMigration()

        assertEquals(81, rowCount)
    }
}
