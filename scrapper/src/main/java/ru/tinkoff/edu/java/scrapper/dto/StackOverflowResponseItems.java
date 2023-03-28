package ru.tinkoff.edu.java.scrapper.dto;

import lombok.Data;

import java.util.ArrayList;
@Data
public class StackOverflowResponseItems {

    private ArrayList<StackOverflowResponse> items;
}
