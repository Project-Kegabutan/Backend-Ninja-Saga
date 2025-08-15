package co.id.beninjasaga.repository;

import co.id.beninjasaga.model.entity.CharacterBodyStyleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterBodyStyleRepository extends JpaRepository<CharacterBodyStyleEntity, Long> {
}
