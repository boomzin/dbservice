package ru.mediatel.icc.dbservice.common.search;

import lombok.Builder;
import lombok.Getter;
import org.jooq.Condition;
import org.jooq.Field;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@Builder
@Getter
public class SearchCriteriaSettings {
    private static final int DEFAULT_LIMIT = 100;

    @lombok.NonNull
    private Map<String, FieldFilterDescription> availableFilters;
    @lombok.NonNull
    private Map<String, Field<?>> availableOrders;

    private Integer defaultLimit;

    public int getDefaultLimit() {
        if (defaultLimit != null) {
            return defaultLimit;
        }
        return DEFAULT_LIMIT;
    }

    public static class SearchCriteriaSettingsBuilder {
        private final Map<String, FieldFilterDescription> availableFilters = new HashMap<>();
        private final Map<String, Field<?>> availableOrders = new HashMap<>();

        public SearchCriteriaSettingsBuilder filter(String key, FieldFilterDescription value) {
            this.availableFilters.put(key, value);
            return this;
        }

        public <T> SearchCriteriaSettingsBuilder filter(String key, Field<T> field, BiFunction<Field<T>, String, Condition> conditionFunc) {
            this.availableFilters.put(key, new FieldFilterDescriptionImpl<>(field, conditionFunc));
            return this;
        }

        public <T> SearchCriteriaSettingsBuilder filter(String key, Field<T> field, BiFunction<Field<T>, String, Condition> conditionFunc,
                                                        boolean ordered) {
            this.availableFilters.put(key, new FieldFilterDescriptionImpl<>(field, conditionFunc));
            if (ordered) {
                this.availableOrders.put(key, field);
            }
            return this;
        }

        public SearchCriteriaSettingsBuilder order(String key, Field<?> value) {
            this.availableOrders.put(key, value);
            return this;
        }

        /**
         * override lombok generation. Not used
         */
        private SearchCriteriaSettingsBuilder availableFilters(Map<String, FieldFilterDescription> availableFilters) {
            // not used
            return this;
        }

        /**
         * override lombok generation. Not used
         */
        private SearchCriteriaSettingsBuilder availableOrders(Map<String, Field<?>> availableOrders) {
            // not used
            return this;
        }
    }
}
