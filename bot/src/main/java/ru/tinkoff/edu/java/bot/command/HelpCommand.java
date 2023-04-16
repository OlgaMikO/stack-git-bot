package ru.tinkoff.edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class HelpCommand implements Command{

    private String responseMessage;

    public HelpCommand(){
        responseMessage = command() + " - " + description();
    }
    public HelpCommand(String message){
        responseMessage = message;
    }
    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "вывести окно с командами";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), responseMessage);
    }

    @Override
    public boolean supports(Update update) {
        return true;
    }
}
