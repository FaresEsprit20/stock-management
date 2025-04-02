package com.fares.stock.management.domain.services;

import com.fares.stock.management.domain.dto.sales.SalesDto;

import java.util.List;

public interface SalesService {

    SalesDto save(SalesDto dto);

    SalesDto findById(Integer id);

    SalesDto findByCode(String code);

    List<SalesDto> findAll();

    void delete(Integer id);

}
