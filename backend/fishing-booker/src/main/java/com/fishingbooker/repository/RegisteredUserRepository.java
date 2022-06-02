package com.fishingbooker.repository;

import com.fishingbooker.model.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Long> {
    RegisteredUser findByUsername(String username);

    @Override
    Optional<RegisteredUser> findById(Long aLong);

    @Transactional
   /* @Query(
            value = "select u.username, u.email, u.name, u.surname, u.role.roleName " +
                    "from RegisteredUser u, Instructor i, CottageOwner c, BoatOwner b " +
                    "INNER JOIN u.role r " +
                    "where r.id = u.role.id ")*/
    List<RegisteredUser> findAll();

    RegisteredUser getUserByUsername(String username);
}
