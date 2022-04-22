CREATE TABLE IF NOT EXISTS product_backlog_item (
    item_id INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    description VARCHAR(4096),
    story_point INTEGER NOT NULL,
    memo VARCHAR(4096),
    updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(item_id)
);

CREATE TABLE IF NOT EXISTS task (
    task_id INTEGER NOT NULL AUTO_INCREMENT,
    item_id INTEGER NOT NULL,
    name VARCHAR(128) NOT NULL,
    description VARCHAR(4096),
    estimate_time INTEGER NOT NULL,
    status ENUM ('todo', 'doing', 'done') NOT NULL,
    memo VARCHAR(4096),
    updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(task_id),
    FOREIGN KEY (item_id) 
        REFERENCES product_backlog_item(item_id)
        ON DELETE CASCADE
);