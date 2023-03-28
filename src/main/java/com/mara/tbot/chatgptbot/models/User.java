package com.mara.tbot.chatgptbot.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user", schema = "public")
@Data
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tg_user_id")
    private Long tgUserId;

    @Column(name = "chatId")
    private Long chatId;

    @Column(name = "username")
    private String username;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "tokens_left")
    private Integer tokensLeft;

    @Column(name = "active_session")
    private Boolean activeSession;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @OneToMany(mappedBy = "user")
    private List<Query> queries;

    public User(Long tgUserId, String username, String firstname, String lastname, Long chatId) {
        this.tgUserId = tgUserId;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.chatId = chatId;
    }
}
