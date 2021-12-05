create table if not exists produced_entity (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    date_time TIMESTAMP NOT NULL
);

create table if not exists consumed_entity (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    date_time TIMESTAMP NOT NULL
);