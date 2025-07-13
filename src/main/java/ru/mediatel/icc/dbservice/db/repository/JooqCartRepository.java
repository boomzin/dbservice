package ru.mediatel.icc.dbservice.db.repository;

import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.jooq.SelectWhereStep;
import org.springframework.stereotype.Repository;
import ru.mediatel.icc.dbservice.common.data.PagedResult;
import ru.mediatel.icc.dbservice.common.exception.ObjectNotFoundException;
import ru.mediatel.icc.dbservice.common.search.SearchCriteria;
import ru.mediatel.icc.dbservice.common.search.SearchCriteriaSettings;
import ru.mediatel.icc.dbservice.db.generated.enums.CartStatus;
import ru.mediatel.icc.dbservice.db.generated.tables.records.CartsRecord;
import ru.mediatel.icc.dbservice.model.cart.Cart;
import ru.mediatel.icc.dbservice.model.cart.CartRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static ru.mediatel.icc.dbservice.common.search.JooqSearchUtils.STR_LIKE_IC;
import static ru.mediatel.icc.dbservice.common.search.JooqSearchUtils.UUID_EQ;
import static ru.mediatel.icc.dbservice.db.generated.Tables.CARTS;


@Repository
public class JooqCartRepository implements CartRepository {
    private final DSLContext db;
    private final SearchCriteriaSettings criteriaSettings;

    private final RecordMapper<CartsRecord, Cart> mapper = r -> new Cart(
            r.getId(),
            r.getUserId(),
            r.getStatus(),
            r.getCreatedAt(),
            r.getUpdatedAt(),
            r.getCustomerComment()
    );


    public JooqCartRepository(DSLContext db) {
        this.db = db;
        this.criteriaSettings = SearchCriteriaSettings.builder()
                .filter("user_id", CARTS.USER_ID, UUID_EQ)
                .filter("status", CARTS.STATUS, (f, v) -> f.eq(CartStatus.valueOf(v)))
                .filter("created_at", CARTS.CREATED_AT, (f, v) -> f.eq(LocalDateTime.parse(v)))
                .filter("created_at_from", CARTS.CREATED_AT, (f, v) -> f.ge(LocalDateTime.parse(v)))
                .filter("created_at_to", CARTS.CREATED_AT, (f, v) -> f.le(LocalDateTime.parse(v)))
                .filter("updated_at", CARTS.UPDATED_AT, (f, v) -> f.eq(LocalDateTime.parse(v)))
                .filter("updated_at_from", CARTS.UPDATED_AT, (f, v) -> f.ge(LocalDateTime.parse(v)))
                .filter("updated_at_to", CARTS.UPDATED_AT, (f, v) -> f.le(LocalDateTime.parse(v)))
                .filter("customer_comment", CARTS.CUSTOMER_COMMENT, STR_LIKE_IC)

                .order("status", CARTS.STATUS)
                .order("created_at", CARTS.CREATED_AT)
                .order("updated_at", CARTS.UPDATED_AT)

                .build();
    }


    @Override
    public void create(Cart cart) {
        CartsRecord record = db.newRecord(CARTS);
        fillRecord(record, cart);
        record.insert();
    }

    @Override
    public void update(Cart cart) {
        CartsRecord record = db.fetchOptional(
                CARTS,
                CARTS.ID.eq(cart.getId()))
                .orElseThrow(() -> new ObjectNotFoundException(cart.getId(), "Cart"));
        fillRecord(record, cart);
        record.store();

    }

    @Override
    public Cart findById(UUID cartId) {
        return db
                .selectFrom(CARTS)
                .where(CARTS.ID.eq(cartId))
                .fetchOptional(mapper)
                .orElseThrow(() -> new ObjectNotFoundException(cartId, "Cart"));
    }


    @Override
    public PagedResult<Cart> search(Map<String, String> apiParams) {
        SearchCriteria criteria = SearchCriteria.getInstance(criteriaSettings, apiParams);
        SelectWhereStep<?> step = db.selectFrom(CARTS);
        criteria.apply(step);

        List<Cart> list = step.fetch().map(record -> mapper.map((CartsRecord) record));

        SelectWhereStep<?> countStep = db.selectCount().from(CARTS);

        criteria.applyForCount(countStep);
        int itemsCount = countStep.fetchOptionalInto(Integer.class).orElse(0);

        return new PagedResult<>(list, itemsCount, criteria.getOffset(), criteria.getLimit());
    }

    @Override
    public void delete(UUID cartId) {
        db.deleteFrom(CARTS)
                .where(CARTS.ID.eq(cartId))
                .execute();
    }

    private void fillRecord(CartsRecord record, Cart cart) {
        record.setId(cart.getId());
        record.setUserId(cart.getUserId());
        record.setStatus(cart.getStatus());
        record.setCreatedAt(cart.getCreatedAt());
        record.setUpdatedAt(cart.getUpdatedAt());
        record.setCustomerComment(cart.getCustomerComment());
    }
}
