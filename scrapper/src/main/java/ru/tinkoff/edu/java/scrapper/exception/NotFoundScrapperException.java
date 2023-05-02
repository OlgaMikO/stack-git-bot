package ru.tinkoff.edu.java.scrapper.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundScrapperException extends RuntimeException {

    private final int statusCode = 400;

    private String description = "Пользователь не найден";

    private Long id;

    public NotFoundScrapperException(String description) {
        super(description);
        this.description = description;
    }

    public NotFoundScrapperException(Long id) {
        this.id = id;
    }

}
