package co.id.beninjasaga.entity;

import co.id.beninjasaga.model.entity.CharacterListEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "character_bloodline")
public class CharacterBloodlineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_bloodline_id")
    private Long characterBloodlineId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterListEntity character;

    public Long getCharacterBloodlineId() { return characterBloodlineId; }
    public void setCharacterBloodlineId(Long id) { this.characterBloodlineId = id; }
    public CharacterListEntity getCharacter() { return character; }
    public void setCharacter(CharacterListEntity character) { this.character = character; }
}
