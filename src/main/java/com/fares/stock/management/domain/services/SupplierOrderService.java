package com.fares.stock.management.domain.services;

import com.fares.stock.management.domain.dto.supplier_dto.SupplierOrderDto;
import com.fares.stock.management.domain.dto.supplier_order_line.SupplierOrderLineDto;
import com.fares.stock.management.domain.entities.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public interface SupplierOrderService {

    SupplierOrderDto save(SupplierOrderDto supplierOrderDto);

    SupplierOrderDto updateOrderStatus(Integer orderId, OrderStatus orderStatus);

    SupplierOrderDto updateOrderQuantity(Integer orderId, Integer orderLineId, BigDecimal quantity);

    SupplierOrderDto updateSupplier(Integer orderId, Integer supplierId);

    SupplierOrderDto updateProduct(Integer orderId, Integer orderLineId, Integer productId);

    // Delete product ==> delete SupplierOrderLine
    SupplierOrderDto deleteProduct(Integer orderId, Integer orderLineId);

    SupplierOrderDto findById(Integer id);

    SupplierOrderDto findByCode(String code);

    List<SupplierOrderDto> findAll();

    List<SupplierOrderLineDto> findAllSupplierOrderLineBySupplierOrderId(Integer orderId);

    void delete(Integer id);

}
