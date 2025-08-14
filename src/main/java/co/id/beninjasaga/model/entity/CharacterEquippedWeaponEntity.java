package co.id.beninjasaga.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "character_equipped_weapon")
public class CharacterEquippedWeaponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_equipped_weapon_id")
    private Long characterEquippedWeaponId;

    @Column(name = "character_weapon_number", length = 255)
    private String characterWeaponNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterListEntity character;

    public Long getCharacterEquippedWeaponId() { return characterEquippedWeaponId; }
    public void setCharacterEquippedWeaponId(Long id) { this.characterEquippedWeaponId = id; }
    public String getCharacterWeaponNumber() { return characterWeaponNumber; }
    public void setCharacterWeaponNumber(String num) { this.characterWeaponNumber = num; }
    public CharacterListEntity getCharacter() { return character; }
    public void setCharacter(CharacterListEntity character) { this.character = character; }
}
