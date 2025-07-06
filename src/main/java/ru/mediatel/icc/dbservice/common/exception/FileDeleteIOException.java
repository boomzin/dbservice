package ru.mediatel.icc.dbservice.common.exception;


public class FileDeleteIOException extends RuntimeException {

    public FileDeleteIOException(Exception cause) {
        super(cause);
    }
}
