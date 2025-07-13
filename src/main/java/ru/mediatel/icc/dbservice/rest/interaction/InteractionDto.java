package ru.mediatel.icc.dbservice.rest.interaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mediatel.icc.dbservice.db.generated.enums.InteractionStatus;
import ru.mediatel.icc.dbservice.model.interaction.Interaction;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InteractionDto {
    private UUID id;
    private UUID cartId;
    private UUID orderId;
    private InteractionStatus status;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String customerComment;

    public InteractionDto(Interaction interaction) {
        this(
                interaction.getId(),
                interaction.getCartId(),
                interaction.getOrderId(),
                interaction.getStatus(),
                interaction.getCreatedAt(),
                interaction.getUpdatedAt(),
                interaction.getCustomerComment()
        );
    }
}

