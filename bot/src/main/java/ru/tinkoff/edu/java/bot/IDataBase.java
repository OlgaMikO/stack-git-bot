package ru.tinkoff.edu.java.bot;

import java.util.ArrayList;

public interface IDataBase {

    ArrayList<String> getLinkList(Long user);

    void addUser(Long user);

    void addLink(Long user, String link);

    void deleteLink(Long user, String link);

    boolean containUser(Long user);
}
