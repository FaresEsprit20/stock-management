package com.fares.stock.management.domain.services;

import com.fares.stock.management.domain.dto.customer_order.CustomerOrderDto;
import com.fares.stock.management.domain.dto.customer_order_line.CustomerOrderLineDto;
import com.fares.stock.management.domain.entities.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerOrderService {

    CustomerOrderDto save(CustomerOrderDto customerOrderDto);

    CustomerOrderDto updateOrderStatus(Integer orderId, OrderStatus orderStatus);

    CustomerOrderDto updateOrderQuantity(Integer orderId, Integer orderLineId, BigDecimal quantity);

    CustomerOrderDto updateCustomer(Integer orderId, Integer idClient);

    CustomerOrderDto updateProduct(Integer orderId, Integer orderLineId, Integer newIdProduct);

    // Delete product ==> delete CustomerOrderLine
    CustomerOrderDto deleteCustomerOrderLine(Integer orderId, Integer orderLineId);

    CustomerOrderDto findById(Integer id);

    CustomerOrderDto findByCode(String code);

    List<CustomerOrderDto> findAll();

    List<CustomerOrderLineDto> findAllCustomerOrderLinesByCustomerOrderId(Integer idCommande);

    void delete(Integer id);

}