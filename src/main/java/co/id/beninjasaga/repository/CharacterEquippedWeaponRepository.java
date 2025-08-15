package co.id.beninjasaga.repository;

import co.id.beninjasaga.model.entity.CharacterEquippedWeaponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterEquippedWeaponRepository extends JpaRepository<CharacterEquippedWeaponEntity, Long> {
}
