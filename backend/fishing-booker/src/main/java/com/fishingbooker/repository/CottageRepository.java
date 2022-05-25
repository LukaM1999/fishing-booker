package com.fishingbooker.repository;

import com.fishingbooker.model.Cottage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CottageRepository extends JpaRepository<Cottage, Long> {
    List<Cottage> findAllByOwnerUsername(String username);
    @Modifying
    @Query("delete from Cottage c where c.id = ?1")
    void deleteById(Long entityId);

    Cottage getCottageByNameAndOwnerUsername(String name, String ownerUsername);
}
