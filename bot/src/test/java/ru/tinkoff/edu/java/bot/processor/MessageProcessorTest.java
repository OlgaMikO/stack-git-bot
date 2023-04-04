package ru.tinkoff.edu.java.bot.processor;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import ru.tinkoff.edu.java.bot.command.Command;
import ru.tinkoff.edu.java.bot.command.ListCommand;
import ru.tinkoff.edu.java.bot.command.UnknownCommand;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;


public class MessageProcessorTest {

    private final MessageProcessor messageProcessor = new MessageProcessor();

    @Test
    public void parseListCommandTest() {
        // GIVEN
        Update update = new Update();
        Message message = new Message();
        ReflectionTestUtils.setField(message, "text", "/list");
        ReflectionTestUtils.setField(update, "message", message);

        // WHEN
        Command actual = messageProcessor.parseCommand(update);

        // THEN
        assertInstanceOf(ListCommand.class, actual);
    }

    @Test
    public void parseUnknownCommandTest() {
        // GIVEN
        Update update = new Update();
        Message message = new Message();
        ReflectionTestUtils.setField(message, "text", "/abracadabra");
        ReflectionTestUtils.setField(update, "message", message);

        // WHEN
        Command actual = messageProcessor.parseCommand(update);

        // THEN
        assertInstanceOf(UnknownCommand.class, actual);
    }
}
