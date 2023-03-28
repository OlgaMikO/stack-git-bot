package ru.tinkoff.edu.java.parser;

import ru.tinkoff.edu.java.answer.ParserAnswer;
import ru.tinkoff.edu.java.answer.StackOverflowAnswer;

import java.net.URI;

public class StackOverflowParser extends LinkParser {

    public StackOverflowParser(LinkParser next){
        super(next);
    }

    public ParserAnswer parseLink(URI link){
        if(matchLink(link)){
            String[] path = link.getPath().split("/");
            return new StackOverflowAnswer(Long.parseLong(path[2]));
        }
        else{
            return this.getNext().parseLink(link);
        }
    }

    public boolean matchLink(URI link){
        return link.getAuthority().equals("stackoverflow.com");
    }
}
