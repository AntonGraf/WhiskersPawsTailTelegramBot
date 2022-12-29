CREATE TABLE dog
(
    id BIGINT PRIMARY KEY,
    fullName TEXT,
    age INTEGER CHECK ( age > 0 ),
    description TEXT,
    photo bytea
);

CREATE TABLE photo (
    data bytea
);

ALTER TABLE photo ALTER COLUMN data SET STORAGE EXTERNAL;

INSERT INTO photo VALUES ( pg_read_binary_file ('/Users/sergej/Downloads/dog1.jpeg')::bytea);