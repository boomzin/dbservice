package ru.mediatel.icc.dbservice.model.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mediatel.icc.dbservice.common.data.PagedResult;

import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class OrderService {
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order findById(UUID orderId) {
        return repository.findById(orderId);
    }

    public PagedResult<Order> search(Map<String, String> apiParams) {
        return repository.search(apiParams);
    }

    public void update(Order order) {
        repository.update(order);
    }

    public void create(Order order) {
        repository.create(order);
    }

    public void delete(UUID orderId) {
        repository.delete(orderId);
    }

}
