CREATE TABLE dog
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(256),
    age TEXT,
    description TEXT,
    file_path TEXT,
    size BIGINT DEFAULT 1024,
    type TEXT,
    photo oid
);


