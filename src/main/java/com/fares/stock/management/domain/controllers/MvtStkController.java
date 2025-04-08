package com.fares.stock.management.domain.controllers;

import com.fares.stock.management.domain.controllers.api.StockMovementApi;
import com.fares.stock.management.domain.dto.stock_movement.StockMovementDto;
import com.fares.stock.management.domain.services.StockMvtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class MvtStkController implements StockMovementApi {

    private final StockMvtService stockMovementService;

    @Autowired
    public MvtStkController(StockMvtService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }


    @Override
    public BigDecimal getRealStockByArticle(Integer productId) {
        return stockMovementService.stockReelProduct(productId);
    }

    @Override
    public List<StockMovementDto> getStockMovementsByArticle(Integer idArticle) {
        return stockMovementService.mvtStkProduct(idArticle);
    }

    @Override
    public StockMovementDto recordStockEntry(StockMovementDto dto) {
        return stockMovementService.operateInStock(dto);
    }

    @Override
    public StockMovementDto recordStockExit(StockMovementDto dto) {
        return stockMovementService.operateOutStock(dto);
    }

    @Override
    public StockMovementDto recordPositiveStockAdjustment(StockMovementDto dto) {
        return stockMovementService.correctionStockPos(dto);
    }

    @Override
    public StockMovementDto recordNegativeStockAdjustment(StockMovementDto dto) {
        return stockMovementService.correctionStockNeg(dto);
    }


}

