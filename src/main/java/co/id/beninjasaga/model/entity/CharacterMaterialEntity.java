package co.id.beninjasaga.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "character_material")
public class CharacterMaterialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_material_id")
    private Long characterMaterialId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterListEntity character;

    public Long getCharacterMaterialId() { return characterMaterialId; }
    public void setCharacterMaterialId(Long id) { this.characterMaterialId = id; }
    public CharacterListEntity getCharacter() { return character; }
    public void setCharacter(CharacterListEntity character) { this.character = character; }
}
