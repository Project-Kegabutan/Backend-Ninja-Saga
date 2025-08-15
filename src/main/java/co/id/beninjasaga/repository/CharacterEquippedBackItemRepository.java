package co.id.beninjasaga.repository;

import co.id.beninjasaga.model.entity.CharacterEquippedBackItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterEquippedBackItemRepository extends JpaRepository<CharacterEquippedBackItemEntity, Long> {
}
