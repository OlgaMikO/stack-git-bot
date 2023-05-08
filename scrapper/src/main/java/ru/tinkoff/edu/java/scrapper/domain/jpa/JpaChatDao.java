package ru.tinkoff.edu.java.scrapper.domain.jpa;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.domain.ChatDao;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.dto.entity.jpa.JpaChat;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaChatDao extends ChatDao {

    private final ChatRepository chatRepository;

    private JpaChat toJpaChat(Chat chat) {
        JpaChat jpaChat = new JpaChat();
        jpaChat.setId(chat.getId());
        return jpaChat;
    }

    @Override
    public int add(Chat chat) {
        JpaChat jpaChat = chatRepository.save(toJpaChat(chat));
        return jpaChat.getId().intValue();
    }

    @Override
    @Transactional
    public int remove(Long id) {
        Optional<JpaChat> opt = chatRepository.findById(id);
        JpaChat jpaChat = opt.orElse(null);
        if (jpaChat == null) {
            return -1;
        }
        chatRepository.delete(jpaChat);
        return jpaChat.getId().intValue();
    }

    @Override
    public List<Chat> findAll() {
        List<JpaChat> jpaChats = chatRepository.findAll();
        return jpaChats.stream().map(u -> new Chat(u.getId())).collect(Collectors.toList());
    }

    @Override
    public Chat findById(Long id) {
        Optional<JpaChat> opt = chatRepository.findById(id);
        JpaChat jpaChat = opt.orElse(null);
        if (jpaChat == null) {
            return null;
        }
        return new Chat(jpaChat.getId());
    }
}
