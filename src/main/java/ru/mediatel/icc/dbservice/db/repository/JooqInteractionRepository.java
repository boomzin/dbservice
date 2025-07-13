package ru.mediatel.icc.dbservice.db.repository;

import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.jooq.SelectWhereStep;
import org.springframework.stereotype.Repository;
import ru.mediatel.icc.dbservice.common.data.PagedResult;
import ru.mediatel.icc.dbservice.common.exception.ObjectNotFoundException;
import ru.mediatel.icc.dbservice.common.search.SearchCriteria;
import ru.mediatel.icc.dbservice.common.search.SearchCriteriaSettings;
import ru.mediatel.icc.dbservice.db.generated.enums.InteractionStatus;
import ru.mediatel.icc.dbservice.db.generated.tables.records.InteractionsRecord;
import ru.mediatel.icc.dbservice.model.interaction.Interaction;
import ru.mediatel.icc.dbservice.model.interaction.InteractionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static ru.mediatel.icc.dbservice.common.search.JooqSearchUtils.STR_LIKE_IC;
import static ru.mediatel.icc.dbservice.common.search.JooqSearchUtils.UUID_EQ;
import static ru.mediatel.icc.dbservice.db.generated.Tables.INTERACTIONS;


@Repository
public class JooqInteractionRepository implements InteractionRepository {
    private final DSLContext db;
    private final SearchCriteriaSettings criteriaSettings;

    private final RecordMapper<InteractionsRecord, Interaction> mapper = r -> new Interaction(
            r.getId(),
            r.getCartId(),
            r.getOrderId(),
            r.getStatus(),
            r.getCreatedAt(),
            r.getUpdatedAt(),
            r.getCustomerComment()
    );


    public JooqInteractionRepository(DSLContext db) {
        this.db = db;
        this.criteriaSettings = SearchCriteriaSettings.builder()
                .filter("cart_id", INTERACTIONS.CART_ID, UUID_EQ)
                .filter("order_id", INTERACTIONS.ORDER_ID, UUID_EQ)
                .filter("status", INTERACTIONS.STATUS, (f, v) -> f.eq(InteractionStatus.valueOf(v)))
                .filter("created_at", INTERACTIONS.CREATED_AT, (f, v) -> f.eq(LocalDateTime.parse(v)))
                .filter("created_at_from", INTERACTIONS.CREATED_AT, (f, v) -> f.ge(LocalDateTime.parse(v)))
                .filter("created_at_to", INTERACTIONS.CREATED_AT, (f, v) -> f.le(LocalDateTime.parse(v)))
                .filter("updated_at", INTERACTIONS.UPDATED_AT, (f, v) -> f.eq(LocalDateTime.parse(v)))
                .filter("updated_at_from", INTERACTIONS.UPDATED_AT, (f, v) -> f.ge(LocalDateTime.parse(v)))
                .filter("updated_at_to", INTERACTIONS.UPDATED_AT, (f, v) -> f.le(LocalDateTime.parse(v)))
                .filter("customer_comment", INTERACTIONS.CUSTOMER_COMMENT, STR_LIKE_IC)

                .order("status", INTERACTIONS.STATUS)
                .order("created_at", INTERACTIONS.CREATED_AT)
                .order("updated_at", INTERACTIONS.UPDATED_AT)

                .build();
    }


    @Override
    public void create(Interaction interaction) {
        InteractionsRecord record = db.newRecord(INTERACTIONS);
        fillRecord(record, interaction);
        record.insert();
    }

    @Override
    public void update(Interaction interaction) {
        InteractionsRecord record = db.fetchOptional(
                INTERACTIONS,
                INTERACTIONS.ID.eq(interaction.getId()))
                .orElseThrow(() -> new ObjectNotFoundException(interaction.getId(), "Interaction"));
        fillRecord(record, interaction);
        record.store();

    }

    @Override
    public Interaction findById(UUID interactionId) {
        return db
                .selectFrom(INTERACTIONS)
                .where(INTERACTIONS.ID.eq(interactionId))
                .fetchOptional(mapper)
                .orElseThrow(() -> new ObjectNotFoundException(interactionId, "Interaction"));
    }


    @Override
    public PagedResult<Interaction> search(Map<String, String> apiParams) {
        SearchCriteria criteria = SearchCriteria.getInstance(criteriaSettings, apiParams);
        SelectWhereStep<?> step = db.selectFrom(INTERACTIONS);
        criteria.apply(step);

        List<Interaction> list = step.fetch().map(record -> mapper.map((InteractionsRecord) record));

        SelectWhereStep<?> countStep = db.selectCount().from(INTERACTIONS);

        criteria.applyForCount(countStep);
        int itemsCount = countStep.fetchOptionalInto(Integer.class).orElse(0);

        return new PagedResult<>(list, itemsCount, criteria.getOffset(), criteria.getLimit());
    }

    @Override
    public void delete(UUID interactionId) {
        db.deleteFrom(INTERACTIONS)
                .where(INTERACTIONS.ID.eq(interactionId))
                .execute();
    }

    private void fillRecord(InteractionsRecord record, Interaction interaction) {
        record.setId(interaction.getId());
        record.setCartId(interaction.getCartId());
        record.setOrderId(interaction.getOrderId());
        record.setStatus(interaction.getStatus());
        record.setCreatedAt(interaction.getCreatedAt());
        record.setUpdatedAt(interaction.getUpdatedAt());
        record.setCustomerComment(interaction.getCustomerComment());
    }
}
