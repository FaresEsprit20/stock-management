package com.fares.stock.management.domain.services.impl;

import com.fares.stock.management.core.exception.EntityNotFoundException;
import com.fares.stock.management.core.exception.ErrorCodes;
import com.fares.stock.management.core.exception.InvalidEntityException;
import com.fares.stock.management.core.exception.InvalidOperationException;
import com.fares.stock.management.core.validators.ProductDtoValidator;
import com.fares.stock.management.domain.dto.customer_order_line.CustomerOrderLineDto;
import com.fares.stock.management.domain.dto.product.ProductDto;
import com.fares.stock.management.domain.dto.sale_line.SaleLineDto;
import com.fares.stock.management.domain.dto.supplier_order_line.SupplierOrderLineDto;
import com.fares.stock.management.domain.entities.CustomerOrderLine;
import com.fares.stock.management.domain.entities.SaleLine;
import com.fares.stock.management.domain.entities.SupplierOrderLine;
import com.fares.stock.management.domain.repository.jpa.CustomerOrderLineRepository;
import com.fares.stock.management.domain.repository.jpa.ProductRepository;
import com.fares.stock.management.domain.repository.jpa.SaleLineRepository;
import com.fares.stock.management.domain.repository.jpa.SupplierOrderLineRepository;
import com.fares.stock.management.domain.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SaleLineRepository saleLineRepository;
    private final SupplierOrderLineRepository supplierOrderLineRepository;
    private final CustomerOrderLineRepository customerOrderLineRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, SaleLineRepository saleLineRepository, SupplierOrderLineRepository supplierOrderLineRepository, CustomerOrderLineRepository customerOrderLineRepository) {
        this.productRepository = productRepository;
        this.saleLineRepository = saleLineRepository;
        this.supplierOrderLineRepository = supplierOrderLineRepository;
        this.customerOrderLineRepository = customerOrderLineRepository;
    }


    @Override
    public ProductDto save(ProductDto productDto) {
        List<String> errors = ProductDtoValidator.validate(productDto);
        if (!errors.isEmpty()) {
            log.error("Product is not valid {}", productDto);
            throw new InvalidEntityException("Product is not valid", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }

        return ProductDto.fromEntity(
                productRepository.save(
                        ProductDto.toEntity(productDto)
                )
        );
    }

    @Override
    public ProductDto update(ProductDto dto) {
        return null;
    }

    @Override
    public ProductDto findById(Integer productId) {
        if (productId == null) {
            log.error("Product ID is null");
            return null;
        }

        return productRepository.findById(productId).map(ProductDto::fromEntity).orElseThrow(() ->
                new EntityNotFoundException(
                        " No Product with ID = " + productId + " has been found in DB ",
                        ErrorCodes.ARTICLE_NOT_FOUND)
        );
    }

    @Override
    public ProductDto findByCodeProduct(String productCode) {
        if (!StringUtils.hasLength(productCode)) {
            log.error("Product CODE is null");
            return null;
        }

        return productRepository.findArticleByCodeProduct(productCode)
                .map(ProductDto::fromEntity)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "No Product with ID = " + productCode + " has been found in DB",
                                ErrorCodes.ARTICLE_NOT_FOUND)
                );
    }

    @Override
    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<SaleLineDto> findSalesHistory(Integer productId) {
        return saleLineRepository.findAllByProductId(productId).stream()
                .map(SaleLineDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerOrderLineDto> findCustomerOrderHistory(Integer productId) {
        return customerOrderLineRepository.findAllByProductId(productId).stream()
                .map(CustomerOrderLineDto::fromEntity)
                .collect(Collectors.toList());
    }


    @Override
    public List<SupplierOrderLineDto> findSupplierOrderHistory(Integer productId) {
        return supplierOrderLineRepository.findAllByProductId(productId).stream()
                .map(SupplierOrderLineDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findAllProductsByIdCategory(Integer categoryId) {
        return productRepository.findAllByCategoryId(categoryId).stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer productId) {
        if (productId == null) {
            log.error("product ID is null");
            return;
        }
        List<CustomerOrderLine> customerOrderLines = customerOrderLineRepository.findAllByProductId(productId);
        if (!customerOrderLines.isEmpty()) {
            throw new InvalidOperationException("Impossible to delete a product already used in customer orders ",
                    ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }
        List<SupplierOrderLine> supplierOrderLines = supplierOrderLineRepository.findAllByProductId(productId);
        if (!supplierOrderLines.isEmpty()) {
            throw new InvalidOperationException("Impossible to delete a product already used in supplier orders ",
                    ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }
        List<SaleLine> saleLines = saleLineRepository.findAllByProductId(productId);
        if (!saleLines.isEmpty()) {
            throw new InvalidOperationException("Impossible to delete a product already used in Sale Lines ",
                    ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }
        productRepository.deleteById(productId);
    }



    
}
