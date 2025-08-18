package ru.mediatel.icc.dbservice.db.repository;

import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import ru.mediatel.icc.dbservice.common.data.PagedResult;
import ru.mediatel.icc.dbservice.common.exception.ObjectNotFoundException;
import ru.mediatel.icc.dbservice.common.search.SearchCriteria;
import ru.mediatel.icc.dbservice.common.search.SearchCriteriaSettings;
import ru.mediatel.icc.dbservice.db.generated.enums.OrderStatus;
import ru.mediatel.icc.dbservice.db.generated.tables.records.OrdersRecord;
import ru.mediatel.icc.dbservice.model.cart.Cart;
import ru.mediatel.icc.dbservice.model.order.Order;
import ru.mediatel.icc.dbservice.model.order.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static ru.mediatel.icc.dbservice.common.search.JooqSearchUtils.STR_LIKE_IC;
import static ru.mediatel.icc.dbservice.common.search.JooqSearchUtils.UUID_EQ;
import static ru.mediatel.icc.dbservice.db.generated.Tables.*;


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

    @Override
    public Order createPendingOrderFromCart(Cart cart) {
        return db.insertInto(ORDERS)
                .set(ORDERS.USER_ID, cart.getUserId())
                .set(ORDERS.STATUS, OrderStatus.PENDING)
                .set(ORDERS.CART_ID, cart.getId())
                .set(ORDERS.CUSTOMER_COMMENT, cart.getCustomerComment())
                .returning()
                .fetchOne(mapper);
    }

    @Override
    public void copyItemsFromCart(UUID orderId, UUID cartId) {
        db.insertInto(PRODUCTS_IN_ORDERS,
                        PRODUCTS_IN_ORDERS.ORDER_ID,
                        PRODUCTS_IN_ORDERS.PRODUCT_ID,
                        PRODUCTS_IN_ORDERS.QUANTITY)
                .select(
                        db.select(DSL.val(orderId),
                                        PRODUCTS_IN_CARTS.PRODUCT_ID,
                                        PRODUCTS_IN_CARTS.QUANTITY)
                                .from(PRODUCTS_IN_CARTS)
                                .where(PRODUCTS_IN_CARTS.CART_ID.eq(cartId))
                )
                .execute();
    }

    @Override
    public void setOrderStatus(UUID orderId, OrderStatus status) {
        db.update(ORDERS)
                .set(ORDERS.STATUS, status)
                .where(ORDERS.ID.eq(orderId))
                .execute();
    }

    @Override
    public void decrementStock(Map<UUID, Integer> items) {
        items.forEach((productId, qty) -> {
            db.update(PRODUCTS)
                    .set(PRODUCTS.QUANTITY, PRODUCTS.QUANTITY.minus(qty))
                    .where(PRODUCTS.ID.eq(productId))
                    .execute();
        });
    }

    @Override
    public void incrementStock(Map<UUID, Integer> items) {
        items.forEach((productId, qty) -> {
            db.update(PRODUCTS)
                    .set(PRODUCTS.QUANTITY, PRODUCTS.QUANTITY.plus(qty))
                    .where(PRODUCTS.ID.eq(productId))
                    .execute();
        });
    }

    @Override
    public Map<UUID, Integer> getOrderItems(UUID orderId) {
        return db.select(PRODUCTS_IN_ORDERS.PRODUCT_ID, PRODUCTS_IN_ORDERS.QUANTITY)
                .from(PRODUCTS_IN_ORDERS)
                .where(PRODUCTS_IN_ORDERS.ORDER_ID.eq(orderId))
                .fetch()
                .intoMap(PRODUCTS_IN_ORDERS.PRODUCT_ID, PRODUCTS_IN_ORDERS.QUANTITY);
    }
}
