package co.id.beninjasaga.repository;

import co.id.beninjasaga.model.entity.CharacterBodyStyleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface CharacterBodyStyleRepository extends JpaRepository<CharacterBodyStyleEntity, Long> {

    @Query(value = """
  SELECT
                                          *
                                         FROM character_body_style
                                         WHERE character_id = :character_id
                                         LIMIT 1;
""", nativeQuery = true)
    Optional<Map<String,Object>> getCharacterBodyStyle(@Param("character_id") Long character_id);
}
