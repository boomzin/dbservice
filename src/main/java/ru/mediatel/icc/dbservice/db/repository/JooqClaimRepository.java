package ru.mediatel.icc.dbservice.db.repository;

import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.jooq.SelectWhereStep;
import org.springframework.stereotype.Repository;
import ru.mediatel.icc.dbservice.common.data.PagedResult;
import ru.mediatel.icc.dbservice.common.exception.ObjectNotFoundException;
import ru.mediatel.icc.dbservice.common.search.SearchCriteria;
import ru.mediatel.icc.dbservice.common.search.SearchCriteriaSettings;
import ru.mediatel.icc.dbservice.db.generated.enums.ClaimStatus;
import ru.mediatel.icc.dbservice.db.generated.tables.records.ClaimsRecord;
import ru.mediatel.icc.dbservice.model.claim.Claim;
import ru.mediatel.icc.dbservice.model.claim.ClaimRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static ru.mediatel.icc.dbservice.common.search.JooqSearchUtils.STR_LIKE_IC;
import static ru.mediatel.icc.dbservice.common.search.JooqSearchUtils.UUID_EQ;
import static ru.mediatel.icc.dbservice.db.generated.Tables.CLAIMS;


@Repository
public class JooqClaimRepository implements ClaimRepository {
    private final DSLContext db;
    private final SearchCriteriaSettings criteriaSettings;

    private final RecordMapper<ClaimsRecord, Claim> mapper = r -> new Claim(
            r.getId(),
            r.getOrderId(),
            r.getStatus(),
            r.getCreatedAt(),
            r.getUpdatedAt(),
            r.getCustomerComment()
    );


    public JooqClaimRepository(DSLContext db) {
        this.db = db;
        this.criteriaSettings = SearchCriteriaSettings.builder()
                .filter("order_id", CLAIMS.ORDER_ID, UUID_EQ)
                .filter("status", CLAIMS.STATUS, (f, v) -> f.eq(ClaimStatus.valueOf(v)))
                .filter("created_at", CLAIMS.CREATED_AT, (f, v) -> f.eq(LocalDateTime.parse(v)))
                .filter("created_at_from", CLAIMS.CREATED_AT, (f, v) -> f.ge(LocalDateTime.parse(v)))
                .filter("created_at_to", CLAIMS.CREATED_AT, (f, v) -> f.le(LocalDateTime.parse(v)))
                .filter("updated_at", CLAIMS.UPDATED_AT, (f, v) -> f.eq(LocalDateTime.parse(v)))
                .filter("updated_at_from", CLAIMS.UPDATED_AT, (f, v) -> f.ge(LocalDateTime.parse(v)))
                .filter("updated_at_to", CLAIMS.UPDATED_AT, (f, v) -> f.le(LocalDateTime.parse(v)))
                .filter("customer_comment", CLAIMS.CUSTOMER_COMMENT, STR_LIKE_IC)

                .order("status", CLAIMS.STATUS)
                .order("created_at", CLAIMS.CREATED_AT)
                .order("updated_at", CLAIMS.UPDATED_AT)

                .build();
    }


    @Override
    public void create(Claim claim) {
        ClaimsRecord record = db.newRecord(CLAIMS);
        fillRecord(record, claim);
        record.insert();
    }

    @Override
    public void update(Claim claim) {
        ClaimsRecord record = db.fetchOptional(
                CLAIMS,
                CLAIMS.ID.eq(claim.getId()))
                .orElseThrow(() -> new ObjectNotFoundException(claim.getId(), "Claim"));
        fillRecord(record, claim);
        record.store();

    }

    @Override
    public Claim findById(UUID claimId) {
        return db
                .selectFrom(CLAIMS)
                .where(CLAIMS.ID.eq(claimId))
                .fetchOptional(mapper)
                .orElseThrow(() -> new ObjectNotFoundException(claimId, "Claim"));
    }


    @Override
    public PagedResult<Claim> search(Map<String, String> apiParams) {
        SearchCriteria criteria = SearchCriteria.getInstance(criteriaSettings, apiParams);
        SelectWhereStep<?> step = db.selectFrom(CLAIMS);
        criteria.apply(step);

        List<Claim> list = step.fetch().map(record -> mapper.map((ClaimsRecord) record));

        SelectWhereStep<?> countStep = db.selectCount().from(CLAIMS);

        criteria.applyForCount(countStep);
        int itemsCount = countStep.fetchOptionalInto(Integer.class).orElse(0);

        return new PagedResult<>(list, itemsCount, criteria.getOffset(), criteria.getLimit());
    }

    @Override
    public void delete(UUID claimId) {
        db.deleteFrom(CLAIMS)
                .where(CLAIMS.ID.eq(claimId))
                .execute();
    }

    private void fillRecord(ClaimsRecord record, Claim claim) {
        record.setId(claim.getId());
        record.setOrderId(claim.getOrderId());
        record.setStatus(claim.getStatus());
        record.setCreatedAt(claim.getCreatedAt());
        record.setUpdatedAt(claim.getUpdatedAt());
        record.setCustomerComment(claim.getCustomerComment());
    }
}
