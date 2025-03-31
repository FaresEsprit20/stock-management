package com.fares.stock.management.domain.repository.jpa;

import com.fares.stock.management.domain.entities.CustomerOrder;
import com.fares.stock.management.domain.entities.Supplier;
import com.fares.stock.management.domain.entities.SupplierOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierOrderRepository extends JpaRepository<SupplierOrder, Integer> {

    Optional<SupplierOrder> findSupplierOrderByCode(String code);

    List<CustomerOrder> findAllBySupplierId(Integer id);

}
