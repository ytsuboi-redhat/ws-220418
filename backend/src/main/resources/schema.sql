CREATE TABLE IF NOT EXISTS product_backlog_item (
    item_id INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    description VARCHAR(4096),
    story_point INTEGER NOT NULL,
    memo VARCHAR(4096),
    updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(item_id)
);