package ru.mediatel.icc.dbservice.model.interaction;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.mediatel.icc.dbservice.common.UuidEntity;
import ru.mediatel.icc.dbservice.db.generated.enums.InteractionStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Interaction extends UuidEntity {
    private UUID cartId;
    private UUID orderId;
    private InteractionStatus status;
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updatedAt;
    private String customerComment;

    public Interaction(UUID id, UUID cartId, UUID ordeId, InteractionStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, String customerComment) {
        super(id);
        this.cartId = cartId;
        this.orderId = ordeId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.customerComment = customerComment;
    }
}
