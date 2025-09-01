package ru.mediatel.icc.dbservice.model.tool;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ToolRepository {

    UUID save(ToolDefinition toolDefinition);

    Optional<Tool> findById(UUID id);

    List<Tool> findAll();

    boolean existsById(UUID id);

    void update(Tool tool);
}
