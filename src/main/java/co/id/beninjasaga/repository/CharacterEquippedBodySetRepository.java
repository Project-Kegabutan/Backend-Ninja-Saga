package co.id.beninjasaga.repository;


import co.id.beninjasaga.model.entity.CharacterEquippedBodySetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterEquippedBodySetRepository extends JpaRepository<CharacterEquippedBodySetEntity, Long> {
}
