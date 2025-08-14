package co.id.beninjasaga.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "character_equipped_skills")
public class CharacterEquippedSkillsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_equipped_skill_id")
    private Long characterEquippedSkillId;

    @Column(name = "character_skill_number", length = 255)
    private String characterSkillNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterListEntity character;

    public Long getCharacterEquippedSkillId() { return characterEquippedSkillId; }
    public void setCharacterEquippedSkillId(Long id) { this.characterEquippedSkillId = id; }
    public String getCharacterSkillNumber() { return characterSkillNumber; }
    public void setCharacterSkillNumber(String num) { this.characterSkillNumber = num; }
    public CharacterListEntity getCharacter() { return character; }
    public void setCharacter(CharacterListEntity character) { this.character = character; }
}
