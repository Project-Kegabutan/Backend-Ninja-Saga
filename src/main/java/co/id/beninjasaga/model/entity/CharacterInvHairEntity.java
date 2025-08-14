package co.id.beninjasaga.model.entity;

import co.id.beninjasaga.model.entity.CharacterListEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "character_inv_hair")
public class CharacterInvHairEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_inv_hair")
    private Long characterInvHairId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterListEntity character;

    public Long getCharacterInvHairId() { return characterInvHairId; }
    public void setCharacterInvHairId(Long id) { this.characterInvHairId = id; }
    public CharacterListEntity getCharacter() { return character; }
    public void setCharacter(CharacterListEntity character) { this.character = character; }
}
