package ru.tinkoff.edu.java.bot.controller;

import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.bot.dto.ApiErrorResponse;
import ru.tinkoff.edu.java.bot.exception.BadRequestException;

@RestControllerAdvice public class ExceptionBotApiHandler {

    @ExceptionHandler(BadRequestException.class) @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> badRequestException(final BadRequestException exception) {
        ArrayList<String> stacktrace = new ArrayList<>();
        for (StackTraceElement ste : exception.getStackTrace()) {
            stacktrace.add(ste.toString());
        }
        return ResponseEntity.status(exception.getStatusCode()).body(new ApiErrorResponse(
            exception.getDescription(),
            String.valueOf(exception.getStatusCode()),
            exception.getClass().getName(),
            exception.getMessage(),
            stacktrace
        ));
    }
}
