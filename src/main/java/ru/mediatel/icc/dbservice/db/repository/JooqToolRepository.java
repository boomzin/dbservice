package ru.mediatel.icc.dbservice.db.repository;

import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;
import ru.mediatel.icc.dbservice.db.generated.tables.records.ToolsRecord;
import ru.mediatel.icc.dbservice.model.tool.Tool;
import ru.mediatel.icc.dbservice.model.tool.ToolDefinition;
import ru.mediatel.icc.dbservice.model.tool.ToolRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ru.mediatel.icc.dbservice.db.generated.Tables.TOOLS;


@Repository
public class JooqToolRepository implements ToolRepository {
    private final DSLContext dsl;


    public JooqToolRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public UUID save(ToolDefinition toolDefinition) {
        return dsl.insertInto(TOOLS)
                .set(TOOLS.DEFINITION, toolDefinition)
                .returning(TOOLS.ID)
                .fetchOne(TOOLS.ID);
    }

    @Override
    public boolean existsById(UUID id) {
        return dsl.fetchExists(
                dsl.selectOne().from(TOOLS).where(TOOLS.ID.eq(id))
        );
    }

    @Override
    public Optional<Tool> findById(UUID id) {
        return dsl.selectFrom(TOOLS)
                .where(TOOLS.ID.eq(id))
                .fetchOptionalInto(Tool.class);
    }

    @Override
    public void update(Tool tool) {
        dsl.update(TOOLS)
                .set(TOOLS.DEFINITION, tool.getDefinition())
                .where(TOOLS.ID.eq(tool.getId()))
                .execute();
    }

    public List<Tool> findAll() {
        return dsl.select(TOOLS.ID, TOOLS.DEFINITION)
                .from(TOOLS)
                .fetchInto(Tool.class);
    }
}
