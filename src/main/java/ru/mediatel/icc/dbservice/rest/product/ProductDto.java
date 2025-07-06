package ru.mediatel.icc.dbservice.rest.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mediatel.icc.dbservice.model.product.Product;

import java.math.BigDecimal;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private UUID id;
    private Integer quantity;
    private BigDecimal price;
    private String description;

    public ProductDto(Product product) {
        this(
                product.getId(),
                product.getQuantity(),
                product.getPrice(),
                product.getDescription()
        );
    }
}

