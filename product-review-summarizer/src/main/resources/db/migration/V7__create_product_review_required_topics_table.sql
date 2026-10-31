CREATE TABLE product_review_required_topics
(
    review_id INTEGER NOT NULL REFERENCES product_reviews (id),
    topic_id  INTEGER NOT NULL REFERENCES required_topics (id),
    PRIMARY KEY (review_id, topic_id)
);
