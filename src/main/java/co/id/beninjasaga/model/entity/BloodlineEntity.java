package co.id.beninjasaga.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bloodline")
public class BloodlineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bloodline_id")
    private Long bloodlineId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterListEntity character;

    public Long getBloodlineId() { return bloodlineId; }
    public void setBloodlineId(Long bloodlineId) { this.bloodlineId = bloodlineId; }
    public CharacterListEntity getCharacter() { return character; }
    public void setCharacter(CharacterListEntity character) { this.character = character; }
}
