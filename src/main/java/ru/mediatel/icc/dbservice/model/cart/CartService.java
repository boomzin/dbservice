package ru.mediatel.icc.dbservice.model.cart;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mediatel.icc.dbservice.common.data.PagedResult;
import ru.mediatel.icc.dbservice.db.generated.enums.CartStatus;
import ru.mediatel.icc.dbservice.db.generated.tables.records.CartsRecord;
import ru.mediatel.icc.dbservice.model.order.Order;
import ru.mediatel.icc.dbservice.model.order.OrderRepository;
import ru.mediatel.icc.dbservice.rest.cart.CartDetailsDto;

import java.util.Map;
import java.util.UUID;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public CartService(CartRepository cartRepository, OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }

    public Cart findById(UUID cartId) {
        return cartRepository.findById(cartId);
    }

    public PagedResult<Cart> search(Map<String, String> apiParams) {
        return cartRepository.search(apiParams);
    }

    public void update(Cart cart) {
        cartRepository.update(cart);
    }

    public void create(Cart cart) {
        cartRepository.create(cart);
    }

    public void delete(UUID cartId) {
        cartRepository.delete(cartId);
    }

    public CartDetailsDto getCartDetails(UUID cartId) {
        return cartRepository.getCartDetails(cartId);
    }


    @Transactional
    public UUID confirmCartAndCreateOrder(UUID cartId) {
        Cart cart = cartRepository.findById(cartId);

        if (cart.getStatus() == CartStatus.CONFIRMED) {
            throw new IllegalStateException("Cart already confirmed");
        }

        var items = cartRepository.findCartItemsForValidation(cartId);
        for (var it : items) {
            int available = it.getStockQty() - it.getReservedQty();
            if (available < it.getRequestedQty()) {
                throw new IllegalStateException(
                        "Not enough stock for product " + it.getProductId() +
                                " (requested " + it.getRequestedQty() + ", available " + available + ")"
                );
            }
        }

        cartRepository.setStatus(cartId, CartStatus.CONFIRMED);

        Order order = orderRepository.createPendingOrderFromCart(cart);
        orderRepository.copyItemsFromCart(order.getId(), cartId);

        return order.getId();
    }
}
