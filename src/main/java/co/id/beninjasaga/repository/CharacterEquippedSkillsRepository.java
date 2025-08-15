package co.id.beninjasaga.repository;

import co.id.beninjasaga.model.entity.CharacterEquippedSkillsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterEquippedSkillsRepository extends JpaRepository<CharacterEquippedSkillsEntity, Long> {
}
