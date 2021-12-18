package com.fishingbooker.repository;

import com.fishingbooker.model.Proba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProbaRepository extends JpaRepository<Proba, Long> {
}
