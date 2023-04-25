package ru.tinkoff.edu.java.scrapper.domain.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dto.entity.jpa.JpaChat;
import ru.tinkoff.edu.java.scrapper.dto.entity.jpa.JpaLink;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<JpaLink, Long> {

    @Query("select link from JpaLink link where link.lastUpdate < :lastUpdate")
    List<JpaLink> findOldLinks(@Param("lastUpdate")OffsetDateTime lastUpdate);

    @Query("update JpaLink link set link.lastUpdate = ?2, link.answerCount = ?3, link.commentCount = ?4 where link.id = ?1")
    void update(Long id, OffsetDateTime time, Integer answerCount, Integer commentCount);
    @Query("select link from JpaLink link where link.url = :url and link.chat = :chat")
    List<JpaLink> findByUrlAndChatId(@Param("url") URI url, @Param("chat")JpaChat chat);
}
