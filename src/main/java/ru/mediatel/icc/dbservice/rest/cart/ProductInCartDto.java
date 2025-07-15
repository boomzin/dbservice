package ru.mediatel.icc.dbservice.rest.cart;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductInCartDto(
        UUID productId,
        String description,
        BigDecimal price,
        int quantity,
        BigDecimal subtotal
) {}
