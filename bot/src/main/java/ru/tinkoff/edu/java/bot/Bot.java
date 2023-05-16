package ru.tinkoff.edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.processor.MessageProcessor;
import ru.tinkoff.edu.java.bot.service.MessageCount;

@Component
public class Bot {
    private final TelegramBot telegramBot;

    private final MessageProcessor messageProcessor;

    private final MessageCount messageCount;

    public Bot(@Qualifier("getToken") String token, MessageProcessor processor, MessageCount messageCount) {
        messageProcessor = processor;
        this.messageCount = messageCount;
        messageProcessor.initCommandsMap();
        BotCommand[] array = new BotCommand[messageProcessor.commands().size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = messageProcessor.commands().get(i).toApiCommand();
        }
        SetMyCommands commands = new SetMyCommands(array);
        telegramBot = new TelegramBot(token);
        telegramBot.execute(commands);

        telegramBot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                if (update.message() != null) {
                    telegramBot.execute(messageProcessor.process(update));
                    messageCount.incrementCounter();
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    public void execute(SendMessage message) {
        telegramBot.execute(message);
    }
}
