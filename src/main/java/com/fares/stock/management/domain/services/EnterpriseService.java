package com.fares.stock.management.domain.services;

import com.fares.stock.management.domain.dto.enterprise.EnterpriseDto;

import java.util.List;


public interface EnterpriseService {

    EnterpriseDto save(EnterpriseDto enterpriseDto);

    EnterpriseDto findById(Integer enterpriseId);

    List<EnterpriseDto> findAll();

    void delete(Integer id);

}
