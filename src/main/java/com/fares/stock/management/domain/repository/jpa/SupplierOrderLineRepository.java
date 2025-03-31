package com.fares.stock.management.domain.repository.jpa;

import com.fares.stock.management.domain.entities.SupplierOrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierOrderLineRepository extends JpaRepository<SupplierOrderLine, Integer> {

    List<SupplierOrderLine> findAllBySupplierOrderId(Integer orderId);

    List<SupplierOrderLine> findAllByProductId(Integer orderId);

}
