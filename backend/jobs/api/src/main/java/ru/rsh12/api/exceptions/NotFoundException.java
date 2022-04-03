package ru.rsh12.api.exceptions;
/*
 * Date: 03.04.2022
 * Time: 10:20 AM
 * */

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

}
