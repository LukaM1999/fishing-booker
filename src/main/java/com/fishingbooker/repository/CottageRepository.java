package com.fishingbooker.repository;

import com.fishingbooker.model.Cottage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CottageRepository extends JpaRepository<Cottage, Long> {
}
