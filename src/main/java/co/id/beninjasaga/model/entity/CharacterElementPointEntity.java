package co.id.beninjasaga.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "character_element_point")
public class CharacterElementPointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_element_id")
    private Long characterElementId;

    @Column(name = "character_skill_ap")
    private Integer characterSkillAp;

    @Column(name = "character_element_fire")
    private Integer fire;

    @Column(name = "character_element_water")
    private Integer water;

    @Column(name = "character_element_wind")
    private Integer wind;

    @Column(name = "character_element_earth")
    private Integer earth;

    @Column(name = "character_element_lightning")
    private Integer lightning;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterListEntity character;

    public Long getCharacterElementId() { return characterElementId; }
    public void setCharacterElementId(Long id) { this.characterElementId = id; }
    public Integer getCharacterSkillAp() { return characterSkillAp; }
    public void setCharacterSkillAp(Integer ap) { this.characterSkillAp = ap; }
    public Integer getFire() { return fire; }
    public void setFire(Integer fire) { this.fire = fire; }
    public Integer getWater() { return water; }
    public void setWater(Integer water) { this.water = water; }
    public Integer getWind() { return wind; }
    public void setWind(Integer wind) { this.wind = wind; }
    public Integer getEarth() { return earth; }
    public void setEarth(Integer earth) { this.earth = earth; }
    public Integer getLightning() { return lightning; }
    public void setLightning(Integer lightning) { this.lightning = lightning; }
    public CharacterListEntity getCharacter() { return character; }
    public void setCharacter(CharacterListEntity character) { this.character = character; }
}
