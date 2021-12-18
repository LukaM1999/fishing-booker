package com.fishingbooker.repository;

import com.fishingbooker.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long>{
}
