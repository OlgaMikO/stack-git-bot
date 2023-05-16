package ru.tinkoff.edu.java.bot.processor;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import ru.tinkoff.edu.java.bot.BotApplication;
import ru.tinkoff.edu.java.bot.command.Command;
import ru.tinkoff.edu.java.bot.command.ListCommand;
import ru.tinkoff.edu.java.bot.command.UnknownCommand;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;


@SpringBootTest
@ContextConfiguration(classes = {BotApplication.class})
public class MessageProcessorTest {

    private final MessageProcessor messageProcessor;

    @Autowired
    public MessageProcessorTest(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    @Test
    public void parseListCommandTest() {
        // GIVEN
        messageProcessor.initCommandsMap();
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
