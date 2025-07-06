package ru.mediatel.icc.dbservice.common.exception;

public enum ResponseStatus {
    SUCCESS(200),
    BAD_REQUEST(400),
    LOGIC_ERROR(-400001),
    OBJECT_NOT_FOUND(-400002),
    INVALID_STATE(-400003),
    NO_ENUM_CONSTANT(-400004),
    PERSISTENCE_LAYER_ACCESS_ERROR(-400010),
    CONVERSION_ERROR(-400021),
    SSO_INTERACTION(-400030),
    FORBIDDEN(403),
    NOT_AUTHENTICATED(401),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500),
    METHOD_NOT_ALLOWED(405),
    MEDIA_TYPE_NOT_SUPPORTED(406);

    private int value;

    ResponseStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
