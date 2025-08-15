package co.id.beninjasaga.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "character_body_style")
@Getter
@Setter
public class CharacterBodyStyleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_body_style_id")
    private Long characterBodyStyleId;

    @Column(name = "character_hair_style")
    private String characterHairStyle;

    @Column(name = "character_hair_color_style")
    private String characterHairColorStyle;

    @Column(name = "character_skin_color_style")
    private String characterSkinColor;

    @Column(name = "character_face_style")
    private String characterFace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterListEntity character;

}
