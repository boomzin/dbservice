package ru.mediatel.icc.dbservice.model.cart;


import ru.mediatel.icc.dbservice.common.data.PagedResult;

import java.util.Map;
import java.util.UUID;

public interface CartRepository {
    void create(Cart cart);

    void update(Cart cart);

    Cart findById(UUID cartId);

    PagedResult<Cart> search(Map<String, String> apiParams);

    void delete(UUID cartId);
}
