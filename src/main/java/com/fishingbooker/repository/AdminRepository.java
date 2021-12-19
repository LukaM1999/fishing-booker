package com.fishingbooker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fishingbooker.model.Admin;

public interface AdminRepository  extends JpaRepository<Admin, Long>{
}
