package ru.mediatel.icc.dbservice.db.repository;

import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.jooq.SelectWhereStep;
import org.springframework.stereotype.Repository;
import ru.mediatel.icc.dbservice.common.data.PagedResult;
import ru.mediatel.icc.dbservice.common.exception.ObjectNotFoundException;
import ru.mediatel.icc.dbservice.common.search.SearchCriteria;
import ru.mediatel.icc.dbservice.common.search.SearchCriteriaSettings;
import ru.mediatel.icc.dbservice.db.generated.enums.OrderStatus;
import ru.mediatel.icc.dbservice.db.generated.tables.records.OrdersRecord;
import ru.mediatel.icc.dbservice.model.order.Order;
import ru.mediatel.icc.dbservice.model.order.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static ru.mediatel.icc.dbservice.common.search.JooqSearchUtils.STR_LIKE_IC;
import static ru.mediatel.icc.dbservice.common.search.JooqSearchUtils.UUID_EQ;
import static ru.mediatel.icc.dbservice.db.generated.Tables.ORDERS;


@Repository
public class JooqOrderRepository implements OrderRepository {
    private final DSLContext db;
    private final SearchCriteriaSettings criteriaSettings;

    private final RecordMapper<OrdersRecord, Order> mapper = r -> new Order(
            r.getId(),
            r.getUserId(),
            r.getStatus(),
            r.getCreatedAt(),
            r.getUpdatedAt(),
            r.getCustomerComment()
    );


    public JooqOrderRepository(DSLContext db) {
        this.db = db;
        this.criteriaSettings = SearchCriteriaSettings.builder()
                .filter("user_id", ORDERS.USER_ID, UUID_EQ)
                .filter("cart_id", ORDERS.CART_ID, UUID_EQ)
                .filter("status", ORDERS.STATUS, (f, v) -> f.eq(OrderStatus.valueOf(v)))
                .filter("created_at", ORDERS.CREATED_AT, (f, v) -> f.eq(LocalDateTime.parse(v)))
                .filter("created_at_from", ORDERS.CREATED_AT, (f, v) -> f.ge(LocalDateTime.parse(v)))
                .filter("created_at_to", ORDERS.CREATED_AT, (f, v) -> f.le(LocalDateTime.parse(v)))
                .filter("updated_at", ORDERS.UPDATED_AT, (f, v) -> f.eq(LocalDateTime.parse(v)))
                .filter("updated_at_from", ORDERS.UPDATED_AT, (f, v) -> f.ge(LocalDateTime.parse(v)))
                .filter("updated_at_to", ORDERS.UPDATED_AT, (f, v) -> f.le(LocalDateTime.parse(v)))
                .filter("customer_comment", ORDERS.CUSTOMER_COMMENT, STR_LIKE_IC)

                .order("status", ORDERS.STATUS)
                .order("created_at", ORDERS.CREATED_AT)
                .order("updated_at", ORDERS.UPDATED_AT)

                .build();
    }


    @Override
    public void create(Order order) {
        OrdersRecord record = db.newRecord(ORDERS);
        fillRecord(record, order);
        record.insert();
    }

    @Override
    public void update(Order order) {
        OrdersRecord record = db.fetchOptional(
                ORDERS,
                ORDERS.ID.eq(order.getId()))
                .orElseThrow(() -> new ObjectNotFoundException(order.getId(), "Order"));
        fillRecord(record, order);
        record.store();

    }

    @Override
    public Order findById(UUID orderId) {
        return db
                .selectFrom(ORDERS)
                .where(ORDERS.ID.eq(orderId))
                .fetchOptional(mapper)
                .orElseThrow(() -> new ObjectNotFoundException(orderId, "Order"));
    }


    @Override
    public PagedResult<Order> search(Map<String, String> apiParams) {
        SearchCriteria criteria = SearchCriteria.getInstance(criteriaSettings, apiParams);
        SelectWhereStep<?> step = db.selectFrom(ORDERS);
        criteria.apply(step);

        List<Order> list = step.fetch().map(record -> mapper.map((OrdersRecord) record));

        SelectWhereStep<?> countStep = db.selectCount().from(ORDERS);

        criteria.applyForCount(countStep);
        int itemsCount = countStep.fetchOptionalInto(Integer.class).orElse(0);

        return new PagedResult<>(list, itemsCount, criteria.getOffset(), criteria.getLimit());
    }

    @Override
    public void delete(UUID orderId) {
        db.deleteFrom(ORDERS)
                .where(ORDERS.ID.eq(orderId))
                .execute();
    }

    private void fillRecord(OrdersRecord record, Order order) {
        record.setId(order.getId());
        record.setUserId(order.getUserId());
        record.setCartId(order.getCartId());
        record.setStatus(order.getStatus());
        record.setCreatedAt(order.getCreatedAt());
        record.setUpdatedAt(order.getUpdatedAt());
        record.setCustomerComment(order.getCustomerComment());
    }
}
