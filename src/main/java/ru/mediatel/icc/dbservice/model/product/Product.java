package ru.mediatel.icc.dbservice.model.product;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.mediatel.icc.dbservice.common.UuidEntity;

import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product extends UuidEntity {
    private Integer quantity;
    private BigDecimal price;
    private String description;

    public Product(UUID id, Integer quantity, BigDecimal price, String description) {
        super(id);
        this.quantity = quantity;
        this.price = price;
        this.description = description;
    }
}
