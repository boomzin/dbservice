package ru.mediatel.icc.dbservice.rest.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mediatel.icc.dbservice.db.generated.enums.OrderStatus;
import ru.mediatel.icc.dbservice.model.order.Order;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private UUID id;
    private UUID userId;
    private UUID cartId;
    private OrderStatus status;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String customerComment;

    public OrderDto(Order order) {
        this(
                order.getId(),
                order.getUserId(),
                order.getUserId(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getUpdatedAt(),
                order.getCustomerComment()
        );
    }
}

