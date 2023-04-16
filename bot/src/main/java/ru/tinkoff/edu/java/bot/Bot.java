package ru.tinkoff.edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SetMyCommands;
import ru.tinkoff.edu.java.bot.processor.MessageProcessor;

public class Bot {
    private TelegramBot telegramBot;


    private MessageProcessor messageProcessor;


    public Bot(String token, MessageProcessor processor){
        messageProcessor = processor;
        messageProcessor.initCommandsMap();
        BotCommand[] array = new BotCommand[messageProcessor.commands().size()];
        for(int i = 0; i < array.length; i++){
            array[i] = messageProcessor.commands().get(i).toApiCommand();
        }
        SetMyCommands commands = new SetMyCommands(array);
        telegramBot = new TelegramBot(token);
        telegramBot.execute(commands);

        telegramBot.setUpdatesListener(updates -> {
            for(Update update : updates){
                if(update.message() != null)
                {
                    telegramBot.execute(messageProcessor.process(update));
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
