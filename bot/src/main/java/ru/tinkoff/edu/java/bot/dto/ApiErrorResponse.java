package ru.tinkoff.edu.java.bot.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import java.util.ArrayList;

@AllArgsConstructor
public class ApiErrorResponse {
    @NotNull
    private String description;

    @NotNull
    private String code;

    @NotNull
    private String exceptionName;

    @NotNull
    private String exceptionMessage;

    @NotNull
    private ArrayList<String> stacktrace;

}
