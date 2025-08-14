package co.id.beninjasaga.model.entity;

import jakarta.persistence.*;

@Entity
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

    // many characters -> 1 account
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountsEntity account;

    // getters & setters
    public Long getCharacterId() { return characterId; }
    public void setCharacterId(Long characterId) { this.characterId = characterId; }
    public String getCharacterName() { return characterName; }
    public void setCharacterName(String characterName) { this.characterName = characterName; }
    public Integer getCharacterLevel() { return characterLevel; }
    public void setCharacterLevel(Integer characterLevel) { this.characterLevel = characterLevel; }
    public Integer getCharacterGender() { return characterGender; }
    public void setCharacterGender(Integer characterGender) { this.characterGender = characterGender; }
    public String getCharacterExp() { return characterExp; }
    public void setCharacterExp(String characterExp) { this.characterExp = characterExp; }
    public Integer getCharacterGold() { return characterGold; }
    public void setCharacterGold(Integer characterGold) { this.characterGold = characterGold; }
    public Integer getCharacterHp() { return characterHp; }
    public void setCharacterHp(Integer characterHp) { this.characterHp = characterHp; }
    public Integer getCharacterMaxHp() { return characterMaxHp; }
    public void setCharacterMaxHp(Integer characterMaxHp) { this.characterMaxHp = characterMaxHp; }
    public Integer getCharacterCp() { return characterCp; }
    public void setCharacterCp(Integer characterCp) { this.characterCp = characterCp; }
    public Integer getCharacterMaxCp() { return characterMaxCp; }
    public void setCharacterMaxCp(Integer characterMaxCp) { this.characterMaxCp = characterMaxCp; }
    public String getCharacterSkillType() { return characterSkillType; }
    public void setCharacterSkillType(String characterSkillType) { this.characterSkillType = characterSkillType; }
    public AccountsEntity getAccount() { return account; }
    public void setAccount(AccountsEntity account) { this.account = account; }
}
