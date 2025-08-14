package co.id.beninjasaga.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "character_mission")
public class CharacterMissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_mission_id")
    private Long characterMissionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterListEntity character;

    public Long getCharacterMissionId() { return characterMissionId; }
    public void setCharacterMissionId(Long id) { this.characterMissionId = id; }
    public CharacterListEntity getCharacter() { return character; }
    public void setCharacter(CharacterListEntity character) { this.character = character; }
}
