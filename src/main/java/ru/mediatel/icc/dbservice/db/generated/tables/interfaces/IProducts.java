/*
 * This file is generated by jOOQ.
 */
package ru.mediatel.icc.dbservice.db.generated.tables.interfaces;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import javax.annotation.processing.Generated;


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
public interface IProducts extends Serializable {

    /**
     * Setter for <code>products.id</code>.
     */
    public void setId(UUID value);

    /**
     * Getter for <code>products.id</code>.
     */
    public UUID getId();

    /**
     * Setter for <code>products.quantity</code>.
     */
    public void setQuantity(Integer value);

    /**
     * Getter for <code>products.quantity</code>.
     */
    public Integer getQuantity();

    /**
     * Setter for <code>products.price</code>.
     */
    public void setPrice(BigDecimal value);

    /**
     * Getter for <code>products.price</code>.
     */
    public BigDecimal getPrice();

    /**
     * Setter for <code>products.description</code>.
     */
    public void setDescription(String value);

    /**
     * Getter for <code>products.description</code>.
     */
    public String getDescription();

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    /**
     * Load data from another generated Record/POJO implementing the common
     * interface IProducts
     */
    public void from(IProducts from);

    /**
     * Copy data into another generated Record/POJO implementing the common
     * interface IProducts
     */
    public <E extends IProducts> E into(E into);
}
