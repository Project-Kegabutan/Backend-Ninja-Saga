package co.id.beninjasaga.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "character_pet_skills")
public class CharacterPetSkillsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_pet_skills_id")
    private Long characterPetSkillsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterListEntity character;

    public Long getCharacterPetSkillsId() { return characterPetSkillsId; }
    public void setCharacterPetSkillsId(Long id) { this.characterPetSkillsId = id; }
    public CharacterListEntity getCharacter() { return character; }
    public void setCharacter(CharacterListEntity character) { this.character = character; }
}
