package ru.mediatel.icc.dbservice.common.exception;

public class DomainException extends RuntimeException {
    private int status;
    private String message;

    public DomainException(ResponseStatus status, String message) {
        this(status.getValue(), message);
    }

    public DomainException(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
