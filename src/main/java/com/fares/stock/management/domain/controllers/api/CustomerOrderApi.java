package com.fares.stock.management.domain.controllers.api;

import com.fares.stock.management.domain.dto.customer_order.CustomerOrderDto;
import com.fares.stock.management.domain.dto.customer_order_line.CustomerOrderLineDto;
import com.fares.stock.management.domain.entities.enums.OrderStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.fares.stock.management.core.constants.Constants.APP_ROOT;

@Tag(name = "Customer Orders", description = "API for managing customer orders")
public interface CustomerOrderApi {

    @PostMapping(APP_ROOT + "/customerorders/create")
    @Operation(summary = "Create a new customer order", description = "Creates a new customer order in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer order successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid order data provided")
    })
    ResponseEntity<CustomerOrderDto> save(@RequestBody CustomerOrderDto dto);

    @PatchMapping(APP_ROOT + "/customerorders/update/status/{orderId}/{status}")
    @Operation(summary = "Update order status", description = "Updates the status of an existing customer order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order status successfully updated"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    ResponseEntity<CustomerOrderDto> updateOrderStatus(
            @PathVariable("orderId") Integer orderId,
            @PathVariable("status") OrderStatus status);

    @PatchMapping(APP_ROOT + "/customerorders/update/quantity/{orderId}/{orderLineId}/{quantity}")
    @Operation(summary = "Update order line quantity", description = "Updates the quantity of an item in a customer order")
    ResponseEntity<CustomerOrderDto> updateOrderLineQuantity(
            @PathVariable("orderId") Integer orderId,
            @PathVariable("orderLineId") Integer orderLineId,
            @PathVariable("quantity") BigDecimal quantity);

    @PatchMapping(APP_ROOT + "/customerorders/update/customer/{orderId}/{customerId}")
    @Operation(summary = "Update order customer", description = "Changes the customer associated with an order")
    ResponseEntity<CustomerOrderDto> updateCustomer(
            @PathVariable("orderId") Integer orderId,
            @PathVariable("customerId") Integer customerId);

    @PatchMapping(APP_ROOT + "/customerorders/update/product/{orderId}/{orderLineId}/{productId}")
    @Operation(summary = "Update order line product", description = "Changes the product in an order line")
    ResponseEntity<CustomerOrderDto> updateProduct(
            @PathVariable("orderId") Integer orderId,
            @PathVariable("orderLineId") Integer orderLineId,
            @PathVariable("productId") Integer productId);

    @DeleteMapping(APP_ROOT + "/customerorders/delete/product/{orderId}/{orderLineId}")
    @Operation(summary = "Remove product from order", description = "Deletes a product line from a customer order")
    ResponseEntity<CustomerOrderDto> deleteProduct(
            @PathVariable("orderId") Integer orderId,
            @PathVariable("orderLineId") Integer orderLineId);

    @GetMapping(APP_ROOT + "/customerorders/{orderId}")
    @Operation(summary = "Find order by ID", description = "Retrieves a customer order by its ID")
    ResponseEntity<CustomerOrderDto> findById(@PathVariable("orderId") Integer orderId);

    @GetMapping(APP_ROOT + "/customerorders/filter/{orderCode}")
    @Operation(summary = "Find order by code", description = "Retrieves a customer order by its unique code")
    ResponseEntity<CustomerOrderDto> findByCode(@PathVariable("orderCode") String code);

    @GetMapping(APP_ROOT + "/customerorders/all")
    @Operation(summary = "Get all customer orders", description = "Retrieves all customer orders in the system")
    ResponseEntity<List<CustomerOrderDto>> findAll();

    @GetMapping(APP_ROOT + "/customerorders/lines/{orderId}")
    @Operation(summary = "Get order lines", description = "Retrieves all order lines for a specific customer order")
    ResponseEntity<List<CustomerOrderLineDto>> findAllOrderLinesByOrderId(
            @PathVariable("orderId") Integer orderId);

    @DeleteMapping(APP_ROOT + "/customerorders/delete/{orderId}")
    @Operation(summary = "Delete customer order", description = "Permanently deletes a customer order")
    ResponseEntity<Void> delete(@PathVariable("orderId") Integer orderId);

}