package com.fares.stock.management.domain.repository.jpa;

import com.fares.stock.management.domain.entities.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Integer> {

    Optional<Sales> findSaleByCode(String code);

}
