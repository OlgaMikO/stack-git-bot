package ru.tinkoff.edu.java.scrapper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Link {

    private Long id;

    private String url;

    private Long chat;

}
