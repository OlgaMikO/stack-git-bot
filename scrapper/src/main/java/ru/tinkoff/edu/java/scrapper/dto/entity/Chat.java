package ru.tinkoff.edu.java.scrapper.dto.entity;

import lombok.Data;

@Data
public class Chat {

    private Long id;

    public Chat(Long id) {
        this.id = id;
    }


}
