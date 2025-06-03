CREATE TABLE users
(
    id         UUID PRIMARY KEY,
    email      VARCHAR(255) NOT NULL UNIQUE,
    full_name  VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT now()
);

CREATE TABLE products
(
    id         UUID PRIMARY KEY,
    name       VARCHAR(255)   NOT NULL,
    category   VARCHAR(100),
    price      NUMERIC(10, 2) NOT NULL,
    created_at TIMESTAMP      NOT NULL DEFAULT now()
);

CREATE TABLE orders
(
    id         UUID PRIMARY KEY,
    user_id    UUID           NOT NULL REFERENCES users (id),
    product_id UUID           NOT NULL REFERENCES products (id),
    quantity   INT            NOT NULL,
    total      NUMERIC(10, 2) NOT NULL,
    status     VARCHAR(20)    NOT NULL CHECK (status IN ('created', 'completed', 'cancelled')),
    created_at TIMESTAMP      NOT NULL DEFAULT now()
);
