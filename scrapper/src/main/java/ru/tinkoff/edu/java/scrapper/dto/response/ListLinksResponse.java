package ru.tinkoff.edu.java.scrapper.dto.response;

import lombok.AllArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
public class ListLinksResponse {

    private ArrayList<LinkResponse> links;

    private Long size;
}
