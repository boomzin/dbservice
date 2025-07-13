package ru.mediatel.icc.dbservice.model.interaction;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mediatel.icc.dbservice.common.data.PagedResult;

import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class InteractionService {
    private final InteractionRepository repository;

    public InteractionService(InteractionRepository repository) {
        this.repository = repository;
    }

    public Interaction findById(UUID interactionId) {
        return repository.findById(interactionId);
    }

    public PagedResult<Interaction> search(Map<String, String> apiParams) {
        return repository.search(apiParams);
    }

    public void update(Interaction interaction) {
        repository.update(interaction);
    }

    public void create(Interaction interaction) {
        repository.create(interaction);
    }

    public void delete(UUID interactionId) {
        repository.delete(interactionId);
    }
}
