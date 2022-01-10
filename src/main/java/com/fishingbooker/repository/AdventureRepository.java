package com.fishingbooker.repository;

import com.fishingbooker.model.Adventure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdventureRepository extends JpaRepository<Adventure, Long> {
}
