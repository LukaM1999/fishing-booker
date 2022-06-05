package com.fishingbooker.repository;

import com.fishingbooker.model.Adventure;
import com.fishingbooker.model.Boat;
import com.fishingbooker.model.Cottage;
import com.fishingbooker.model.Rentable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;

public interface RentableRepository extends JpaRepository<Rentable, Long> {
    @Query("select cottage from Cottage cottage " +
            "where cottage.ownerUsername = :owner and cottage.name = :name ")
    Rentable getCottageByNameAndOwner(@Param("name") String name, @Param("owner") String owner);

    @Query("select boat from Boat boat " +
            "where boat.ownerUsername = :owner and boat.name = :name ")
    Rentable getBoatByNameAndOwner(@Param("name") String name, @Param("owner") String owner);

    @Query("select adventure from Adventure adventure " +
            "where adventure.ownerUsername = :owner ")
    List<Rentable> getAdventuresByOwner(@Param("owner") String owner);

    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select rentable from Rentable rentable " +
            "where rentable.id = :id")
    //@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
    Rentable getRentableById(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select cottage from Cottage cottage " +
            "where cottage.id = :id")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
    Cottage getCottageLock(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select boat from Boat boat " +
            "where boat.id = :id")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "10000")})
    Boat getBoatLock(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select adventure from Adventure adventure " +
            "where adventure.id = :id")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "10000")})
    Adventure getAdventureLock(Long id);
}
