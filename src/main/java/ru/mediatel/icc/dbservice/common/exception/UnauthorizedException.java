package ru.mediatel.icc.dbservice.common.exception;

public class UnauthorizedException extends DomainException {
    public UnauthorizedException() {
        super(ResponseStatus.NOT_AUTHENTICATED, "Unauthorized");
    }
}
