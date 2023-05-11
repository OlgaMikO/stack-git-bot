package ru.tinkoff.edu.java.scrapper.domain.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.edu.java.scrapper.dto.entity.jpa.JpaChat;

public interface ChatRepository extends JpaRepository<JpaChat, Long> {

}
