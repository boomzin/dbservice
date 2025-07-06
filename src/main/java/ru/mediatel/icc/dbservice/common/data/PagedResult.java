package ru.mediatel.icc.dbservice.common.data;

import java.util.List;

public class PagedResult<T> extends AssertionConcern {
    private List<T> items;
    private long itemsCount;
    private long offset;
    private long limit;

    public PagedResult(List<T> items, long itemsCount, long offset, long limit) {
        setItems(items);
        setItemsCount(itemsCount);
        setLimit(limit);
        setOffset(offset);
    }

    public List<T> getItems() {
        return items;
    }

    private void setItems(List<T> items) {
        assertNotNull(items, "Items may not be null");
        this.items = items;
    }

    public long getItemsCount() {
        return itemsCount;
    }

    private void setItemsCount(long itemsCount) {
        assertMin(0, itemsCount, "Items count may not be negative");
        this.itemsCount = itemsCount;
    }

    public long getOffset() {
        return offset;
    }

    private void setOffset(long offset) {
        assertMin(0, offset, "Offset may not be negative");
        this.offset = offset;
    }

    public long getLimit() {
        return limit;
    }

    private void setLimit(long limit) {
        assertMin(1, limit, "Limit may not be less than 1");
        this.limit = limit;
    }
}
