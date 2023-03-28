package ru.tinkoff.edu.java.scrapper.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundScrapperException extends RuntimeException{

    private final int statusCode = 400;

    private String description;

    public NotFoundScrapperException(String description){
        super(description);
        this.description = description;
    }
}
