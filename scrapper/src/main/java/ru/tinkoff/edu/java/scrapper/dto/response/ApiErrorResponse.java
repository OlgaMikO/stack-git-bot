package ru.tinkoff.edu.java.scrapper.dto.response;

import lombok.AllArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
public class ApiErrorResponse {

    private String description;

    private String code;

    private String exceptionName;

    private String exceptionMessage;

    private ArrayList<String> stacktrace;

}

