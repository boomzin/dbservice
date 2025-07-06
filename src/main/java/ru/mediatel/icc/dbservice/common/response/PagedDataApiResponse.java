package ru.mediatel.icc.dbservice.common.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import ru.mediatel.icc.dbservice.common.data.PagedResult;

import java.util.List;

public class PagedDataApiResponse<T> extends DataApiResponse<List<T>> {
    private final Meta meta;

    public PagedDataApiResponse(int code, PagedResult<T> page) {
        super(page.getItems());
        this.meta = new Meta(page);
    }

    public PagedDataApiResponse(PagedResult<T> pagedData) {
        this(HttpStatus.OK.value(), pagedData);
    }

    public PagedDataApiResponse(List<T> items, long itemsCount, long offset, long limit) {
        super(items);
        this.meta = new Meta(itemsCount, offset, limit);
    }

    public Meta getMeta() {
        return meta;
    }

    static class Meta {
        @JsonProperty
        private final long itemsCount;
        @JsonProperty
        private final long offset;
        @JsonProperty
        private final long limit;

        Meta(long itemsCount, long offset, long limit) {
            this.itemsCount = itemsCount;
            this.offset = offset;
            this.limit = limit;
        }

        Meta(PagedResult<?> page) {
            itemsCount = page.getItemsCount();
            offset = page.getOffset();
            limit = page.getLimit();
        }
    }

}
