package co.id.beninjasaga.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "character_inv_slots")
public class CharacterInvSlotsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_inv_slots_id")
    private Long characterInvSlotsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterListEntity character;

    public Long getCharacterInvSlotsId() { return characterInvSlotsId; }
    public void setCharacterInvSlotsId(Long id) { this.characterInvSlotsId = id; }
    public CharacterListEntity getCharacter() { return character; }
    public void setCharacter(CharacterListEntity character) { this.character = character; }
}
