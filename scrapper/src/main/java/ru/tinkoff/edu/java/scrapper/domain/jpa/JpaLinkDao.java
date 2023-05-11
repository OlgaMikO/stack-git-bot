package ru.tinkoff.edu.java.scrapper.domain.jpa;

import jakarta.transaction.Transactional;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.domain.LinkDao;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;
import ru.tinkoff.edu.java.scrapper.dto.entity.jpa.JpaChat;
import ru.tinkoff.edu.java.scrapper.dto.entity.jpa.JpaLink;

@RequiredArgsConstructor
public class JpaLinkDao extends LinkDao {

    private final LinkRepository linkRepository;

    private final ChatRepository chatRepository;

    private JpaLink toJpaLink(Link link) {
        JpaLink jpaLink = new JpaLink();
        JpaChat jpaChat = new JpaChat();
        jpaLink.setId(link.getId());
        jpaLink.setUrl(link.getUrl());
        jpaChat.setId(link.getChat());
        jpaLink.setChat(jpaChat);
        jpaLink.setLastUpdate(OffsetDateTime.now());
        jpaLink.setLastActivity(link.getLastActivity());
        jpaLink.setAnswerCount(link.getAnswerCount());
        jpaLink.setCommentCount(link.getCommentCount());
        return jpaLink;
    }

    private Link toLink(JpaLink jpaLink) {
        if (jpaLink == null) {
            return null;
        }
        Link link = new Link();
        link.setId(jpaLink.getId());
        link.setUrl(jpaLink.getUrl());
        link.setChat(jpaLink.getChat().getId());
        link.setLastUpdate(OffsetDateTime.now());
        link.setLastActivity(jpaLink.getLastActivity());
        link.setAnswerCount(jpaLink.getAnswerCount());
        link.setCommentCount(jpaLink.getCommentCount());
        return link;
    }

    @Override
    public long add(Link link) {
        JpaLink jpaLink = linkRepository.save(toJpaLink(link));
        return jpaLink.getId();
    }

    @Override
    @Transactional
    public int remove(Long id) {
        Optional<JpaLink> opt = linkRepository.findById(id);
        JpaLink jpaLink = opt.orElse(null);
        if (jpaLink == null) {
            return -1;
        }
        linkRepository.delete(jpaLink);
        return jpaLink.getId().intValue();
    }

    @Override
    public List<Link> findAll() {
        return linkRepository.findAll().stream().map(this::toLink).collect(Collectors.toList());
    }

    @Override
    public List<Link> findOldLinks(Long minutes) {
        List<JpaLink> jpaLinks = linkRepository.findOldLinks(OffsetDateTime.now().minusMinutes(minutes));
        return jpaLinks.stream().map(this::toLink).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public int update(Long id, OffsetDateTime time, Integer answerCount, Integer commentCount) {
        Optional<JpaLink> opt = linkRepository.findById(id);
        JpaLink jpaLink = opt.orElse(null);
        if (jpaLink == null) {
            return -1;
        }
        linkRepository.update(id, time, answerCount, commentCount);
        return id.intValue();
    }

    @Override
    @Transactional
    public Link findByUrlAndChatId(URI url, Long chatId) {
        Optional<JpaChat> opt = chatRepository.findById(chatId);
        JpaChat jpaChat = opt.orElse(null);
        if (jpaChat == null) {
            return null;
        }
        List<JpaLink> list = linkRepository.findByUrlAndChatId(url, jpaChat);
        if (list == null) {
            return null;
        }
        return toLink(list.get(0));
    }
}
