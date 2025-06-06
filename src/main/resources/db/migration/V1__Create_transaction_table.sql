CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,

    valor       BIGINT NOT NULL,

    hora_data   TIMESTAMP WITH TIME ZONE NOT NULL
);