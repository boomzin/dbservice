package ru.mediatel.icc.dbservice.db.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jooq.Converter;
import org.jooq.JSONB;
import ru.mediatel.icc.dbservice.model.tool.ToolDefinition;

public class ToolDefinitionConverter implements Converter<JSONB, ToolDefinition> {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public ToolDefinition from(JSONB databaseObject) {
        if (databaseObject == null) return null;
        try {
            ToolDefinition result = mapper.readValue(databaseObject.data(), ToolDefinition.class);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSONB", e);
        }
    }

    @Override
    public JSONB to(ToolDefinition userObject) {
        if (userObject == null) return null;
        try {
            return JSONB.valueOf(mapper.writeValueAsString(userObject));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize JSONB", e);
        }
    }

    @Override
    public Class<JSONB> fromType() {
        return JSONB.class;
    }

    @Override
    public Class<ToolDefinition> toType() {
        return ToolDefinition.class;
    }
}
