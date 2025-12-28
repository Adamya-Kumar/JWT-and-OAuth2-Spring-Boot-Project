package com.codeshutlle.project.JWTAndOAuth2.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor // Required for JPA (as noted in your logs!)
@AllArgsConstructor
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refreshToken;

    @CreationTimestamp
    private LocalDateTime leastUsedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
