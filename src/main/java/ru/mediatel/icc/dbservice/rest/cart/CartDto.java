package ru.mediatel.icc.dbservice.rest.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mediatel.icc.dbservice.db.generated.enums.CartStatus;
import ru.mediatel.icc.dbservice.model.cart.Cart;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private UUID id;
    private UUID userId;
    private CartStatus status;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String customerComment;

    public CartDto(Cart cart) {
        this(
                cart.getId(),
                cart.getUserId(),
                cart.getStatus(),
                cart.getCreatedAt(),
                cart.getUpdatedAt(),
                cart.getCustomerComment()
        );
    }
}

