package ru.mediatel.icc.dbservice.model.productincart;


import java.util.List;
import java.util.UUID;

public interface ProductInCartRepository {

    void addOrUpdate(ProductInCart productInCart);

    void remove(ProductInCart productInCart);

    List<ProductInCart> findByCartId(UUID cartId);
}
