package ru.mediatel.icc.dbservice.model.product;


import ru.mediatel.icc.dbservice.common.data.PagedResult;

import java.util.Map;
import java.util.UUID;

public interface ProductRepository {
    void create(Product product);

    void update(Product product);

    Product findById(UUID productId);

    PagedResult<Product> search(Map<String, String> apiParams);

    void delete(UUID productId);
}
