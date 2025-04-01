package com.fares.stock.management.domain.services.impl;

import com.fares.stock.management.core.exception.EntityNotFoundException;
import com.fares.stock.management.core.exception.ErrorCodes;
import com.fares.stock.management.core.exception.InvalidEntityException;
import com.fares.stock.management.core.exception.InvalidOperationException;
import com.fares.stock.management.core.validators.CustomerValidator;
import com.fares.stock.management.domain.dto.customer.CustomerDto;
import com.fares.stock.management.domain.entities.CustomerOrder;
import com.fares.stock.management.domain.repository.jpa.CustomerOrderRepository;
import com.fares.stock.management.domain.repository.jpa.CustomerRepository;
import com.fares.stock.management.domain.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerOrderRepository customerOrderRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerOrderRepository customerOrderRepository) {
        this.customerRepository = customerRepository;
        this.customerOrderRepository = customerOrderRepository;
    }


    @Override
    public CustomerDto save(CustomerDto customerDto) {
        List<String> errors = CustomerValidator.validate(customerDto);
        if (!errors.isEmpty()) {
            log.error(" Customer is not valid {}", customerDto);
            throw new InvalidEntityException("Customer Is not valid", ErrorCodes.CLIENT_NOT_VALID, errors);
        }

        return CustomerDto.fromEntity(
                customerRepository.save(
                        CustomerDto.toEntity(customerDto)
                )
        );
    }

    @Override
    public CustomerDto findById(Integer id) {
        if (id == null) {
            log.error(" Customer ID is null");
            return null;
        }
        return customerRepository.findById(id)
                .map(CustomerDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No Customer with the ID = " + id + " has been found in DB ",
                        ErrorCodes.CLIENT_NOT_FOUND)
                );
    }

    @Override
    public List<CustomerDto> findAll() {
        return customerRepository.findAll().stream()
                .map(CustomerDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer customerId) {
        if (customerId == null) {
            log.error("Customer ID is null");
            return;
        }
        List<CustomerOrder> customerOrders = customerOrderRepository.findAllByCustomerId(customerId);
        if (!customerOrders.isEmpty()) {
            throw new InvalidOperationException(" Impossible to delete a customer already used in customer orders ",
                    ErrorCodes.CLIENT_ALREADY_IN_USE);
        }
        customerRepository.deleteById(customerId);
    }


}

