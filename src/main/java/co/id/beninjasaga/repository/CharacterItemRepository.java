package co.id.beninjasaga.repository;

import co.id.beninjasaga.model.entity.CharacterItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterItemRepository extends JpaRepository<CharacterItemEntity, Long> {
}
