package ru.mediatel.icc.dbservice.model.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mediatel.icc.dbservice.common.data.PagedResult;
import ru.mediatel.icc.dbservice.db.generated.enums.CartStatus;
import ru.mediatel.icc.dbservice.db.generated.enums.OrderStatus;
import ru.mediatel.icc.dbservice.model.cart.CartRepository;

import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;


    public OrderService(OrderRepository orderRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    public Order findById(UUID orderId) {
        return orderRepository.findById(orderId);
    }

    public PagedResult<Order> search(Map<String, String> apiParams) {
        return orderRepository.search(apiParams);
    }

    public void update(Order order) {
        orderRepository.update(order);
    }

    public void create(Order order) {
        orderRepository.create(order);
    }

    public void delete(UUID orderId) {
        orderRepository.delete(orderId);
    }


    @Transactional
    public void activateOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId);

        orderRepository.setOrderStatus(orderId, OrderStatus.ACTIVE);

        UUID cartId = order.getCartId();
        cartRepository.setStatus(cartId, CartStatus.ARCHIVED);
    }

    @Transactional
    public void markAsDone(UUID orderId) {
        Map<UUID, Integer> items = orderRepository.getOrderItems(orderId);

        orderRepository.decrementStock(items);

        orderRepository.setOrderStatus(orderId, OrderStatus.DONE);
    }
}
