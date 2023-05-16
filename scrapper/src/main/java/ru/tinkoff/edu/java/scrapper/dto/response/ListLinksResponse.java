package ru.tinkoff.edu.java.scrapper.dto.response;

import java.util.ArrayList;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ListLinksResponse {

    private ArrayList<LinkResponse> links;

    private Long size;
}
