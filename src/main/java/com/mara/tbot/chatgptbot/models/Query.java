package com.mara.tbot.chatgptbot.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "query")
@Data
@NoArgsConstructor
public class Query {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "question")
    private String question;
    
    @Column(name = "answer")
    private String answer;

    @Column(name = "received_at")
    private LocalDateTime receivedAt;

    @Column(name = "context_weight")
    private Integer contextWeight;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "total_spent")
    private Integer totalSpent;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Query(String question, String answer, Integer contextWeight, Integer weight, Integer totalSpent) {
        this.question = question;
        this.answer = answer;
        this.contextWeight = contextWeight;
        this.weight = weight;
        this.totalSpent = totalSpent;
    }
}
