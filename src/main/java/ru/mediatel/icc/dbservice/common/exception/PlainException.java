package ru.mediatel.icc.dbservice.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class PlainException extends RuntimeException {

    @Getter
    private HttpStatus status = HttpStatus.BAD_REQUEST;

    public PlainException(String message) {
        super(message);
    }

    public PlainException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
