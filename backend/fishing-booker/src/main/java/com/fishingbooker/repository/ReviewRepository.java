package com.fishingbooker.repository;

import com.fishingbooker.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select avg(r.rentableRating) from Review r where r.rentableName = :name and r.ownerUsername = :owner")
    double getAverageRatingByNameAndOwner(@Param("name") String name, @Param("owner") String owner);
    @Query("select count(r.rentableName) from Review r where r.rentableName = :name and r.ownerUsername = :owner")
    int getNumberOfReviewsByNameAndOwner(@Param("name") String name, @Param("owner") String owner);
}
