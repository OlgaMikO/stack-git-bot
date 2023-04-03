package ru.tinkoff.edu.java.bot;

import java.util.ArrayList;
import java.util.HashMap;

public class DataBaseMock implements IDataBase {

    HashMap<Long, ArrayList<String>> db;
    private DataBaseMock(){
        db = new HashMap<>();
    }

    private static DataBaseMock instance;

    public static DataBaseMock getInstance(){
        if(instance == null){
            instance = new DataBaseMock();
        }
        return instance;
    }

    @Override
    public ArrayList<String> getLinkList(Long user){
        return db.get(user);
    }

    @Override
    public void addUser(Long user){
        db.put(user, new ArrayList<>());
    }

    @Override
    public void addLink(Long user, String link){
        ArrayList<String> links = db.get(user);
        links.add(link);
        db.put(user, links);
    }

    @Override
    public void deleteLink(Long user, String link){
        ArrayList<String> links = db.get(user);
        links.remove(link);
        db.put(user, links);
    }

    @Override
    public boolean containUser(Long user){
        return db.containsKey(user);
    }
}
