-- Runs automatically the first time the MySQL container starts
-- (files in /docker-entrypoint-initdb.d/ are executed on first init only).

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users (username, email, full_name, active) VALUES
    ('jdoe',   'jdoe@example.com',   'John Doe',   TRUE),
    ('asmith', 'asmith@example.com', 'Alice Smith', TRUE),
    ('bwhite', 'bwhite@example.com', 'Bob White',  FALSE);
