package ru.tinkoff.edu.java.scrapper.dto.entity.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URI;
import java.time.OffsetDateTime;

@Entity
@Table(name = "links")
@Data
@AllArgsConstructor
public class JpaLink {

    @Id
    @Column(nullable = false)
    private Long id;

    private URI url;

    @ManyToOne
    private JpaChat chat;

    private OffsetDateTime lastUpdate;
    private OffsetDateTime lastActivity;

    private Integer answerCount;

    private Integer commentCount;

    public JpaLink() {
    }
}
