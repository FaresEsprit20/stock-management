package com.fares.stock.management.domain.repository.jpa;

import com.fares.stock.management.domain.entities.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Integer> {

    Optional<CustomerOrder> findCustomerOrderByCode(String code);

    List<CustomerOrder> findAllByCustomerId(Integer id);

}
