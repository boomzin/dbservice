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
      AND NOT EXISTS (
          SELECT 1
          FROM orders o2
          WHERE o2.cart_id = c.id
            AND o2.status IN ('ACTIVE', 'PAID')
      )
) AS combined
GROUP BY product_id;

ALTER TYPE cart_status ADD VALUE IF NOT EXISTS 'ARCHIVED';