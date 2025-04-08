package com.fares.stock.management.domain.controllers;

import com.fares.stock.management.domain.controllers.api.SalesApi;
import com.fares.stock.management.domain.dto.sales.SalesDto;
import com.fares.stock.management.domain.services.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SalesController implements SalesApi {

    private final SalesService salesService;

    @Autowired
    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }


    @Override
    public SalesDto createSale(SalesDto salesDto) {
        return salesService.save(salesDto);
    }

    @Override
    public SalesDto getSaleById(Integer id) {
        return salesService.findById(id);
    }

    @Override
    public SalesDto getSaleByCode(String code) {
        return salesService.findByCode(code);
    }

    @Override
    public List<SalesDto> getAllSales() {
        return salesService.findAll();
    }

    @Override
    public void deleteSale(Integer id) {
        salesService.delete(id);
    }
}

