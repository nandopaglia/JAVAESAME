CREATE TABLE IF NOT EXISTS users (
    id IDENTITY PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS documents (
    id IDENTITY PRIMARY KEY,
    title VARCHAR(255),
    content CLOB,
    created_date TIMESTAMP
);

INSERT INTO users (username, password, role) VALUES
    ('admin', 'admin', 'ADMIN'),
    ('user1', 'user1', 'EMPLOYEE');
