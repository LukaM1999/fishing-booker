package com.fishingbooker.repository;

import com.fishingbooker.model.Cottage;
import com.fishingbooker.model.Rentable;
import org.springframework.data.jpa.repository.*;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface CottageRepository extends JpaRepository<Cottage, Long> {
    List<Cottage> findAllByOwnerUsername(String username);
    @Modifying
    @Query("delete from Cottage c where c.id = ?1")
    void deleteById(Long entityId);

    Cottage getCottageByNameAndOwnerUsername(String name, String ownerUsername);

    List<Rentable> getRentablesByOwnerUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(@QueryHint(name = "javax.persistence.lock.timeout", value = "0"))
    @Query("select c from Cottage c where c.id = ?1")
    Cottage findByIdLocked(Long id);


}
