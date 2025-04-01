package com.fares.stock.management.domain.services;

import com.fares.stock.management.domain.dto.customer_order_line.CustomerOrderLineDto;
import com.fares.stock.management.domain.dto.product.ProductDto;
import com.fares.stock.management.domain.dto.sale_line.SaleLineDto;
import com.fares.stock.management.domain.dto.supplier_order_line.SupplierOrderLineDto;

import java.util.List;


public interface ProductService {


    ProductDto save(ProductDto dto);

    ProductDto update(ProductDto dto);

    ProductDto findById(Integer id);

    ProductDto findByCodeProduct(String codeProduct);

    List<ProductDto> findAll();

    List<SaleLineDto> findSalesHistory(Integer idProduct);

    List<CustomerOrderLineDto> findCustomerOrderHistory(Integer idProduct);

    List<SupplierOrderLineDto> findSupplierOrderHistory(Integer idArticle);

    List<ProductDto> findAllProductsByIdCategory(Integer idCategory);

    void delete(Integer id);


}
