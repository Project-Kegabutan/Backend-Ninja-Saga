package co.id.beninjasaga.model.entity;

import co.id.beninjasaga.model.entity.CharacterListEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "character_item")
public class CharacterItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_item_id")
    private Long characterItemId;

    @Column(name = "character_item_number", length = 255)
    private String characterItemNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterListEntity character;
}
