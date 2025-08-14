package co.id.beninjasaga.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "character_equipped_back_item")
public class CharacterEquippedBackItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_equipped_back_item_id")
    private Long characterEquippedBackItemId;

    @Column(name = "character_equipped_back_item_number", length = 255)
    private String characterEquippedBackItemNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterListEntity character;

    public Long getCharacterEquippedBackItemId() { return characterEquippedBackItemId; }
    public void setCharacterEquippedBackItemId(Long id) { this.characterEquippedBackItemId = id; }
    public String getCharacterEquippedBackItemNumber() { return characterEquippedBackItemNumber; }
    public void setCharacterEquippedBackItemNumber(String num) { this.characterEquippedBackItemNumber = num; }
    public CharacterListEntity getCharacter() { return character; }
    public void setCharacter(CharacterListEntity character) { this.character = character; }
}
