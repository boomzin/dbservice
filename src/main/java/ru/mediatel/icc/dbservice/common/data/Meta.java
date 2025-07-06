package ru.mediatel.icc.dbservice.common.data;

public class Meta {
    private final int limit;
    private final int offset;
    private final int count;

    public Meta(int limit, int offset, int count) {
        this.limit = limit;
        this.offset = offset;
        this.count = count;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public int getCount() {
        return count;
    }
}
