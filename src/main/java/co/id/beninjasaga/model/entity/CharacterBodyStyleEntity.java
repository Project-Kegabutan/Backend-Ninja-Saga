package co.id.beninjasaga.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "character_body_style")
public class CharacterBodyStyleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_body_style_id")
    private Long characterBodyStyleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterListEntity character;

    public Long getCharacterBodyStyleId() { return characterBodyStyleId; }
    public void setCharacterBodyStyleId(Long id) { this.characterBodyStyleId = id; }
    public CharacterListEntity getCharacter() { return character; }
    public void setCharacter(CharacterListEntity character) { this.character = character; }
}
