package co.id.beninjasaga.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "character_token")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountsCharacterTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_token")
    private Long idToken;

    @Column(name = "username")
    private String username;

    @Column(name = "balance_token")
    private int balanceToken;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Sisi owning: FK ada di sini (character_token.account_id → accounts.account_id)
    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false, unique = true)
    private AccountsEntity account;
}

