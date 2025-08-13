package co.id.beninjasaga.repository;

import co.id.beninjasaga.model.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByUsername(String username);
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE AccountEntity a SET a.accountSessionKey = :sessionKey, a.updatedAt = :updatedAt WHERE a.account_id = :accountId")
    int updateSessionKey(@Param("sessionKey") String sessionKey,
                         @Param("accountId") Long accountId,
                         @Param("updatedAt")LocalDateTime updatedAt);
}
