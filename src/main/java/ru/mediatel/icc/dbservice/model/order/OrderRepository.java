package ru.mediatel.icc.dbservice.model.order;


import ru.mediatel.icc.dbservice.common.data.PagedResult;
import ru.mediatel.icc.dbservice.db.generated.enums.OrderStatus;
import ru.mediatel.icc.dbservice.model.cart.Cart;

import java.util.Map;
import java.util.UUID;

public interface OrderRepository {
    void create(Order order);

    void update(Order order);

    Order findById(UUID orderId);

    PagedResult<Order> search(Map<String, String> apiParams);

    void delete(UUID orderId);

    Order createPendingOrderFromCart(Cart cart);

    void copyItemsFromCart(UUID orderId, UUID cartId);

    void setOrderStatus(UUID orderId, OrderStatus status);

    void decrementStock(Map<UUID, Integer> items);

    void incrementStock(Map<UUID, Integer> items);

    Map<UUID, Integer> getOrderItems(UUID orderId);
}
