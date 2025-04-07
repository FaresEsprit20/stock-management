package com.fares.stock.management.domain.controllers;

import com.fares.stock.management.domain.controllers.api.ProductApi;
import com.fares.stock.management.domain.dto.customer_order_line.CustomerOrderLineDto;
import com.fares.stock.management.domain.dto.product.ProductDto;
import com.fares.stock.management.domain.dto.sale_line.SaleLineDto;
import com.fares.stock.management.domain.dto.supplier_order_line.SupplierOrderLineDto;
import com.fares.stock.management.domain.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController implements ProductApi {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @Override
    public ProductDto save(ProductDto dto) {
        return productService.save(dto);
    }

    @Override
    public ProductDto findById(Integer id) {
        return productService.findById(id);
    }

    @Override
    public ProductDto findByProductCode(String productCode) {
        return productService.findByCodeProduct(productCode);
    }

    @Override
    public List<ProductDto> findAll() {
        return productService.findAll();
    }

    @Override
    public List<SaleLineDto> findSalesHistory(Integer productId) {
        return productService.findSalesHistory(productId);
    }

    @Override
    public List<CustomerOrderLineDto> findCustomerOrderHistory(Integer productId) {
        return productService.findCustomerOrderHistory(productId);
    }

    @Override
    public List<SupplierOrderLineDto> findSupplierOrderHistory(Integer productId) {
        return productService.findSupplierOrderHistory(productId);
    }

    @Override
    public List<ProductDto> findAllProductsByCategoryId(Integer categoryId) {
        return productService.findAllProductsByIdCategory(categoryId);
    }

    @Override
    public void delete(Integer id) {
        productService.delete(id);
    }

}