package ru.mediatel.icc.dbservice.model.tool;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mediatel.icc.dbservice.db.generated.tables.pojos.Tools;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tool {

    @JsonProperty("uuid")
    private UUID id;

    @JsonProperty("toolDefinition")
    private ToolDefinition definition;

    public Tool create(Tools tools) {
        return new Tool(tools.getId(), getDefinition());
    }
}
