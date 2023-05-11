package ru.tinkoff.edu.java.scrapper.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private final int statusCode = 400;

    private final String description;

    public BadRequestException(String description) {
        super(description);
        this.description = description;
    }
}
