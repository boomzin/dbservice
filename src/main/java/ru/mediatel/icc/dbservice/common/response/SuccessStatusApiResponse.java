package ru.mediatel.icc.dbservice.common.response;


import ru.mediatel.icc.dbservice.common.exception.ResponseStatus;

public class SuccessStatusApiResponse extends StatusApiResponse {
    public static SuccessStatusApiResponse SUCCESS = new SuccessStatusApiResponse(ResponseStatus.SUCCESS);

    public SuccessStatusApiResponse(ResponseStatus status) {
        super(status.getValue(), true);
    }
}
