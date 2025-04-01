package com.fares.stock.management.domain.services;

import com.fares.stock.management.domain.dto.customer.CustomerDto;

import java.util.List;

public interface CustomerService {

    CustomerDto save(CustomerDto customerDto);

    CustomerDto findById(Integer customerId);

    List<CustomerDto> findAll();

    void delete(Integer customerId);

}
