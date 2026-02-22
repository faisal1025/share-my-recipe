package com.airtribe.ShareMyRecipe.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "verification_token")
@EntityListeners(AuditingEntityListener.class)
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    @NotNull
    @Column(nullable = false, unique = true)
    private String token;

    @NotNull
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate = LocalDateTime.now().plusDays(1);

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private AbstractUserBase user;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public VerificationToken() {
    }

    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public AbstractUserBase getUser() {
        return user;
    }

    public void setUser(AbstractUserBase user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
