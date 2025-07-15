package ru.mediatel.icc.dbservice.db.repository;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.mediatel.icc.dbservice.model.productincart.ProductInCart;
import ru.mediatel.icc.dbservice.model.productincart.ProductInCartRepository;

import java.util.List;
import java.util.UUID;

import static ru.mediatel.icc.dbservice.db.generated.Tables.PRODUCTS_IN_CARTS;


@Repository
public class JooqProductInCartRepository implements ProductInCartRepository {
    private final DSLContext db;

    public JooqProductInCartRepository(DSLContext db) {
        this.db = db;
    }

    @Override
    public void addOrUpdate(ProductInCart productInCart) {
        db.insertInto(PRODUCTS_IN_CARTS)
                .set(PRODUCTS_IN_CARTS.CART_ID, productInCart.getCartId())
                .set(PRODUCTS_IN_CARTS.PRODUCT_ID, productInCart.getProductId())
                .set(PRODUCTS_IN_CARTS.QUANTITY, productInCart.getQuantity())
                .onConflict(PRODUCTS_IN_CARTS.CART_ID, PRODUCTS_IN_CARTS.PRODUCT_ID)
                .doUpdate()
                .set(PRODUCTS_IN_CARTS.QUANTITY, PRODUCTS_IN_CARTS.QUANTITY.add(productInCart.getQuantity()))
                .execute();
    }

    @Override
    public void remove(ProductInCart productInCart) {
        db.deleteFrom(PRODUCTS_IN_CARTS)
                .where(PRODUCTS_IN_CARTS.CART_ID.eq(productInCart.getCartId())
                        .and(PRODUCTS_IN_CARTS.PRODUCT_ID.eq(productInCart.getProductId())))
                .execute();
    }

    @Override
    public List<ProductInCart> findByCartId(UUID cartId) {
        return db.selectFrom(PRODUCTS_IN_CARTS)
                .where(PRODUCTS_IN_CARTS.CART_ID.eq(cartId))
                .fetch()
                .map(record ->
                        new ProductInCart(record.getCartId(), record.getProductId(), record.getQuantity()));
    }
}
