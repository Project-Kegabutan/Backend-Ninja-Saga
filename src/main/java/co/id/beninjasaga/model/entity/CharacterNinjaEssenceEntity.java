package co.id.beninjasaga.model.entity;

import co.id.beninjasaga.model.entity.CharacterListEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "character_ninja_essence")
public class CharacterNinjaEssenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_ninja_essence_id")
    private Long characterNinjaEssenceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterListEntity character;

    public Long getCharacterNinjaEssenceId() { return characterNinjaEssenceId; }
    public void setCharacterNinjaEssenceId(Long id) { this.characterNinjaEssenceId = id; }
    public CharacterListEntity getCharacter() { return character; }
    public void setCharacter(CharacterListEntity character) { this.character = character; }
}
