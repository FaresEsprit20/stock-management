package com.fares.stock.management.domain.services.impl;

import com.fares.stock.management.core.exception.ErrorCodes;
import com.fares.stock.management.core.exception.InvalidEntityException;
import com.fares.stock.management.core.validators.StockMovementDtoValidator;
import com.fares.stock.management.domain.dto.stock_movement.StockMovementDto;
import com.fares.stock.management.domain.entities.enums.StockMvtType;
import com.fares.stock.management.domain.repository.jpa.StockMovementRepository;
import com.fares.stock.management.domain.services.ProductService;
import com.fares.stock.management.domain.services.StockMvtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockMovementServiceImpl implements StockMvtService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final StockMovementRepository repository;
    private final ProductService productService;

    @Autowired
    public StockMovementServiceImpl(StockMovementRepository repository, ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }


    @Override
    public BigDecimal stockReelProduct(Integer productId) {
        if (productId == null) {
            log.warn("Product ID is NULL");
            return BigDecimal.valueOf(-1);
        }
        productService.findById(productId);
        return repository.stockReelProduct(productId);
    }

    @Override
    public List<StockMovementDto> mvtStkProduct(Integer productId) {
        return repository.findAllByProductId(productId).stream()
                .map(StockMovementDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public StockMovementDto operateInStock(StockMovementDto dto) {
        return entreePositive(dto, StockMvtType.IN);
    }

    @Override
    public StockMovementDto operateOutStock(StockMovementDto dto) {
        return sortieNegative(dto, StockMvtType.OUT);
    }

    @Override
    public StockMovementDto correctionStockPos(StockMovementDto dto) {
        return entreePositive(dto, StockMvtType.POS_CORRECTION);
    }

    @Override
    public StockMovementDto correctionStockNeg(StockMovementDto dto) {
        return sortieNegative(dto, StockMvtType.NEG_CORRECTION);
    }

    private StockMovementDto entreePositive(StockMovementDto dto, StockMvtType typeMvtStk) {
        List<String> errors = StockMovementDtoValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Product is not valid {}", dto);
            throw new InvalidEntityException("The stock movement is not valid", ErrorCodes.MVT_STK_NOT_VALID, errors);
        }
        dto.setQuantity(
                BigDecimal.valueOf(
                        Math.abs(dto.getQuantity().doubleValue())
                )
        );
        dto.setStockMvtType(typeMvtStk);
        return StockMovementDto.fromEntity(
                repository.save(StockMovementDto.toEntity(dto))
        );
    }

    private StockMovementDto sortieNegative(StockMovementDto dto, StockMvtType typeMvtStk) {
        List<String> errors = StockMovementDtoValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Product is not valid {}", dto);
            throw new InvalidEntityException("The stock Movement is not valid ", ErrorCodes.MVT_STK_NOT_VALID, errors);
        }
        dto.setQuantity(
                BigDecimal.valueOf(
                        Math.abs(dto.getQuantity().doubleValue()) * -1
                )
        );
        dto.setStockMvtType(typeMvtStk);
        return StockMovementDto.fromEntity(
                repository.save(StockMovementDto.toEntity(dto))
        );
    }



}
