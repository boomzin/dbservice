package ru.mediatel.icc.dbservice.model.cart;


import ru.mediatel.icc.dbservice.common.data.PagedResult;
import ru.mediatel.icc.dbservice.rest.cart.CartDetailsDto;

import java.util.Map;
import java.util.UUID;

public interface CartRepository {
    void create(Cart cart);

    void update(Cart cart);

    Cart findById(UUID cartId);

    PagedResult<Cart> search(Map<String, String> apiParams);

    void delete(UUID cartId);

    CartDetailsDto getCartDetails(UUID cartId);
}
