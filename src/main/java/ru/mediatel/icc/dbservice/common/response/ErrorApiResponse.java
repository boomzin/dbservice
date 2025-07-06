package ru.mediatel.icc.dbservice.common.response;



import ru.mediatel.icc.dbservice.common.exception.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

public class ErrorApiResponse implements ApiResponse {
    private final int status;
    private final String message;
    private Map<String, String> errors = new HashMap<>();

    public ErrorApiResponse(ResponseStatus status, String message) {
        this(status.getValue(), message);
    }

    public ErrorApiResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ErrorApiResponse(int status, String message, Map<String, String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ErrorApiResponse(ResponseStatus code, String message, Map<String, String> errors) {
        this(code.getValue(), message, errors);
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
