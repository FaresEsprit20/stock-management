package com.fares.stock.management.domain.services.impl;

import com.fares.stock.management.core.exception.EntityNotFoundException;
import com.fares.stock.management.core.exception.ErrorCodes;
import com.fares.stock.management.core.exception.InvalidEntityException;
import com.fares.stock.management.core.exception.InvalidOperationException;
import com.fares.stock.management.core.validators.SupplierValidator;
import com.fares.stock.management.domain.dto.supplier.SupplierDto;
import com.fares.stock.management.domain.entities.SupplierOrder;
import com.fares.stock.management.domain.repository.jpa.SupplierOrderRepository;
import com.fares.stock.management.domain.repository.jpa.SupplierRepository;
import com.fares.stock.management.domain.services.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final SupplierRepository supplierRepository;
    private final SupplierOrderRepository supplierOrderRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, SupplierOrderRepository supplierOrderRepository) {
        this.supplierRepository = supplierRepository;
        this.supplierOrderRepository = supplierOrderRepository;
    }


    @Override
    public SupplierDto save(SupplierDto dto) {
        List<String> errors = SupplierValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Supplier is not valid {}", dto);
            throw new InvalidEntityException("The supplier is not valid", ErrorCodes.SUPPLIER_NOT_VALID, errors);
        }

        return SupplierDto.fromEntity(
                supplierRepository.save(
                        SupplierDto.toEntity(dto)
                )
        );
    }

    @Override
    public SupplierDto findById(Integer supplierId) {
        if (supplierId == null) {
            log.error("Supplier ID is null");
            throw new InvalidEntityException("The supplier ID is not valid",
                    ErrorCodes.SUPPLIER_NOT_VALID);
        }
        return supplierRepository.findById(supplierId)
                .map(SupplierDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No Supplier with ID = " + supplierId + " has been found in the DB",
                        ErrorCodes.SUPPLIER_NOT_FOUND)
                );
    }

    @Override
    public List<SupplierDto> findAll() {
        return supplierRepository.findAll().stream()
                .map(SupplierDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Supplier ID is null");
            throw new InvalidEntityException("The supplier ID is not valid",
                    ErrorCodes.SUPPLIER_NOT_VALID);
        }
        List<SupplierOrder> supplierOrders = supplierOrderRepository.findAllBySupplierId(id);
        if (!supplierOrders.isEmpty()) {
            throw new InvalidOperationException("Impossible to delete a supplier who already has orders",
                    ErrorCodes.SUPPLIER_ORDER_ALREADY_IN_USE);
        }
        supplierRepository.deleteById(id);
    }


}
