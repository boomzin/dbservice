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
import ru.mediatel.icc.dbservice.db.generated.tables.records.ProductsRecord;
import ru.mediatel.icc.dbservice.model.product.Product;
import ru.mediatel.icc.dbservice.model.product.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static ru.mediatel.icc.dbservice.common.search.JooqSearchUtils.*;
import static ru.mediatel.icc.dbservice.db.generated.Tables.PRODUCTS;
import static ru.mediatel.icc.dbservice.db.generated.Tables.PRODUCT_RESERVATIONS;


@Repository
public class JooqProductRepository implements ProductRepository {
    private final DSLContext db;
    private final SearchCriteriaSettings criteriaSettings;

    private final RecordMapper<ProductsRecord, Product> mapper = r -> new Product(
            r.getId(),
            r.getQuantity(),
            r.getPrice(),
            r.getDescription()
    );


    public JooqProductRepository(DSLContext db) {
        this.db = db;
        this.criteriaSettings = SearchCriteriaSettings.builder()
                .filter("quantity_ge", PRODUCTS.QUANTITY, INT_MIN_EQ_STR)
                .filter("quantity_le", PRODUCTS.QUANTITY, INT_MAX_EQ_STR)
                .filter("price_le", PRODUCTS.PRICE, (f, v) -> f.le(new BigDecimal(v)))
                .filter("price_ge", PRODUCTS.PRICE, (f, v) -> f.ge(new BigDecimal(v)))
                .filter("description", PRODUCTS.DESCRIPTION, STR_LIKE_IC)

                .order("price", PRODUCTS.PRICE)
                .order("quantity", PRODUCTS.QUANTITY)

                .build();
    }


    @Override
    public void create(Product product) {
        ProductsRecord record = db.newRecord(PRODUCTS);
        fillRecord(record, product);
        record.insert();
    }

    @Override
    public void update(Product product) {
        ProductsRecord record = db.fetchOptional(
                PRODUCTS,
                PRODUCTS.ID.eq(product.getId()))
                .orElseThrow(() -> new ObjectNotFoundException(product.getId(), "Product"));
        fillRecord(record, product);
        record.store();

    }

    @Override
    public Product findById(UUID productIs) {
        return db
                .selectFrom(PRODUCTS)
                .where(PRODUCTS.ID.eq(productIs))
                .fetchOptional(mapper)
                .orElseThrow(() -> new ObjectNotFoundException(productIs, "Product"));
    }


    @Override
    public PagedResult<Product> search(Map<String, String> apiParams) {
        SearchCriteria criteria = SearchCriteria.getInstance(criteriaSettings, apiParams);
        SelectWhereStep<?> step = db.selectFrom(PRODUCTS);
        criteria.apply(step);

        List<Product> list = step.fetch().map(record -> mapper.map((ProductsRecord) record));

        SelectWhereStep<?> countStep = db.selectCount().from(PRODUCTS);

        criteria.applyForCount(countStep);
        int itemsCount = countStep.fetchOptionalInto(Integer.class).orElse(0);

        return new PagedResult<>(list, itemsCount, criteria.getOffset(), criteria.getLimit());
    }

    @Override
    public void delete(UUID productId) {
        db.deleteFrom(PRODUCTS)
                .where(PRODUCTS.ID.eq(productId))
                .execute();
    }

    @Override
    public int availableAmount(UUID productId) {
        ProductsRecord product = db.selectFrom(PRODUCTS)
                .where(PRODUCTS.ID.eq(productId))
                .fetchOptional()
                .orElseThrow(() -> new ObjectNotFoundException(productId, "Product"));

        int reserved = db.select(DSL.coalesce(PRODUCT_RESERVATIONS.RESERVED_QUANTITY, 0))
                .from(PRODUCT_RESERVATIONS)
                .where(PRODUCT_RESERVATIONS.PRODUCT_ID.eq(productId))
                .fetchOptionalInto(Integer.class)
                .orElse(0);

        return product.getQuantity() - reserved;
    }

    @Override
    public boolean isAvailable(UUID productId, int requestedQuantity) {
        ProductsRecord product = db.selectFrom(PRODUCTS)
                .where(PRODUCTS.ID.eq(productId))
                .fetchOptional()
                .orElseThrow(() -> new ObjectNotFoundException(productId, "Product"));

        int reserved = db.select(DSL.coalesce(PRODUCT_RESERVATIONS.RESERVED_QUANTITY, 0))
                .from(PRODUCT_RESERVATIONS)
                .where(PRODUCT_RESERVATIONS.PRODUCT_ID.eq(productId))
                .fetchOptionalInto(Integer.class)
                .orElse(0);

        int available = product.getQuantity() - reserved;
        return available >= requestedQuantity;
    }

    private void fillRecord(ProductsRecord record, Product product) {
        record.setId(product.getId());
        record.setPrice(product.getPrice());
        record.setQuantity(product.getQuantity());
        record.setDescription(product.getDescription());
    }
}
