package ru.mediatel.icc.dbservice.common.exception;


import java.io.Serializable;
import java.util.function.Supplier;

public class ObjectNotFoundException extends DomainException {
    public <K extends Serializable> ObjectNotFoundException(K id, String discriminator) {
        super(ResponseStatus.OBJECT_NOT_FOUND, String.format("%s with id %s not found", discriminator, id));
    }

    public static <K extends Serializable> Supplier<ObjectNotFoundException> lambda(K id, String discriminator) {
        return () -> new ObjectNotFoundException(id, discriminator);
    }
}
