package ru.tinkoff.edu.java.parser;

import java.net.URI;

public class GitHubParser extends LinkParser{

    public GitHubParser(LinkParser next){
        super(next);
    }

    public ParserAnswer parseLink(URI link){
        if(matchLink(link)){
            String[] path = link.getPath().split("/");
            return new GitHubAnswer(path[1], path[2]);
        }
        else{
            if(this.getNext() != null){
                return this.getNext().parseLink(link);
            }
        }
        return null;
    }

    public boolean matchLink(URI link){
        if(link.getAuthority().equals("github.com")){
            return true;
        }
        return false;
    }

}
