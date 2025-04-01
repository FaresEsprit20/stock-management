package com.fares.stock.management.domain.services;

import com.fares.stock.management.domain.dto.supplier.SupplierDto;

import java.util.List;

public interface SupplierService {

    SupplierDto save(SupplierDto supplierDto);

    SupplierDto findById(Integer supplierId);

    List<SupplierDto> findAll();

    void delete(Integer supplierId);

}