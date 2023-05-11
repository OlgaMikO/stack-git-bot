package ru.tinkoff.edu.java.bot.dto;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import lombok.AllArgsConstructor;

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
