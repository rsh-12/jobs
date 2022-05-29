package ru.rsh12.util.exception;
/*
 * Date: 03.04.2022
 * Time: 10:30 AM
 * */

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import ru.rsh12.api.exceptions.InvalidInputException;
import ru.rsh12.api.exceptions.NotFoundException;

import java.time.ZonedDateTime;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public HttpErrorInfo handleNotFoundException(ServerHttpRequest request, NotFoundException nfe) {

        return createHttpErrorInfo(NOT_FOUND, request, nfe);
    }

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidInputException.class)
    @ResponseBody
    public HttpErrorInfo handleInvalidInputException(ServerHttpRequest request, InvalidInputException iie) {
        return createHttpErrorInfo(UNPROCESSABLE_ENTITY, request, iie);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public HttpErrorInfo handleValidationError(ServerHttpRequest request, WebExchangeBindException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));

        return new HttpErrorInfo(
                ZonedDateTime.now(),
                request.getPath().pathWithinApplication().value(),
                BAD_REQUEST,
                message);
    }

    private HttpErrorInfo createHttpErrorInfo(
            HttpStatus httpStatus, ServerHttpRequest request, Exception ex) {

        String path = request.getPath().pathWithinApplication().value();

        return new HttpErrorInfo(
                ZonedDateTime.now(), path, httpStatus, ex.getMessage());
    }

}
