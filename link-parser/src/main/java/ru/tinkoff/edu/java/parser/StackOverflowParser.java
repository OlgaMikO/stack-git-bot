package ru.tinkoff.edu.java.parser;

import java.net.URI;

public class StackOverflowParser extends LinkParser{

    public StackOverflowParser(LinkParser next){
        super(next);
    }

    public ParserAnswer parseLink(URI link){
        if(matchLink(link)){
            String[] path = link.getPath().split("/");
            return new StackOverflowAnswer(Long.parseLong(path[2]));
        }
        else{
            if(this.getNext() != null){
                return this.getNext().parseLink(link);
            }
        }
        return null;
    }

    public boolean matchLink(URI link){
        if(link.getAuthority().equals("stackoverflow.com")){
            return true;
        }
        return false;
    }
}
