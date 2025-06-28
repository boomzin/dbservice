CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

ALTER DATABASE db_service SET search_path TO db_service;

CREATE TYPE order_status AS ENUM ('ACTIVE', 'EXPIRED', 'CANCELLED', 'PENDING', 'SUSPENDED', 'PAID', 'DONE');
CREATE TYPE cart_status AS ENUM ('NEW', 'CONFIRMED');
CREATE TYPE claim_status AS ENUM ('NEW', 'PROCESS', 'DONE');
CREATE TYPE interaction_status AS ENUM ('RECALL', 'CANCEL_ORDER', 'RESCHEDULE');

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    phone INT NOT NULL UNIQUE,
    email VARCHAR(100) UNIQUE,
    tg VARCHAR(100) UNIQUE,
    description TEXT
);

CREATE TABLE products (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description TEXT
);

CREATE TABLE carts (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES users(id),
    status cart_status NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    customer_comment TEXT
);

CREATE TABLE products_in_carts (
    cart_id UUID REFERENCES carts(id) ON DELETE CASCADE,
    product_id UUID REFERENCES products(id) ON DELETE CASCADE,
    quantity INT NOT NULL,
    CONSTRAINT unique_cart_product UNIQUE (cart_id, product_id)
);

CREATE TABLE orders (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES users(id),
    status order_status NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    customer_comment TEXT,
    cart_id UUID NOT NULL REFERENCES carts(id)
);

CREATE INDEX idx_orders_status ON orders(status);

CREATE TABLE products_in_orders (
    order_id UUID REFERENCES orders(id) ON DELETE CASCADE,
    product_id UUID REFERENCES products(id) ON DELETE CASCADE,
    quantity INT NOT NULL,
    CONSTRAINT unique_order_product UNIQUE (order_id, product_id)
);

CREATE TABLE claims (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    order_id UUID REFERENCES orders(id) ON DELETE CASCADE,
    status claim_status NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    customer_comment TEXT
);

CREATE TABLE interactions (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    cart_id UUID REFERENCES carts(id),
    order_id UUID REFERENCES orders(id),
    status interaction_status NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    customer_comment TEXT,
    CHECK (
        (cart_id IS NOT NULL AND order_id IS NULL)
        OR (cart_id IS NULL AND order_id IS NOT NULL)
    )
);

CREATE VIEW product_reservations AS
SELECT
    product_id,
    SUM(quantity) AS reserved_quantity
FROM products_in_orders
JOIN orders ON orders.id = products_in_orders.order_id
WHERE orders.status IN ('ACTIVE', 'PAID')
GROUP BY product_id;

CREATE INDEX idx_products_in_orders_product_id ON products_in_orders(product_id);
CREATE INDEX idx_products_in_carts_product_id ON products_in_carts(product_id);

CREATE OR REPLACE FUNCTION set_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_set_updated_at_orders
BEFORE UPDATE ON orders
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_set_updated_at_carts
BEFORE UPDATE ON carts
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_set_updated_at_claims
BEFORE UPDATE ON claims
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_set_updated_at_interactions
BEFORE UPDATE ON interactions
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();
