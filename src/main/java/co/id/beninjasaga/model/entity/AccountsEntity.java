package co.id.beninjasaga.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity

@Table(name = "accounts",
        indexes = {
                @Index(name = "idx_accounts_username", columnList = "username"),
                @Index(name = "idx_accounts_email", columnList = "email")
        })
public class AccountsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId; // bigint

    @Column(name = "username", nullable = false, length = 255)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "account_type", nullable = false)
    private Integer accountType;

    @Column(name = "account_session_key", length = 255)
    private String accountSessionKey;

    // ✅ jadikan INT (bukan String)
    @Column(name = "login_per_day", nullable = false)
    private Integer loginPerDay = 0;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 1 Account -> 1 CharacterToken (opsional)
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private AccountsCharacterTokenEntity characterToken;

    // 1 Account -> many Characters
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    @org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    // opsional, tetap sarankan FK DB
    private List<CharacterListEntity> characters = new ArrayList<>();

    // helper methods
    public void addCharacter(CharacterListEntity c) {
        characters.add(c);
        c.setAccount(this);
    }

    public void removeCharacter(CharacterListEntity c) {
        characters.remove(c);
        c.setAccount(null);
    }
}
