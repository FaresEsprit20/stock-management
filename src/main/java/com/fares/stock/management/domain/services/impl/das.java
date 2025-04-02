package com.fares.stock.management.domain.services.impl;

import com.fares.stock.management.core.exception.EntityNotFoundException;
import com.fares.stock.management.core.exception.ErrorCodes;
import com.fares.stock.management.core.exception.InvalidEntityException;
import com.fares.stock.management.core.exception.InvalidOperationException;
import com.fares.stock.management.core.validators.ProductDtoValidator;
import com.fares.stock.management.core.validators.SupplierOrderDtoValidator;
import com.fares.stock.management.domain.dto.product.ProductDto;
import com.fares.stock.management.domain.dto.stock_movement.StockMovementDto;
import com.fares.stock.management.domain.dto.supplier.SupplierDto;
import com.fares.stock.management.domain.dto.supplier_dto.SupplierOrderDto;
import com.fares.stock.management.domain.dto.supplier_order_line.SupplierOrderLineDto;
import com.fares.stock.management.domain.entities.Product;
import com.fares.stock.management.domain.entities.Supplier;
import com.fares.stock.management.domain.entities.SupplierOrder;
import com.fares.stock.management.domain.entities.SupplierOrderLine;
import com.fares.stock.management.domain.entities.enums.OrderStatus;
import com.fares.stock.management.domain.entities.enums.StockMvtSource;
import com.fares.stock.management.domain.entities.enums.StockMvtType;
import com.fares.stock.management.domain.repository.jpa.ProductRepository;
import com.fares.stock.management.domain.repository.jpa.SupplierOrderLineRepository;
import com.fares.stock.management.domain.repository.jpa.SupplierOrderRepository;
import com.fares.stock.management.domain.repository.jpa.SupplierRepository;
import com.fares.stock.management.domain.services.SupplierOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SupplierOrderServiceImpl implements SupplierOrderService {

    private final SupplierOrderRepository supplierOrderRepository;
    private final SupplierOrderLineRepository supplierOrderLineRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final StockMovementService stockMovementService;


    @Autowired
    public SupplierOrderServiceImpl(SupplierOrderRepository supplierOrderRepository, SupplierOrderLineRepository supplierOrderLineRepository, SupplierRepository supplierRepository, ProductRepository productRepository, StockMovementService stockMovementService) {
        this.supplierOrderRepository = supplierOrderRepository;
        this.supplierOrderLineRepository = supplierOrderLineRepository;
        this.supplierRepository = supplierRepository;
        this.productRepository = productRepository;
        this.stockMovementService = stockMovementService;
    }


    @Override
    public SupplierOrderDto save(SupplierOrderDto dto) {

        List<String> errors = SupplierOrderDtoValidator.validate(dto);

        if (!errors.isEmpty()) {
            log.error("Supplier Order validation failed: {}", errors);
            throw new InvalidEntityException("The supplier order is not valid", ErrorCodes.SUPPLIER_ORDER_NOT_VALID, errors);
        }

        if (dto.getId() != null && dto.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible to modify the order when it's already delivered", ErrorCodes.SUPPLIER_ORDER_NON_MODIFIABLE);
        }

        Optional<Supplier> supplier = supplierRepository.findById(dto.getSupplier().getId());
        if (supplier.isEmpty()) {
            log.warn("Supplier with ID {} was not found in the DB", dto.getSupplier().getId());
            throw new EntityNotFoundException("No supplier with the ID" + dto.getSupplier().getId() + " has been found in the DB",
                    ErrorCodes.SUPPLIER_NOT_FOUND);
        }

        List<String> articleErrors = new ArrayList<>();

        if (dto.getSupplierOrderLines() != null) {
            dto.getSupplierOrderLines().forEach(ligCmdFrs -> {
                if (ligCmdFrs.getProduct() != null) {
                    Optional<Product> article = productRepository.findById(ligCmdFrs.getProduct().getId());
                    if (article.isEmpty()) {
                        articleErrors.add("The product with the ID " + ligCmdFrs.getProduct().getId() + " does not exist in the DB");
                    }
                } else {
                    articleErrors.add("Impossible to save the order with a NULL product ");
                }
            });
        }

        if (!articleErrors.isEmpty()) {
            log.warn("");
            throw new InvalidEntityException("Product does not exist in the DB", ErrorCodes.ARTICLE_NOT_FOUND, articleErrors);
        }
        dto.setOrderDate(Instant.now());
        SupplierOrder savedCmdFrs = supplierOrderRepository.save(SupplierOrderDto.toEntity(dto));

        if (dto.getSupplierOrderLines() != null) {
            dto.getSupplierOrderLines().forEach(ligCmdFrs -> {
                SupplierOrderLine supplierOrderLine = SupplierOrderLineDto.toEntity(ligCmdFrs);
                supplierOrderLine.setSupplierOrder(savedCmdFrs);
                supplierOrderLine.setCompanyId(savedCmdFrs.getIdEnterprise());
                SupplierOrderLine saveLine = supplierOrderLineRepository.save(supplierOrderLine);

                effectuerEntree(saveLine);
            });
        }

        return SupplierOrderDto.fromEntity(savedCmdFrs);
    }

    @Override
    public SupplierOrderDto findById(Integer id) {
        if (id == null) {
            log.error("Supplier Order ID is NULL");
            return null;
        }
        return supplierOrderRepository.findById(id)
                .map(SupplierOrderDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No supplier order has been found with the ID " + id, ErrorCodes.SUPPLIER_ORDER_NOT_FOUND
                ));
    }

    @Override
    public SupplierOrderDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("Supplier CODE is NULL");
            return null;
        }
        return supplierOrderRepository.findSupplierOrderByCode(code)
                .map(SupplierOrderDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No supplier order has been found with the  CODE " + code, ErrorCodes.SUPPLIER_ORDER_NOT_FOUND
                ));
    }

    @Override
    public List<SupplierOrderDto> findAll() {
        return supplierOrderRepository.findAll().stream()
                .map(SupplierOrderDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<SupplierOrderLineDto> findAllSupplierOrderLineBySupplierOrderId(Integer orderId) {
        return supplierOrderLineRepository.findAllBySupplierOrderId(orderId).stream()
                .map(SupplierOrderLineDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Supplier code  ID is NULL");
            return;
        }
        List<SupplierOrderLine> supplierOrderLines = supplierOrderLineRepository.findAllBySupplierOrderId(id);
        if (!supplierOrderLines.isEmpty()) {
            throw new InvalidOperationException("Impossible to delete a supplier order that is already used",
                    ErrorCodes.SUPPLIER_ORDER_ALREADY_IN_USE);
        }
        supplierOrderLineRepository.deleteById(id);
    }

    @Override
    public SupplierOrderDto updateOrderStatus(Integer orderId, OrderStatus orderStatus) {
        checkOrderId(orderId);
        if (!StringUtils.hasLength(String.valueOf(orderStatus))) {
            log.error("The order status is NULL");
            throw new InvalidOperationException("Impossible to modify the status of the order with a null status ",
                    ErrorCodes.SUPPLIER_ORDER_NON_MODIFIABLE);
        }
        SupplierOrderDto supplierOrderDto = checkOrderStatus(orderId);
        supplierOrderDto.setOrderStatus(orderStatus);

       SupplierOrder savedSupplierOrder = supplierOrderRepository.save(SupplierOrderDto.toEntity(supplierOrderDto));
        if (supplierOrderDto.isCommandeLivree()) {
            updateMvtStk(orderId);
        }
        return SupplierOrderDto.fromEntity(savedSupplierOrder);
    }


    @Override
    public SupplierOrderDto updateOrderQuantity(Integer orderId, Integer orderLineId, BigDecimal quantity) {
        checkOrderId(orderId);
        checkIdOrderLine(orderLineId);

        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) == 0) {
            log.error("The ID of the order line is NULL");
            throw new InvalidOperationException("Impossible to modify the status of the order with a null ou ZERO quantity ",
                    ErrorCodes.SUPPLIER_ORDER_NON_MODIFIABLE);
        }

        SupplierOrderDto supplierOrderDto = checkOrderStatus(orderId);
        Optional<SupplierOrderLine> ligneCommandeFournisseurOptional = findSupplierOrderLine(orderLineId);

        if(ligneCommandeFournisseurOptional.isEmpty()){
            log.error("The order line  is NULL");
            throw new EntityNotFoundException("Impossible to get the order line with Null value " ,
                    ErrorCodes.SUPPLIER_ORDER_NOT_FOUND);
        }

        SupplierOrderLine supplierOrderLine = ligneCommandeFournisseurOptional.get();
        supplierOrderLine.setQuantity(quantity);
        supplierOrderLineRepository.save(supplierOrderLine);

        return supplierOrderDto;
    }

    @Override
    public SupplierOrderDto updateSupplier(Integer orderId, Integer supplierId) {
        checkOrderId(orderId);
        if (supplierId == null) {
            log.error("the ID of the supplier is NULL");
            throw new InvalidOperationException("Impossible to modify the status of the order with a nul supplier IDl",
                    ErrorCodes.SUPPLIER_ORDER_NON_MODIFIABLE);
        }
        SupplierOrderDto supplierOrderDto = checkOrderStatus(orderId);
        Optional<Supplier> fournisseurOptional = supplierRepository.findById(supplierId);
        if (fournisseurOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "No Supplier has been found with the ID " + supplierId, ErrorCodes.SUPPLIER_NOT_FOUND);
        }
        supplierOrderDto.setSupplier(SupplierDto.fromEntity(fournisseurOptional.get()));

        return SupplierOrderDto.fromEntity(
                supplierOrderRepository.save(SupplierOrderDto.toEntity(supplierOrderDto))
        );
    }

    @Override
    public SupplierOrderDto updateProduct(Integer orderId, Integer orderLineId, Integer productId) {
        checkOrderId(orderId);
        checkIdOrderLine(orderLineId);
        checkIdArticle(productId, "nouvel");

        SupplierOrderDto commandeFournisseur = checkOrderStatus(orderId);

        Optional<SupplierOrderLine> ligneCommandeFournisseur = findSupplierOrderLine(orderLineId);

        Optional<Product> articleOptional = productRepository.findById(productId);
        if (articleOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "No product has been found with the ID " + productId, ErrorCodes.ARTICLE_NOT_FOUND);
        }

        List<String> errors = ProductDtoValidator.validate(ProductDto.fromEntity(articleOptional.get()));
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Article invalid", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }

        if (ligneCommandeFournisseur.isEmpty()) {
            throw new EntityNotFoundException(
                    "Order Line of the supplier has not been found " + productId, ErrorCodes.ARTICLE_NOT_FOUND);
        }
        SupplierOrderLine supplierOrderLineToSaved = ligneCommandeFournisseur.get();
        supplierOrderLineToSaved.setProduct(articleOptional.get());
        supplierOrderLineRepository.save(supplierOrderLineToSaved);

        return commandeFournisseur;
    }

    @Override
    public SupplierOrderDto deleteSupplierOrderLine(Integer orderId, Integer orderLineId) {
        checkOrderId(orderId);
        checkIdOrderLine(orderLineId);

        SupplierOrderDto supplierOrderDto = checkOrderStatus(orderId);
        // Just to check the SupplierOrderLine and inform the supplier in case it is absent
        findSupplierOrderLine(orderLineId);
        supplierOrderLineRepository.deleteById(orderLineId);

        return supplierOrderDto;
    }

    private SupplierOrderDto checkOrderStatus(Integer orderId) {
        SupplierOrderDto supplierOrderDto = findById(orderId);
        if (supplierOrderDto.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modify the order when it's already delivered",
                    ErrorCodes.SUPPLIER_ORDER_NON_MODIFIABLE);
        }
        return supplierOrderDto;
    }

    private Optional<SupplierOrderLine> findSupplierOrderLine(Integer orderLineId) {
        Optional<SupplierOrderLine> ligneCommandeFournisseurOptional =
                supplierOrderLineRepository.findById(orderLineId);
        if (ligneCommandeFournisseurOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "No supplier order line has been found with the ID " + orderLineId,
                    ErrorCodes.SUPPLIER_ORDER_NOT_FOUND);
        }
        return ligneCommandeFournisseurOptional;
    }

    private void checkOrderId(Integer orderId) {
        if (orderId == null) {
            log.error("Supplier Order ID is NULL");
            throw new InvalidOperationException("Impossible to modify the state of the order" +
                    " with a null ID",
                    ErrorCodes.SUPPLIER_ORDER_NON_MODIFIABLE);
        }
    }

    private void checkIdOrderLine(Integer orderLineId) {
        if (orderLineId == null) {
            log.error("The ID of the order line is NULL");
            throw new InvalidOperationException("Impossible to modify the state of the order with a" +
                    " null order line ID",
                    ErrorCodes.SUPPLIER_ORDER_NON_MODIFIABLE);
        }
    }

    private void checkIdArticle(Integer productId, String msg) {
        if (productId == null) {
            log.error(" The ID of " + msg + " is NULL");
            throw new InvalidOperationException("Impossible to modify the state of the order with a " + msg +
                    " null product ID ",
                    ErrorCodes.SUPPLIER_ORDER_NON_MODIFIABLE);
        }
    }

    private void updateMvtStk(Integer orderId) {
        List<SupplierOrderLine> supplierOrderLines = supplierOrderLineRepository.findAllBySupplierOrderId(orderId);
        supplierOrderLines.forEach(lig -> {
            effectuerEntree(lig);
        });
    }

    private void effectuerEntree(SupplierOrderLine lig) {
        StockMovementDto mvtStkDto = new StockMovementDto();
        mvtStkDto.setProduct(ProductDto.fromEntity(lig.getProduct()));
        mvtStkDto.setMovementDate(Instant.now());
        mvtStkDto.setStockMvtType(StockMvtType.OUT);
        mvtStkDto.setMovementSource(StockMvtSource.SUPPLIER_COMMAND);
        mvtStkDto.setQuantity(lig.getQuantity());
        mvtStkDto.setCompanyId(lig.getCompanyId());
        stockMovementService.entreeStock(mvtStkDto);
    }


}
