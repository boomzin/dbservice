package ru.mediatel.icc.dbservice.model.interaction;


import ru.mediatel.icc.dbservice.common.data.PagedResult;

import java.util.Map;
import java.util.UUID;

public interface InteractionRepository {
    void create(Interaction interaction);

    void update(Interaction interaction);

    Interaction findById(UUID interactionId);

    PagedResult<Interaction> search(Map<String, String> apiParams);

    void delete(UUID interactionId);
}
