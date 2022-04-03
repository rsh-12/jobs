package ru.rsh12.util;
/*
 * Date: 03.04.2022
 * Time: 10:32 AM
 * */

import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;

public record HttpErrorInfo(ZonedDateTime timestamp, String path,
                            HttpStatus httpStatus,
                            String message) {

}
