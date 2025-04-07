package com.fares.stock.management.domain.controllers;

import com.fares.stock.management.domain.controllers.api.CustomerOrderApi;
import com.fares.stock.management.domain.dto.customer_order.CustomerOrderDto;
import com.fares.stock.management.domain.dto.customer_order_line.CustomerOrderLineDto;
import com.fares.stock.management.domain.entities.enums.OrderStatus;
import com.fares.stock.management.domain.services.CustomerOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class CustomerOrderController implements CustomerOrderApi {

    private final CustomerOrderService customerOrderService;

    @Autowired
    public CustomerOrderController(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    @Override
    public ResponseEntity<CustomerOrderDto> save(CustomerOrderDto dto) {
        return ResponseEntity.ok(customerOrderService.save(dto));
    }

    @Override
    public ResponseEntity<CustomerOrderDto> updateOrderStatus(Integer orderId, OrderStatus orderStatus) {
        return ResponseEntity.ok(customerOrderService.updateOrderStatus(orderId, orderStatus));
    }

    @Override
    public ResponseEntity<CustomerOrderDto> updateOrderLineQuantity(Integer orderId, Integer orderLineId, BigDecimal quantity) {
        return ResponseEntity.ok(customerOrderService.updateOrderQuantity(orderId, orderLineId, quantity));
    }

    @Override
    public ResponseEntity<CustomerOrderDto> updateCustomer(Integer orderId, Integer customerId) {
        return ResponseEntity.ok(customerOrderService.updateCustomer(orderId, customerId));
    }

    @Override
    public ResponseEntity<CustomerOrderDto> updateProduct(Integer orderId, Integer orderLineId, Integer productId) {
        return ResponseEntity.ok(customerOrderService.updateProduct(orderId, orderLineId, productId));
    }

    @Override
    public ResponseEntity<CustomerOrderDto> deleteProduct(Integer orderId, Integer orderLineId) {
        return ResponseEntity.ok(customerOrderService.deleteCustomerOrderLine(orderId, orderLineId));
    }

    @Override
    public ResponseEntity<CustomerOrderDto> findById(Integer id) {
        return ResponseEntity.ok(customerOrderService.findById(id));
    }

    @Override
    public ResponseEntity<CustomerOrderDto> findByCode(String code) {
        return ResponseEntity.ok(customerOrderService.findByCode(code));
    }

    @Override
    public ResponseEntity<List<CustomerOrderDto>> findAll() {
        return ResponseEntity.ok(customerOrderService.findAll());
    }

    @Override
    public ResponseEntity<List<CustomerOrderLineDto>> findAllOrderLinesByOrderId(Integer orderId) {
        return ResponseEntity.ok(customerOrderService.findAllCustomerOrderLinesByCustomerOrderId(orderId));
    }

    @Override
    public ResponseEntity<Void> delete(Integer id) {
        customerOrderService.delete(id);
        return ResponseEntity.ok().build();
    }


}
