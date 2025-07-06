package ru.mediatel.icc.dbservice.common.data;

import java.util.List;

public class MetaData<T> {
    private final List<T> data;
    private final Meta meta;

    public MetaData(List<T> data, Meta meta) {
        this.data = data;
        this.meta = meta;
    }

    public List<T> getData() {
        return data;
    }

    public Meta getMeta() {
        return meta;
    }
}
