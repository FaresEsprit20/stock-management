package com.fares.stock.management.domain.controllers;

import com.fares.stock.management.domain.controllers.api.SupplierOrderApi;
import com.fares.stock.management.domain.dto.supplier_dto.SupplierOrderDto;
import com.fares.stock.management.domain.dto.supplier_order_line.SupplierOrderLineDto;
import com.fares.stock.management.domain.entities.enums.OrderStatus;
import com.fares.stock.management.domain.services.SupplierOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class SupplierOrderController implements SupplierOrderApi {

    private final SupplierOrderService supplierOrderService;

    @Autowired
    public SupplierOrderController(SupplierOrderService supplierOrderService) {
        this.supplierOrderService = supplierOrderService;
    }


    @Override
    public SupplierOrderDto save(SupplierOrderDto dto) {
        return supplierOrderService.save(dto);
    }

    @Override
    public SupplierOrderDto updateOrderStatus(Integer orderId, OrderStatus orderStatus) {
        return supplierOrderService.updateOrderStatus(orderId, orderStatus);
    }

    @Override
    public SupplierOrderDto updateOrderLineQuantity(Integer orderId, Integer orderLineId, BigDecimal quantity) {
        return supplierOrderService.updateOrderQuantity(orderId, orderLineId, quantity);
    }

    @Override
    public SupplierOrderDto updateSupplier(Integer orderId, Integer idSupplier) {
        return supplierOrderService.updateSupplier(orderId, idSupplier);
    }

    @Override
    public SupplierOrderDto updateProduct(Integer orderId, Integer orderLineId, Integer productId) {
        return supplierOrderService.updateProduct(orderId, orderLineId, productId);
    }

    @Override
    public SupplierOrderDto deleteProduct(Integer orderId, Integer orderLineId) {
        return supplierOrderService.deleteSupplierOrderLine(orderId, orderLineId);
    }

    @Override
    public SupplierOrderDto findById(Integer id) {
        return supplierOrderService.findById(id);
    }

    @Override
    public SupplierOrderDto findByCode(String code) {
        return supplierOrderService.findByCode(code);
    }

    @Override
    public List<SupplierOrderDto> findAll() {
        return supplierOrderService.findAll();
    }

    @Override
    public List<SupplierOrderLineDto> findAllOrderLinesByOrderId(Integer orderId) {
        return supplierOrderService.findAllSupplierOrderLineBySupplierOrderId(orderId);
    }

    @Override
    public void delete(Integer id) {
        supplierOrderService.delete(id);
    }

}

