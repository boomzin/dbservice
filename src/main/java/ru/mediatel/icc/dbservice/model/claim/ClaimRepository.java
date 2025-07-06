package ru.mediatel.icc.dbservice.model.claim;


import ru.mediatel.icc.dbservice.common.data.PagedResult;

import java.util.Map;
import java.util.UUID;

public interface ClaimRepository {
    void create(Claim claim);

    void update(Claim claim);

    Claim findById(UUID claimId);

    PagedResult<Claim> search(Map<String, String> apiParams);

    void delete(UUID claimId);
}
