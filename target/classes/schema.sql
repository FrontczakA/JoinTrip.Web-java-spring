CREATE TABLE IF NOT EXISTS roles (
                                     id SERIAL PRIMARY KEY,
                                     name VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     login VARCHAR(255) UNIQUE,
                                     email VARCHAR(255) UNIQUE,
                                     password VARCHAR(255),
                                     role_id BIGINT REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS trips (
                                     id SERIAL PRIMARY KEY,
                                     title VARCHAR(255),
                                     photo_url TEXT,
                                     description TEXT,
                                     creation_time TIMESTAMP,
                                     update_time TIMESTAMP
);

CREATE TABLE IF NOT EXISTS event (
                                     id SERIAL PRIMARY KEY,
                                     name VARCHAR(255),
                                     start_time TIMESTAMP,
                                     end_time TIMESTAMP,
                                     photo_url TEXT,
                                     create_date TIMESTAMP,
                                     update_date TIMESTAMP,
                                     trip_id BIGINT REFERENCES trips(id),
                                     description TEXT
);

CREATE TABLE IF NOT EXISTS comments (
                                        id SERIAL PRIMARY KEY,
                                        content TEXT,
                                        likes INT,
                                        trip_id BIGINT REFERENCES trips(id),
                                        creation_time TIMESTAMP,
                                        user_id BIGINT REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS comment_likes (
                                             comment_id BIGINT REFERENCES comments(id),
                                             user_id BIGINT REFERENCES users(id),
                                             PRIMARY KEY (comment_id, user_id)
);

CREATE TABLE IF NOT EXISTS user_roles (
                                          user_id BIGINT REFERENCES users(id),
                                          role_id BIGINT REFERENCES roles(id),
                                          PRIMARY KEY (user_id, role_id)
);
