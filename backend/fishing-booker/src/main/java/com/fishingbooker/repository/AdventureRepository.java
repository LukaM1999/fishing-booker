package com.fishingbooker.repository;

import com.fishingbooker.model.Adventure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdventureRepository extends JpaRepository<Adventure, Long> {
    List<Adventure> findAllByOwnerUsername(String username);

    @Modifying
    @Query("delete from Adventure a where a.id = ?1")
    void deleteById(Long entityId);

    Adventure getAdventureByNameAndOwnerUsername(String name, String ownerUsername);
}
