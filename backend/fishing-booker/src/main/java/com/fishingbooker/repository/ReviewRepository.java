package com.fishingbooker.repository;

import com.fishingbooker.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select avg(r.rentableRating) from Review r where r.rentableName = :name and r.ownerUsername = :owner and r.isPublic = true")
    double getAverageRatingByNameAndOwner(@Param("name") String name, @Param("owner") String owner);
    @Query("select count(r.rentableName) from Review r where r.rentableName = :name and r.ownerUsername = :owner and r.isPublic = true")
    int getNumberOfReviewsByNameAndOwner(@Param("name") String name, @Param("owner") String owner);
    @Query("select avg(r.ownerRating) from Review r where r.ownerUsername = :owner and r.isPublic = true")
    double getOwnerAverageRating(@Param("owner") String owner);
    @Query("select count(r.ownerUsername) from Review r where r.ownerUsername = :owner and r.isPublic = true")
    int getNumberOfOwnerReviews(@Param("owner") String owner);
}
