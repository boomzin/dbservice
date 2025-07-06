package ru.mediatel.icc.dbservice.model.claim;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mediatel.icc.dbservice.common.data.PagedResult;

import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class ClaimService {
    private final ClaimRepository repository;

    public ClaimService(ClaimRepository repository) {
        this.repository = repository;
    }

    public Claim findById(UUID climId) {
        return repository.findById(climId);
    }

    public PagedResult<Claim> search(Map<String, String> apiParams) {
        return repository.search(apiParams);
    }

    public void update(Claim product) {
        repository.update(product);
    }

    public void create(Claim product) {
        repository.create(product);
    }

    public void delete(UUID productId) {
        repository.delete(productId);
    }

}
