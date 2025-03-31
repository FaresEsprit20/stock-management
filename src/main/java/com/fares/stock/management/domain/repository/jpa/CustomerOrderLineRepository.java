package com.fares.stock.management.domain.repository.jpa;

import com.fares.stock.management.domain.entities.CustomerOrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerOrderLineRepository extends JpaRepository<CustomerOrderLine, Integer> {

    List<CustomerOrderLine> findAllByCustomerOrderId(Integer id);

    List<CustomerOrderLine> findAllByArticleId(Integer id);

}