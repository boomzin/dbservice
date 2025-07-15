package ru.mediatel.icc.dbservice.rest.cart;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CartDetailsDto(
        UUID id,
        UUID userId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String status,
        String customerComment,
        List<ProductInCartDto> items,
        BigDecimal totalPrice
) {}