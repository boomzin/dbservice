package ru.mediatel.icc.dbservice.model.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mediatel.icc.dbservice.common.data.PagedResult;

import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product findById(UUID productId) {
        return repository.findById(productId);
    }

    public PagedResult<Product> search(Map<String, String> apiParams) {
        return repository.search(apiParams);
    }

    public void update(Product product) {
        repository.update(product);
    }

    public void create(Product product) {
        repository.create(product);
    }

    public void delete(UUID productId) {
        repository.delete(productId);
    }

}
