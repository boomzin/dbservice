CREATE OR REPLACE VIEW product_reservations AS
SELECT
    product_id,
    SUM(quantity) AS reserved_quantity
FROM (
    SELECT
        pio.product_id,
        pio.quantity
    FROM products_in_orders pio
    JOIN orders o ON o.id = pio.order_id
    WHERE o.status IN ('ACTIVE', 'PAID')

    UNION ALL

    SELECT
        pic.product_id,
        pic.quantity
    FROM products_in_carts pic
    JOIN carts c ON c.id = pic.cart_id
    WHERE c.status = 'CONFIRMED'
) AS combined
GROUP BY product_id;
