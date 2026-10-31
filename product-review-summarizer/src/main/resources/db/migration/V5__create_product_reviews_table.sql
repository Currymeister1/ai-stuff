CREATE TABLE product_reviews
(
    id           INTEGER PRIMARY KEY,
    product_id   INTEGER REFERENCES products (id),
    stars        VARCHAR(255) NOT NULL,
    reviewer_age INT,
    title        VARCHAR(500),
    text         TEXT
);
