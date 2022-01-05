package com.fishingbooker.repository;

import com.fishingbooker.dto.ApproveUserDTO;
import com.fishingbooker.model.BoatOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoatOwnerRepository extends JpaRepository<BoatOwner, String> {

    @Query("SELECT new com.fishingbooker.dto.ApproveUserDTO(i.username, i.name, i.surname, i.email, i.letterOfIntent, i.role.roleName) " +
            "FROM BoatOwner i, Role r " +
            "WHERE r.id = i.role.id and i.enabled = false")
    List<ApproveUserDTO> findAllWithoutBoats();

}
