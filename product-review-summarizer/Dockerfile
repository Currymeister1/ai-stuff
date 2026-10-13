FROM amazoncorretto:25-alpine-jdk

WORKDIR /app

COPY . .

RUN ./gradlew clean build -x test

EXPOSE 8080

CMD ["java", "-jar", "./build/libs/product-review-summarizer-0.0.1-SNAPSHOT.jar"]

