package ru.tinkoff.edu.java.scrapper.dto.entity.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "chats")
@Data
@AllArgsConstructor
public class JpaChat {

    @Id
    @Column(nullable = false)
    private Long id;

    public JpaChat() {
    }
}
