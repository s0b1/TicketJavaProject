CREATE TABLE place (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       address VARCHAR(255) NOT NULL
);

CREATE TABLE event (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       event_date DATE NOT NULL,
                       place_id BIGINT REFERENCES place(id)
);

CREATE TABLE customer (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL,
                          phone VARCHAR(50) NOT NULL
);

CREATE TABLE ticket (
                        id BIGSERIAL PRIMARY KEY,
                        cost DOUBLE PRECISION NOT NULL,
                        number INTEGER NOT NULL,
                        status VARCHAR(10) NOT NULL,
                        customer_id BIGINT REFERENCES customer(id),
                        event_id BIGINT REFERENCES event(id)
);
