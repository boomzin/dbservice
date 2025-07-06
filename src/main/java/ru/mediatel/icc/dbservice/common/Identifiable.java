package ru.mediatel.icc.dbservice.common;


import java.io.Serializable;

public interface Identifiable<T extends Serializable> {
    T getId();
}
