package co.id.beninjasaga.repository;

import co.id.beninjasaga.model.entity.CharacterEquippedWeaponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface CharacterEquippedWeaponRepository extends JpaRepository<CharacterEquippedWeaponEntity, Long> {

    @Query(value = """
  SELECT
                                          *
                                         FROM character_equipped_weapon
                                         WHERE character_id = :character_id
                                         LIMIT 1;
""", nativeQuery = true)
    Optional<Map<String,Object>> getEquipmentWeapById(@Param("character_id") Long character_id);
}
