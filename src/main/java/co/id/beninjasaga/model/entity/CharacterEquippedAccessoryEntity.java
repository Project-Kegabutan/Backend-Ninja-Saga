package co.id.beninjasaga.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "character_equipped_accessory")
public class CharacterEquippedAccessoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_equipped_accessory_id")
    private Long characterEquippedAccessoryId;

    @Column(name = "character_equipped_accessory_number", length = 255)
    private String characterEquippedAccessoryNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterListEntity character;

    public Long getCharacterEquippedAccessoryId() { return characterEquippedAccessoryId; }
    public void setCharacterEquippedAccessoryId(Long id) { this.characterEquippedAccessoryId = id; }
    public String getCharacterEquippedAccessoryNumber() { return characterEquippedAccessoryNumber; }
    public void setCharacterEquippedAccessoryNumber(String num) { this.characterEquippedAccessoryNumber = num; }
    public CharacterListEntity getCharacter() { return character; }
    public void setCharacter(CharacterListEntity character) { this.character = character; }
}
