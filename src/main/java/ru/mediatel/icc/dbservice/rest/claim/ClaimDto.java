package ru.mediatel.icc.dbservice.rest.claim;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mediatel.icc.dbservice.db.generated.enums.ClaimStatus;
import ru.mediatel.icc.dbservice.model.claim.Claim;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaimDto {
    private UUID id;
    private UUID orderId;
    private ClaimStatus status;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String customerComment;

    public ClaimDto(Claim claim) {
        this(
                claim.getId(),
                claim.getOrderId(),
                claim.getStatus(),
                claim.getCreatedAt(),
                claim.getUpdatedAt(),
                claim.getCustomerComment()
        );
    }
}

