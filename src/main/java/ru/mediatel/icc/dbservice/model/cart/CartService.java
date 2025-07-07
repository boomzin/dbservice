package ru.mediatel.icc.dbservice.model.cart;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mediatel.icc.dbservice.common.data.PagedResult;

import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class CartService {
    private final CartRepository repository;

    public CartService(CartRepository repository) {
        this.repository = repository;
    }

    public Cart findById(UUID cartId) {
        return repository.findById(cartId);
    }

    public PagedResult<Cart> search(Map<String, String> apiParams) {
        return repository.search(apiParams);
    }

    public void update(Cart cart) {
        repository.update(cart);
    }

    public void create(Cart cart) {
        repository.create(cart);
    }

    public void delete(UUID cartId) {
        repository.delete(cartId);
    }

}
