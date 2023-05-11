package ru.tinkoff.edu.java.scrapper.dto.response;

import java.util.ArrayList;
import lombok.Data;

@Data
public class StackOverflowResponseItems {

    private ArrayList<StackOverflowResponse> items;
}
