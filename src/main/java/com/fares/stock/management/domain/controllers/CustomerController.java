package com.fares.stock.management.domain.controllers;

import com.fares.stock.management.domain.controllers.api.CustomerApi;
import com.fares.stock.management.domain.dto.customer.CustomerDto;
import com.fares.stock.management.domain.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController implements CustomerApi {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public CustomerDto save(CustomerDto customerDto) {
        return customerService.save(customerDto);
    }

    @Override
    public CustomerDto findById(Integer id) {
        return customerService.findById(id);
    }

    @Override
    public List<CustomerDto> findAll() {
        return customerService.findAll();
    }

    @Override
    public void delete(Integer id) {
        customerService.delete(id);
    }


}
