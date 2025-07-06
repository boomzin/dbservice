package ru.mediatel.icc.dbservice.common.search;

import org.jooq.Condition;
import org.jooq.Field;

import java.util.function.BiFunction;

public class FieldFilterDescriptionImpl<T> implements FieldFilterDescription {
    private final Field<T> field;
    private final BiFunction<Field<T>, String, Condition> conditionFunc;

    public FieldFilterDescriptionImpl(Field<T> field, BiFunction<Field<T>, String, Condition> conditionFunc) {
        this.field = field;
        this.conditionFunc = conditionFunc;
    }

    @Override
    public Condition getCondition(String value) {
        return conditionFunc.apply(this.field, value);
    }
}
