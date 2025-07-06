package ru.mediatel.icc.dbservice.common.search;

import lombok.Getter;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.jooq.SortField;
import org.jooq.SortOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SearchCriteria {
    private final SearchCriteriaSettings criteriaSettings;

    private final Map<String, String> filters;
    private final List<String> order;
    @Getter
    private final int limit;
    @Getter
    private final int offset;

    private SearchCriteria(SearchCriteriaSettings criteriaSettings,
                          Map<String, String> filters, List<String> order, int limit, int offset) {
        this.criteriaSettings = criteriaSettings;
        this.filters = filters;
        this.order = order;
        this.limit = limit;
        this.offset = offset;
    }

    public static SearchCriteria getInstance(SearchCriteriaSettings criteriaSettings, Map<String, String> apiParams) {
        int limit = criteriaSettings.getDefaultLimit();
        int offset = 0;
        List<String> order = new ArrayList<>();

        for (var entry : apiParams.entrySet()) {
            if (entry.getValue() != null) {
                switch (entry.getKey()) {
                    case "limit" -> {
                        limit = Integer.parseInt(entry.getValue());
                        if (limit == 0) {
                            limit = criteriaSettings.getDefaultLimit();
                        }
                    }
                    case "offset" -> offset = Integer.parseInt(entry.getValue());
                    case "order" -> order.addAll(Arrays.stream(entry.getValue().split(",")).filter((str) -> !str.isBlank()).toList());
                }
            }
        }
        Map<String, String> filters = new HashMap<>(apiParams);
        filters.remove("limit");
        filters.remove("offset");
        filters.remove("order");

        return new SearchCriteria(criteriaSettings, filters, order, limit, offset);
    }

    public <T extends Record> void apply(SelectWhereStep<T> step) {
        Condition condition = getSelectCondition();
        if (condition != null) {
            step.where(condition);
        }

        step.orderBy(getSelectOrder());
        step.offset(offset);
        step.limit(limit);
    }

    public <T extends Record> void applyForCount(SelectWhereStep<T> step) {
        Condition condition = getSelectCondition();
        if (condition != null) {
            step.where(condition);
        }
    }

    private List<SortField<?>> getSelectOrder() {
        var availableOrders = criteriaSettings.getAvailableOrders();
        return order.stream()
                .filter(o -> !o.isBlank())
                .map(this::fieldToFieldOrder)
                .filter(o -> availableOrders.containsKey(o.fieldName))
                .map(o -> availableOrders.get(o.fieldName).sort(o.order))
                .collect(Collectors.toList());
    }

    private FieldOrder fieldToFieldOrder(String order) {
        SortOrder sortOrder;
        String fieldName;

        if(order.startsWith("-")) {
            sortOrder = SortOrder.DESC;
            fieldName = order.substring(1);
        } else {
            sortOrder = SortOrder.ASC;
            fieldName = order;
        }
        return new FieldOrder(fieldName, sortOrder);
    }

    private Condition getSelectCondition() {
        Condition res = null;
        for (Condition c : getConditions()) {
            res = andCondition(res, c);
        }
        return res;
    }

    private List<Condition> getConditions() {
        List<Condition> conditions = new ArrayList<>();
        criteriaSettings.getAvailableFilters().forEach((field, desc) -> {
            String value = filters.get(field);
            if (value != null) {
                conditions.add(desc.getCondition(value));
            }
        });
        return Collections.unmodifiableList(conditions);
    }

    private Condition andCondition(Condition target, Condition condition) {
        if (target == null) {
            return condition;
        } else {
            return target.and(condition);
        }
    }

    public record FieldOrder(String fieldName, SortOrder order) {
    }
}
