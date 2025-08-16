package co.id.beninjasaga.repository;

import co.id.beninjasaga.model.entity.CharacterEquippedSkillsEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterEquippedSkillsRepository extends JpaRepository<CharacterEquippedSkillsEntity, Long> {
    @Modifying
    @Transactional
    @Query(value = """
        UPDATE character_equipped_skills
        SET character_skill_number = :skillNumber
        WHERE character_id = :characterId
    """, nativeQuery = true)
    int updateNewSkillCharacter(@Param("characterId") long characterId,
                               @Param("skillNumber") String skillNumber);

    CharacterEquippedSkillsEntity findByCharacter_CharacterId(@Param("accountId") Long accountId);
}
