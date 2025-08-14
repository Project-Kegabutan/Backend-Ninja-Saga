package co.id.beninjasaga.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "character_token",
        indexes = {
                @Index(name = "idx_token_username", columnList = "username"),
                @Index(name = "idx_token_balance", columnList = "balance_token")
        })
public class AccountsCharacterTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_token")
    private Long idToken; // bigint

    @Column(name = "username", nullable = false, length = 255)
    private String username;

    @Column(name = "balance_token", length = 255)
    private Integer balanceToken;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // One-to-One ke Account
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", unique = true)
    private AccountsEntity account;

    // getters & setters
    public Long getIdToken() { return idToken; }
    public void setIdToken(Long idToken) { this.idToken = idToken; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Integer getBalanceToken() { return balanceToken; }
    public void setBalanceToken(Integer balanceToken) { this.balanceToken = balanceToken; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public AccountsEntity getAccount() { return account; }
    public void setAccount(AccountsEntity account) { this.account = account; }
}
