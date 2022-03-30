package com.fishingbooker.repository;

import com.fishingbooker.dto.ApproveUserDTO;
import com.fishingbooker.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InstructorRepository extends JpaRepository<Instructor, String> {
    @Override
    @Query("SELECT DISTINCT instructor FROM Instructor instructor " +
            "JOIN FETCH instructor.adventures adventures")
    List<Instructor> findAll();

    @Query("SELECT new com.fishingbooker.dto.ApproveUserDTO(i.username, i.name, i.surname, i.email, i.letterOfIntent, i.role.roleName) " +
            "FROM Instructor i, Role r " +
            "WHERE r.id = i.role.id and i.enabled = false")
    List<ApproveUserDTO> findAllWithoutAdventures();
}
