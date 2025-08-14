package co.id.beninjasaga.model.entity;

import co.id.beninjasaga.model.entity.CharacterListEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "character_equipped_body_set")
public class CharacterEquippedBodySetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_equipped_body_set_id")
    private Long characterEquippedBodySetId;

    @Column(name = "character_equipped_body_set_number", length = 255)
    private String characterEquippedBodySetNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterListEntity character;

    public Long getCharacterEquippedBodySetId() { return characterEquippedBodySetId; }
    public void setCharacterEquippedBodySetId(Long id) { this.characterEquippedBodySetId = id; }
    public String getCharacterEquippedBodySetNumber() { return characterEquippedBodySetNumber; }
    public void setCharacterEquippedBodySetNumber(String num) { this.characterEquippedBodySetNumber = num; }
    public CharacterListEntity getCharacter() { return character; }
    public void setCharacter(CharacterListEntity character) { this.character = character; }
}
