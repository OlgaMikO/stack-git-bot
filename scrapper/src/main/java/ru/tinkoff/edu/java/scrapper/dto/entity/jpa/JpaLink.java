package ru.tinkoff.edu.java.scrapper.dto.entity.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.net.URI;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity @Table(name = "links") @Data @AllArgsConstructor public class JpaLink {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "links_id_seq")
    @SequenceGenerator(name = "links_id_seq", sequenceName = "links_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false) private Long id;

    @Column(name = "url") private URI url;

    @ManyToOne(cascade = {CascadeType.ALL}) @JoinColumn(name = "chat") private JpaChat chat;

    @Column(name = "last_update") private OffsetDateTime lastUpdate;

    @Column(name = "last_activity") private OffsetDateTime lastActivity;

    @Column(name = "answer_count") private Integer answerCount;

    @Column(name = "comment_count") private Integer commentCount;

    public JpaLink() {
    }
}
