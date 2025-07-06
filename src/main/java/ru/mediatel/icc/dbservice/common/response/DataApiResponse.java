package ru.mediatel.icc.dbservice.common.response;


import ru.mediatel.icc.dbservice.common.exception.ResponseStatus;

public class DataApiResponse<T> extends SuccessStatusApiResponse {
    private T data;

    public DataApiResponse(T data) {
        super(ResponseStatus.SUCCESS);
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
