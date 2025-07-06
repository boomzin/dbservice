package ru.mediatel.icc.dbservice.common.exception;


import static ru.mediatel.icc.dbservice.common.exception.ResponseStatus.LOGIC_ERROR;

public class LoginBlockedException extends DomainException {

    public LoginBlockedException() {
        super(LOGIC_ERROR, "LOGIN_BLOCKED");
    }
}
