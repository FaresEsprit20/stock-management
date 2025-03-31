package com.fares.stock.management.domain.repository.jpa;

import com.fares.stock.management.domain.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {

}