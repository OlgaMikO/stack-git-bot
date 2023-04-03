package ru.tinkoff.edu.java.bot;

public class DataBaseHolder {

    public static IDataBase get() {
        return DataBaseMock.getInstance();
    }
}
