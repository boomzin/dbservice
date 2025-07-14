package ru.mediatel.icc.dbservice.model.order;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.mediatel.icc.dbservice.common.UuidEntity;
import ru.mediatel.icc.dbservice.db.generated.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order extends UuidEntity {
    private UUID userId;
    private UUID cartId;
    private OrderStatus status;
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updatedAt;
    private String customerComment;

    public Order(UUID id, UUID userId, UUID cartId, OrderStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, String customerComment) {
        super(id);
        this.userId = userId;
        this.cartId = cartId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.customerComment = customerComment;
    }
}
