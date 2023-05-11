package ru.tinkoff.edu.java.bot;

public final class DataBaseHolder {

    private DataBaseHolder() {
    }

    public static IDataBase get() {
        return DataBaseMock.getInstance();
    }
}
