package ru.mediatel.icc.dbservice.model.tool;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ToolService {

    private final  ToolRepository repository;

    public ToolService(ToolRepository repository) {
        this.repository = repository;
    }

    public Tool saveOrUpdate(Tool tool) {
        UUID resultUuid = tool.getId();
        if (resultUuid == null) {
            resultUuid = repository.save(tool.getDefinition());
        } else {
            if (!repository.existsById(tool.getId())) {
                throw new IllegalArgumentException("Tool with id=" + tool.getId() + " not found");
            }
            repository.update(tool);
        }

        return repository.findById(resultUuid)
                .orElseThrow(() -> new IllegalStateException("Failed to save tool with id=" + tool.getId()));
    }

    public Tool findById(UUID uuid) {
        return repository.findById(uuid).orElseThrow(() -> new IllegalStateException("Failed to get tool with id=" + uuid));
    }

    public List<Tool> findAll() {
        List<Tool> result = repository.findAll();
        return result;
    }
}
