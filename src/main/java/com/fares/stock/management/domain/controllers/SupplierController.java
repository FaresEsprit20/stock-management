package com.fares.stock.management.domain.controllers;

import com.fares.stock.management.domain.controllers.api.SupplierApi;
import com.fares.stock.management.domain.dto.supplier.SupplierDto;
import com.fares.stock.management.domain.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SupplierController implements SupplierApi {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @Override
    public SupplierDto save(SupplierDto supplierDto) {
        return supplierService.save(supplierDto);
    }

    @Override
    public SupplierDto findById(Integer supplierId) {
        return supplierService.findById(supplierId);
    }

    @Override
    public List<SupplierDto> findAll() {
        return supplierService.findAll();
    }

    @Override
    public void delete(Integer id) {
        supplierService.delete(id);
    }


}

