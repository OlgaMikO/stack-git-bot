package ru.tinkoff.edu.java.bot.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnknownCommandTest {

    UnknownCommand unknownCommand = new UnknownCommand();

    @Test
    public void handleTest() {
        // GIVEN
        long userId = 1L;
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        ReflectionTestUtils.setField(chat, "id", userId);
        ReflectionTestUtils.setField(message, "chat", chat);
        ReflectionTestUtils.setField(update, "message", message);
        SendMessage expected = new SendMessage(chat, "Неизвестная команда");

        // WHEN
        SendMessage actual = unknownCommand.handle(update);

        // THEN
        assertEquals(expected.getParameters().get("text"), actual.getParameters().get("text"));
    }
}
