/*
 * This file is generated by jOOQ.
 */
package ru.mediatel.icc.dbservice.db.generated.tables;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.annotation.processing.Generated;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.InverseForeignKey;
import org.jooq.Name;
import org.jooq.Path;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import ru.mediatel.icc.dbservice.db.generated.DefaultSchema;
import ru.mediatel.icc.dbservice.db.generated.Indexes;
import ru.mediatel.icc.dbservice.db.generated.Keys;
import ru.mediatel.icc.dbservice.db.generated.tables.Orders.OrdersPath;
import ru.mediatel.icc.dbservice.db.generated.tables.Products.ProductsPath;
import ru.mediatel.icc.dbservice.db.generated.tables.records.ProductsInOrdersRecord;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.19.24"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class ProductsInOrders extends TableImpl<ProductsInOrdersRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>products_in_orders</code>
     */
    public static final ProductsInOrders PRODUCTS_IN_ORDERS = new ProductsInOrders();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProductsInOrdersRecord> getRecordType() {
        return ProductsInOrdersRecord.class;
    }

    /**
     * The column <code>products_in_orders.order_id</code>.
     */
    public final TableField<ProductsInOrdersRecord, UUID> ORDER_ID = createField(DSL.name("order_id"), SQLDataType.UUID, this, "");

    /**
     * The column <code>products_in_orders.product_id</code>.
     */
    public final TableField<ProductsInOrdersRecord, UUID> PRODUCT_ID = createField(DSL.name("product_id"), SQLDataType.UUID, this, "");

    /**
     * The column <code>products_in_orders.quantity</code>.
     */
    public final TableField<ProductsInOrdersRecord, Integer> QUANTITY = createField(DSL.name("quantity"), SQLDataType.INTEGER.nullable(false), this, "");

    private ProductsInOrders(Name alias, Table<ProductsInOrdersRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private ProductsInOrders(Name alias, Table<ProductsInOrdersRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>products_in_orders</code> table reference
     */
    public ProductsInOrders(String alias) {
        this(DSL.name(alias), PRODUCTS_IN_ORDERS);
    }

    /**
     * Create an aliased <code>products_in_orders</code> table reference
     */
    public ProductsInOrders(Name alias) {
        this(alias, PRODUCTS_IN_ORDERS);
    }

    /**
     * Create a <code>products_in_orders</code> table reference
     */
    public ProductsInOrders() {
        this(DSL.name("products_in_orders"), null);
    }

    public <O extends Record> ProductsInOrders(Table<O> path, ForeignKey<O, ProductsInOrdersRecord> childPath, InverseForeignKey<O, ProductsInOrdersRecord> parentPath) {
        super(path, childPath, parentPath, PRODUCTS_IN_ORDERS);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    @Generated(
        value = {
            "https://www.jooq.org",
            "jOOQ version:3.19.24"
        },
        comments = "This class is generated by jOOQ"
    )
    public static class ProductsInOrdersPath extends ProductsInOrders implements Path<ProductsInOrdersRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> ProductsInOrdersPath(Table<O> path, ForeignKey<O, ProductsInOrdersRecord> childPath, InverseForeignKey<O, ProductsInOrdersRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private ProductsInOrdersPath(Name alias, Table<ProductsInOrdersRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public ProductsInOrdersPath as(String alias) {
            return new ProductsInOrdersPath(DSL.name(alias), this);
        }

        @Override
        public ProductsInOrdersPath as(Name alias) {
            return new ProductsInOrdersPath(alias, this);
        }

        @Override
        public ProductsInOrdersPath as(Table<?> alias) {
            return new ProductsInOrdersPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.IDX_PRODUCTS_IN_ORDERS_PRODUCT_ID);
    }

    @Override
    public List<UniqueKey<ProductsInOrdersRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.UNIQUE_ORDER_PRODUCT);
    }

    @Override
    public List<ForeignKey<ProductsInOrdersRecord, ?>> getReferences() {
        return Arrays.asList(Keys.PRODUCTS_IN_ORDERS__PRODUCTS_IN_ORDERS_ORDER_ID_FKEY, Keys.PRODUCTS_IN_ORDERS__PRODUCTS_IN_ORDERS_PRODUCT_ID_FKEY);
    }

    private transient OrdersPath _orders;

    /**
     * Get the implicit join path to the <code>orders</code> table.
     */
    public OrdersPath orders() {
        if (_orders == null)
            _orders = new OrdersPath(this, Keys.PRODUCTS_IN_ORDERS__PRODUCTS_IN_ORDERS_ORDER_ID_FKEY, null);

        return _orders;
    }

    private transient ProductsPath _products;

    /**
     * Get the implicit join path to the <code>products</code> table.
     */
    public ProductsPath products() {
        if (_products == null)
            _products = new ProductsPath(this, Keys.PRODUCTS_IN_ORDERS__PRODUCTS_IN_ORDERS_PRODUCT_ID_FKEY, null);

        return _products;
    }

    @Override
    public ProductsInOrders as(String alias) {
        return new ProductsInOrders(DSL.name(alias), this);
    }

    @Override
    public ProductsInOrders as(Name alias) {
        return new ProductsInOrders(alias, this);
    }

    @Override
    public ProductsInOrders as(Table<?> alias) {
        return new ProductsInOrders(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public ProductsInOrders rename(String name) {
        return new ProductsInOrders(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ProductsInOrders rename(Name name) {
        return new ProductsInOrders(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public ProductsInOrders rename(Table<?> name) {
        return new ProductsInOrders(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProductsInOrders where(Condition condition) {
        return new ProductsInOrders(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProductsInOrders where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProductsInOrders where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProductsInOrders where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public ProductsInOrders where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public ProductsInOrders where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public ProductsInOrders where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public ProductsInOrders where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProductsInOrders whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProductsInOrders whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
