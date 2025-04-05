package com.fares.stock.management.domain.services.impl;

import com.fares.stock.management.core.exception.EntityNotFoundException;
import com.fares.stock.management.core.exception.ErrorCodes;
import com.fares.stock.management.core.exception.InvalidEntityException;
import com.fares.stock.management.core.exception.InvalidOperationException;
import com.fares.stock.management.core.validators.SalesDtoValidator;
import com.fares.stock.management.domain.dto.product.ProductDto;
import com.fares.stock.management.domain.dto.sale_line.SaleLineDto;
import com.fares.stock.management.domain.dto.sales.SalesDto;
import com.fares.stock.management.domain.dto.stock_movement.StockMovementDto;
import com.fares.stock.management.domain.entities.Product;
import com.fares.stock.management.domain.entities.SaleLine;
import com.fares.stock.management.domain.entities.Sales;
import com.fares.stock.management.domain.entities.enums.StockMvtSource;
import com.fares.stock.management.domain.entities.enums.StockMvtType;
import com.fares.stock.management.domain.repository.jpa.ProductRepository;
import com.fares.stock.management.domain.repository.jpa.SaleLineRepository;
import com.fares.stock.management.domain.repository.jpa.SalesRepository;
import com.fares.stock.management.domain.services.SalesService;
import com.fares.stock.management.domain.services.StockMvtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalesServiceImpl implements SalesService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final SalesRepository salesRepository;
    private final SaleLineRepository saleLineRepository;
    private final StockMvtService mvtStkService;
    private final ProductRepository productRepository;

    @Autowired
    public SalesServiceImpl(SalesRepository salesRepository, SaleLineRepository saleLineRepository, StockMvtService mvtStkService, ProductRepository productRepository) {
        this.salesRepository = salesRepository;
        this.saleLineRepository = saleLineRepository;
        this.mvtStkService = mvtStkService;
        this.productRepository = productRepository;
    }




    @Override
    public SalesDto save(SalesDto salesDto) {
        List<String> errors = SalesDtoValidator.validate(salesDto);
        if (!errors.isEmpty()) {
            log.error("Sales is not valid");
            throw new InvalidEntityException("The object Sale is not valid",
                    ErrorCodes.SALE_NOT_VALID, errors);
        }

        List<String> articleErrors = new ArrayList<>();

        salesDto.getSaleLines().forEach(ligneVenteDto -> {
            Optional<Product> article = productRepository.findById(ligneVenteDto.getProduct().getId());
            if (article.isEmpty()) {
                articleErrors.add("No product with teh ID " + ligneVenteDto.getProduct().getId() + " has been found in DB ");
            }
        });

        if (!articleErrors.isEmpty()) {
            log.error("One or more articles were not found in the DB, {}", errors);
            throw new InvalidEntityException("One or more articles were not found in the DB", ErrorCodes.SALE_NOT_VALID, errors);
        }

        Sales savedSales = salesRepository.save(SalesDto.toEntity(salesDto));

        salesDto.getSaleLines().forEach(ligneVenteDto -> {
            SaleLine ligneVente = SaleLineDto.toEntity(ligneVenteDto);
            ligneVente.setSale(savedSales);
            saleLineRepository.save(ligneVente);
            updateMvtStk(ligneVente);
        });

        return SalesDto.fromEntity(savedSales);
    }

    @Override
    public SalesDto findById(Integer salesId) {
        if (salesId == null) {
            log.error("Sale ID is NULL");
            throw new InvalidEntityException("Sale ID is not valid , NUll value found",
                    ErrorCodes.SALE_NOT_VALID);
        }
        return salesRepository.findById(salesId)
                .map(SalesDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No Sale has been found in the DB",
                        ErrorCodes.SALE_NOT_FOUND));
    }

    @Override
    public SalesDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("Sale CODE is NULL");
            throw new InvalidEntityException("Sale ID is not valid , NUll value found",
                    ErrorCodes.SALE_NOT_VALID);
        }
        return salesRepository.findSaleByCode(code)
                .map(SalesDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No customer Sale has been found with the CODE " + code, ErrorCodes.SALE_NOT_VALID
                ));
    }

    @Override
    public List<SalesDto> findAll() {
        return salesRepository.findAll().stream()
                .map(SalesDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Sale ID is NULL");
            throw new InvalidEntityException("Sale ID is not valid , NUll value found",
                    ErrorCodes.SALE_NOT_VALID);
        }
        List<SaleLine> saleLines = saleLineRepository.findAllBySaleId(id);
        if (!saleLines.isEmpty()) {
            throw new InvalidOperationException("Impossible to delete a Sale ...",
                    ErrorCodes.SALE_ALREADY_IN_USE);
        }
        salesRepository.deleteById(id);
    }

    private void updateMvtStk(SaleLine saleLine) {
        StockMovementDto mvtStkDto = new StockMovementDto();
                mvtStkDto.setProduct(ProductDto.fromEntity(saleLine.getProduct()));
                mvtStkDto.setMovementDate(Instant.now());
                mvtStkDto.setStockMvtType(StockMvtType.OUT);
                mvtStkDto.setMovementSource(StockMvtSource.SALE);
                mvtStkDto.setQuantity(saleLine.getQuantity());
                mvtStkDto.setCompanyId(saleLine.getCompanyId());

        mvtStkService.operateOutStock(mvtStkDto);
    }


}

