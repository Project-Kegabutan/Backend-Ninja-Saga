package co.id.beninjasaga.model.entity;

import co.id.beninjasaga.model.entity.CharacterListEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "character_item")
public class CharacterItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_item_id")
    private Long characterItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterListEntity character;

    public Long getCharacterItemId() { return characterItemId; }
    public void setCharacterItemId(Long id) { this.characterItemId = id; }
    public CharacterListEntity getCharacter() { return character; }
    public void setCharacter(CharacterListEntity character) { this.character = character; }
}
