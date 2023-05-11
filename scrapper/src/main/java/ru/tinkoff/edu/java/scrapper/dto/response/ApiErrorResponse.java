package ru.tinkoff.edu.java.scrapper.dto.response;

import java.util.ArrayList;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApiErrorResponse {

    private String description;

    private String code;

    private String exceptionName;

    private String exceptionMessage;

    private ArrayList<String> stacktrace;

}

