package ru.mediatel.icc.dbservice.model.cart;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CartItemCheck {
    private UUID productId;
    private int requestedQty;
    private int stockQty;
    private int reservedQty;
}