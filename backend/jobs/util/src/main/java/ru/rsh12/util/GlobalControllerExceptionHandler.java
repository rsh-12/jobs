package ru.rsh12.util;
/*
 * Date: 03.04.2022
 * Time: 10:30 AM
 * */

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.rsh12.api.exceptions.InvalidInputException;
import ru.rsh12.api.exceptions.NotFoundException;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public HttpErrorInfo handleNotFoundException(
            ServerHttpRequest request, NotFoundException nfe) {

        return createHttpErrorInfo(NOT_FOUND, request, nfe);
    }

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidInputException.class)
    @ResponseBody
    public HttpErrorInfo handleInvalidInputException(
            ServerHttpRequest request, InvalidInputException iie) {

        return createHttpErrorInfo(UNPROCESSABLE_ENTITY, request, iie);
    }

    private HttpErrorInfo createHttpErrorInfo(
            HttpStatus httpStatus, ServerHttpRequest request, Exception ex) {

        String path = request.getPath().pathWithinApplication().value();

        return new HttpErrorInfo(
                ZonedDateTime.now(), path, httpStatus, ex.getMessage());
    }

}
