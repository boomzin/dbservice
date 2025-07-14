package ru.mediatel.icc.dbservice.model.order;


import ru.mediatel.icc.dbservice.common.data.PagedResult;

import java.util.Map;
import java.util.UUID;

public interface OrderRepository {
    void create(Order order);

    void update(Order order);

    Order findById(UUID orderId);

    PagedResult<Order> search(Map<String, String> apiParams);

    void delete(UUID orderId);
}
