package ru.mediatel.icc.dbservice.model.productincart;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.mediatel.icc.dbservice.common.UuidEntity;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInCart extends UuidEntity {
    private UUID cartId;
    private UUID productId;
    private Integer quantity;
}
