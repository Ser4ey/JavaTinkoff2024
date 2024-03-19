CREATE TABLE chat
(
    chat_id BIGINT PRIMARY KEY
);

CREATE TABLE link
(
    id          SERIAL PRIMARY KEY,
    url         VARCHAR(255) UNIQUE NOT NULL,
    last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE chat_link
(
    chat_id BIGINT,
    link_id INTEGER,
    PRIMARY KEY (chat_id, link_id),
    FOREIGN KEY (chat_id) REFERENCES chat (chat_id) ON DELETE CASCADE,
    FOREIGN KEY (link_id) REFERENCES link (id) ON DELETE CASCADE
);
