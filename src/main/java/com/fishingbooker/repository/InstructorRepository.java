package com.fishingbooker.repository;

import com.fishingbooker.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InstructorRepository extends JpaRepository<Instructor, String> {
    @Override
    @Query("SELECT DISTINCT instructor FROM Instructor instructor " +
            "JOIN FETCH instructor.adventures adventures")
    List<Instructor> findAll();
}
