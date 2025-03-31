package com.fares.stock.management.domain.repository.jpa;


import com.fares.stock.management.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // JPQL query
    @Query(value = "select u from User u where u.email = :email")
    Optional<User> findUserByEmail(@Param("email") String email);

}
