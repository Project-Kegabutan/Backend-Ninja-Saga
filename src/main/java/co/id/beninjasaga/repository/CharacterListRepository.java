package co.id.beninjasaga.repository;

import co.id.beninjasaga.model.entity.CharacterListEntity;
import org.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface CharacterListRepository extends JpaRepository<CharacterListEntity, Long> {

    @Query(value = """
        SELECT 
            character_id, 
            character_name, 
            character_level, 
            CAST(character_gender AS CHAR) AS gender_str
        FROM character_list
        WHERE account_id = :accountId
    """, nativeQuery = true)
    List<Object[]> findLiteByAccountId(@Param("accountId") Long accountId);

    @Query(value = """
  SELECT
                                           account_id,
                                           character_id,
                                           character_name,
                                           character_level,
                                           CAST(character_gender AS CHAR) AS gender_str
                                         FROM character_list
                                         WHERE account_id = :accountId
                                         ORDER BY created_at DESC, character_id DESC
                                         LIMIT 1;
""", nativeQuery = true)
    Optional<Map<String,Object>> findNewCreatingCharacter(@Param("accountId") Long accountId);

}
