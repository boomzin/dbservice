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
import ru.mediatel.icc.dbservice.db.generated.tables.Carts.CartsPath;
import ru.mediatel.icc.dbservice.db.generated.tables.Products.ProductsPath;
import ru.mediatel.icc.dbservice.db.generated.tables.records.ProductsInCartsRecord;


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
public class ProductsInCarts extends TableImpl<ProductsInCartsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>products_in_carts</code>
     */
    public static final ProductsInCarts PRODUCTS_IN_CARTS = new ProductsInCarts();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProductsInCartsRecord> getRecordType() {
        return ProductsInCartsRecord.class;
    }

    /**
     * The column <code>products_in_carts.cart_id</code>.
     */
    public final TableField<ProductsInCartsRecord, UUID> CART_ID = createField(DSL.name("cart_id"), SQLDataType.UUID, this, "");

    /**
     * The column <code>products_in_carts.product_id</code>.
     */
    public final TableField<ProductsInCartsRecord, UUID> PRODUCT_ID = createField(DSL.name("product_id"), SQLDataType.UUID, this, "");

    /**
     * The column <code>products_in_carts.quantity</code>.
     */
    public final TableField<ProductsInCartsRecord, Integer> QUANTITY = createField(DSL.name("quantity"), SQLDataType.INTEGER.nullable(false), this, "");

    private ProductsInCarts(Name alias, Table<ProductsInCartsRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private ProductsInCarts(Name alias, Table<ProductsInCartsRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>products_in_carts</code> table reference
     */
    public ProductsInCarts(String alias) {
        this(DSL.name(alias), PRODUCTS_IN_CARTS);
    }

    /**
     * Create an aliased <code>products_in_carts</code> table reference
     */
    public ProductsInCarts(Name alias) {
        this(alias, PRODUCTS_IN_CARTS);
    }

    /**
     * Create a <code>products_in_carts</code> table reference
     */
    public ProductsInCarts() {
        this(DSL.name("products_in_carts"), null);
    }

    public <O extends Record> ProductsInCarts(Table<O> path, ForeignKey<O, ProductsInCartsRecord> childPath, InverseForeignKey<O, ProductsInCartsRecord> parentPath) {
        super(path, childPath, parentPath, PRODUCTS_IN_CARTS);
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
    public static class ProductsInCartsPath extends ProductsInCarts implements Path<ProductsInCartsRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> ProductsInCartsPath(Table<O> path, ForeignKey<O, ProductsInCartsRecord> childPath, InverseForeignKey<O, ProductsInCartsRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private ProductsInCartsPath(Name alias, Table<ProductsInCartsRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public ProductsInCartsPath as(String alias) {
            return new ProductsInCartsPath(DSL.name(alias), this);
        }

        @Override
        public ProductsInCartsPath as(Name alias) {
            return new ProductsInCartsPath(alias, this);
        }

        @Override
        public ProductsInCartsPath as(Table<?> alias) {
            return new ProductsInCartsPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.IDX_PRODUCTS_IN_CARTS_PRODUCT_ID);
    }

    @Override
    public List<UniqueKey<ProductsInCartsRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.UNIQUE_CART_PRODUCT);
    }

    @Override
    public List<ForeignKey<ProductsInCartsRecord, ?>> getReferences() {
        return Arrays.asList(Keys.PRODUCTS_IN_CARTS__PRODUCTS_IN_CARTS_CART_ID_FKEY, Keys.PRODUCTS_IN_CARTS__PRODUCTS_IN_CARTS_PRODUCT_ID_FKEY);
    }

    private transient CartsPath _carts;

    /**
     * Get the implicit join path to the <code>carts</code> table.
     */
    public CartsPath carts() {
        if (_carts == null)
            _carts = new CartsPath(this, Keys.PRODUCTS_IN_CARTS__PRODUCTS_IN_CARTS_CART_ID_FKEY, null);

        return _carts;
    }

    private transient ProductsPath _products;

    /**
     * Get the implicit join path to the <code>products</code> table.
     */
    public ProductsPath products() {
        if (_products == null)
            _products = new ProductsPath(this, Keys.PRODUCTS_IN_CARTS__PRODUCTS_IN_CARTS_PRODUCT_ID_FKEY, null);

        return _products;
    }

    @Override
    public ProductsInCarts as(String alias) {
        return new ProductsInCarts(DSL.name(alias), this);
    }

    @Override
    public ProductsInCarts as(Name alias) {
        return new ProductsInCarts(alias, this);
    }

    @Override
    public ProductsInCarts as(Table<?> alias) {
        return new ProductsInCarts(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public ProductsInCarts rename(String name) {
        return new ProductsInCarts(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ProductsInCarts rename(Name name) {
        return new ProductsInCarts(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public ProductsInCarts rename(Table<?> name) {
        return new ProductsInCarts(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProductsInCarts where(Condition condition) {
        return new ProductsInCarts(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProductsInCarts where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProductsInCarts where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProductsInCarts where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public ProductsInCarts where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public ProductsInCarts where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public ProductsInCarts where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public ProductsInCarts where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProductsInCarts whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProductsInCarts whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
