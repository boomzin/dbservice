/*
 * This file is generated by jOOQ.
 */
package ru.mediatel.icc.dbservice.db.generated;


import javax.annotation.processing.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;

import ru.mediatel.icc.dbservice.db.generated.tables.Orders;
import ru.mediatel.icc.dbservice.db.generated.tables.ProductsInCarts;
import ru.mediatel.icc.dbservice.db.generated.tables.ProductsInOrders;


/**
 * A class modelling indexes of tables in the default schema.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.19.24"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index IDX_ORDERS_STATUS = Internal.createIndex(DSL.name("idx_orders_status"), Orders.ORDERS, new OrderField[] { Orders.ORDERS.STATUS }, false);
    public static final Index IDX_PRODUCTS_IN_CARTS_PRODUCT_ID = Internal.createIndex(DSL.name("idx_products_in_carts_product_id"), ProductsInCarts.PRODUCTS_IN_CARTS, new OrderField[] { ProductsInCarts.PRODUCTS_IN_CARTS.PRODUCT_ID }, false);
    public static final Index IDX_PRODUCTS_IN_ORDERS_PRODUCT_ID = Internal.createIndex(DSL.name("idx_products_in_orders_product_id"), ProductsInOrders.PRODUCTS_IN_ORDERS, new OrderField[] { ProductsInOrders.PRODUCTS_IN_ORDERS.PRODUCT_ID }, false);
}
