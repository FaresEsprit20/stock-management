package com.fares.stock.management.domain.controllers;

import com.fares.stock.management.domain.controllers.api.EnterpriseApi;
import com.fares.stock.management.domain.dto.enterprise.EnterpriseDto;
import com.fares.stock.management.domain.services.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EnterpriseController implements EnterpriseApi {

    private final EnterpriseService enterpriseService;

    @Autowired
    public EnterpriseController(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }


    @Override
    public EnterpriseDto save(EnterpriseDto enterpriseDto) {
        return enterpriseService.save(enterpriseDto);
    }

    @Override
    public EnterpriseDto findById(Integer enterpriseId) {
        return enterpriseService.findById(enterpriseId);
    }

    @Override
    public List<EnterpriseDto> findAll() {
        return enterpriseService.findAll();
    }

    @Override
    public void delete(Integer enterpriseId) {
        enterpriseService.delete(enterpriseId);
    }


}
