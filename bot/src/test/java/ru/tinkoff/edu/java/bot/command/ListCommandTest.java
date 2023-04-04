package ru.tinkoff.edu.java.bot.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import ru.tinkoff.edu.java.bot.IDataBase;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListCommandTest {

    private final IDataBase mockDatabase = Mockito.mock(IDataBase.class);
    private final ListCommand listCommand = new ListCommand(mockDatabase);

    @Test
    public void handleListTest() {
        // GIVEN
        long userId = 1L;
        ArrayList<String> links = new ArrayList<>();
        links.add("https://github.com/OlgaMikO/stack-git-bot");
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        ReflectionTestUtils.setField(chat, "id", userId);
        ReflectionTestUtils.setField(message, "chat", chat);
        ReflectionTestUtils.setField(update, "message", message);
        Mockito.when(mockDatabase.getLinkList(userId)).thenReturn(links);
        Mockito.when(mockDatabase.containUser(userId)).thenReturn(true);
        SendMessage expected = new SendMessage(chat, links.toString());

        // WHEN
        SendMessage actual = listCommand.handle(update);

        // THEN
        assertEquals(expected.getParameters().get("text"), actual.getParameters().get("text"));
    }

    @Test
    public void handleNullTest() {
        // GIVEN
        long userId = 1L;
        ArrayList<String> links = new ArrayList<>();
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        ReflectionTestUtils.setField(chat, "id", userId);
        ReflectionTestUtils.setField(message, "chat", chat);
        ReflectionTestUtils.setField(update, "message", message);
        Mockito.when(mockDatabase.getLinkList(userId)).thenReturn(links);
        Mockito.when(mockDatabase.containUser(userId)).thenReturn(true);
        SendMessage expected = new SendMessage(chat, "Список отслеживаемых ссылок пуст");

        // WHEN
        SendMessage actual = listCommand.handle(update);

        // THEN
        assertEquals(expected.getParameters().get("text"), actual.getParameters().get("text"));
    }

}
