package ru.tinkoff.edu.java.scrapper.dto.entity.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
