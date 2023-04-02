package ru.tinkoff.edu.java.bot;

import java.util.ArrayList;
import java.util.HashMap;

public class DataBase {

    HashMap<Long, ArrayList<String>> db;
    private DataBase(){
        db = new HashMap<>();
    }

    private static DataBase instance;

    public static DataBase getInstance(){
        if(instance == null){
            instance = new DataBase();
        }
        return instance;
    }

    public ArrayList<String> getLinkList(Long user){
        return db.get(user);
    }

    public void addUser(Long user){
        db.put(user, new ArrayList<>());
    }

    public void addLink(Long user, String link){
        ArrayList<String> links = db.get(user);
        links.add(link);
        db.put(user, links);
    }

    public void deleteLink(Long user, String link){
        ArrayList<String> links = db.get(user);
        links.remove(link);
        db.put(user, links);
    }

    public boolean containUser(Long user){
        return db.containsKey(user);
    }
}
