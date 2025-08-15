package co.id.beninjasaga.repository;

import co.id.beninjasaga.model.entity.CharacterEquippedAccessoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterEquippedAccessoryRepository extends JpaRepository<CharacterEquippedAccessoryEntity, Long> {
}
