package com.fares.stock.management.domain.dto.customer_order;

import com.fares.stock.management.domain.dto.customer.CustomerDto;
import com.fares.stock.management.domain.dto.customer_order_line.CustomerOrderLineDto;
import com.fares.stock.management.domain.entities.CustomerOrder;
import com.fares.stock.management.domain.entities.enums.OrderStatus;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerOrderDto {

    private Integer id;
    private String code;
    private Instant orderDate;
    private OrderStatus orderStatus;
    private Integer companyId;
    private CustomerDto customer;
    private List<CustomerOrderLineDto> orderLines;

    public CustomerOrderDto() {
    }

    public CustomerOrderDto(String code, Instant orderDate, OrderStatus orderStatus, Integer companyId, CustomerDto customer, List<CustomerOrderLineDto> orderLines) {
        this.code = code;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.companyId = companyId;
        this.customer = customer;
        this.orderLines = orderLines;
    }

    public Integer getId() {
        return id;
    }

    public void setCode(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public List<CustomerOrderLineDto> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<CustomerOrderLineDto> orderLines) {
        this.orderLines = orderLines;
    }

    public static CustomerOrderDto fromEntity(CustomerOrder order) {
        if (order == null) {
            return null;
        }
        return new CustomerOrderDto(
                order.getCode(),
                order.getOrderDate(),
                order.getOrderStatus(),
                order.getCompanyId(),
                CustomerDto.fromEntity(order.getCustomer()),
                order.getOrderLines() != null ? order.getOrderLines().stream().map(CustomerOrderLineDto::fromEntity).collect(Collectors.toList()) : null
        );
    }

    public static CustomerOrder toEntity(CustomerOrderDto orderDto) {
        if (orderDto == null) {
            return null;
        }
        CustomerOrder order = new CustomerOrder();
        order.setCode(orderDto.getCode());
        order.setOrderDate(orderDto.getOrderDate());
        order.setOrderStatus(orderDto.getOrderStatus());
        order.setCompanyId(orderDto.getCompanyId());
        order.setCustomer(CustomerDto.toEntity(orderDto.getCustomer()));
        return order;
    }

    public boolean isCommandeLivree() {
        return OrderStatus.LIVREE.equals(this.orderStatus);
    }


}
