package com.fares.stock.management.domain.repository.jpa;

import com.fares.stock.management.domain.entities.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, Integer> {

}