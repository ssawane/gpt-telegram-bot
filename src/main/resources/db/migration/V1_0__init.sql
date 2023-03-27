CREATE TABLE IF NOT EXISTS "user" (
    id INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    tg_user_id BIGINT NOT NULL UNIQUE,
    username VARCHAR(50),
    firstname VARCHAR(100),
    lastname VARCHAR(100),
    active_session BOOLEAN,
    tokens_left INT
);

CREATE TABLE IF NOT EXISTS query (
    id INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    question VARCHAR(200) NOT NULL,
    answer VARCHAR,
    user_id INT REFERENCES "user" (id) NOT NULL,
    used_tokens INT,
    received_at TIMESTAMP
);