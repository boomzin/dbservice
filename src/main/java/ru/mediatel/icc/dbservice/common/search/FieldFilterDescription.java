package ru.mediatel.icc.dbservice.common.search;

import org.jooq.Condition;

public interface FieldFilterDescription {
    Condition getCondition(String value);
}
