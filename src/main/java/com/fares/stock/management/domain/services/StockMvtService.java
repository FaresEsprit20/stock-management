package com.fares.stock.management.domain.services;

import com.fares.stock.management.domain.dto.stock_movement.StockMovementDto;

import java.math.BigDecimal;
import java.util.List;

public interface StockMvtService {

    BigDecimal stockReelProduct(Integer productId);

    List<StockMovementDto> mvtStkProduct(Integer productId);

    StockMovementDto operateInStock(StockMovementDto stockMovementDto);

    StockMovementDto operateOutStock(StockMovementDto stockMovementDto);

    StockMovementDto correctionStockPos(StockMovementDto stockMovementDto);

    StockMovementDto correctionStockNeg(StockMovementDto stockMovementDto);

}
