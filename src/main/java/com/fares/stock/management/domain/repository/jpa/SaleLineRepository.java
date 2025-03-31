package com.fares.stock.management.domain.repository.jpa;

import com.fares.stock.management.domain.entities.SaleLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleLineRepository extends JpaRepository<SaleLine, Integer> {

    List<SaleLine> findAllByProductId(Integer idProduct);

    List<SaleLine> findAllBySaleId(Integer id);
}