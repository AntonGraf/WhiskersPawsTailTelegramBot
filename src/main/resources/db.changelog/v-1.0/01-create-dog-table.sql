CREATE TABLE dog
(
    id BIGINT PRIMARY KEY,
    name TEXT,
    age INTEGER CHECK ( age > 0 ),
    description TEXT,
    photo bytea
);


