/*
 * This file is generated by jOOQ.
 */
package ru.mediatel.icc.dbservice.db.generated.enums;


import javax.annotation.processing.Generated;

import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;

import ru.mediatel.icc.dbservice.db.generated.DefaultSchema;


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
public enum InteractionStatus implements EnumType {

    RECALL("RECALL"),

    CANCEL_ORDER("CANCEL_ORDER"),

    RESCHEDULE("RESCHEDULE");

    private final String literal;

    private InteractionStatus(String literal) {
        this.literal = literal;
    }

    @Override
    public Catalog getCatalog() {
        return getSchema().getCatalog();
    }

    @Override
    public Schema getSchema() {
        return DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public String getName() {
        return "interaction_status";
    }

    @Override
    public String getLiteral() {
        return literal;
    }

    /**
     * Lookup a value of this EnumType by its literal. Returns
     * <code>null</code>, if no such value could be found, see {@link
     * EnumType#lookupLiteral(Class, String)}.
     */
    public static InteractionStatus lookupLiteral(String literal) {
        return EnumType.lookupLiteral(InteractionStatus.class, literal);
    }
}
