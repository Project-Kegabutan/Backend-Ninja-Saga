package co.id.beninjasaga.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "character_list",
        indexes = { @Index(name = "idx_character_name", columnList = "character_name") })
public class CharacterListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_id")
    private Long characterId; // bigint

    @Column(name = "character_name", length = 255)
    private String characterName;

    @Column(name = "character_level")
    private Integer characterLevel;

    @Column(name = "character_rank")
    private Integer characterRank;

    @Column(name = "character_gender")
    private Integer characterGender;

    @Column(name = "character_exp", length = 255)
    private String characterExp;

    @Column(name = "character_gold")
    private Integer characterGold;

    @Column(name = "character_hp")
    private Integer characterHp;

    @Column(name = "character_max_hp")
    private Integer characterMaxHp;

    @Column(name = "character_cp")
    private Integer characterCp;

    @Column(name = "character_max_cp")
    private Integer characterMaxCp;

    @Column(name = "character_skill_type", length = 255)
    private String characterSkillType;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    // many characters -> 1 account
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountsEntity account;

}
