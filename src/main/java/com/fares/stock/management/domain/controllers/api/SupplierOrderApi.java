package com.fares.stock.management.domain.controllers.api;

import com.fares.stock.management.domain.dto.supplier_dto.SupplierOrderDto;
import com.fares.stock.management.domain.dto.supplier_order_line.SupplierOrderLineDto;
import com.fares.stock.management.domain.entities.enums.OrderStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.fares.stock.management.core.utils.constants.constants.Constants.*;

@Tag(name = "Supplier Orders", description = "API for managing supplier orders")
public interface SupplierOrderApi {

    @PostMapping(CREATE_SUPPLIER_ORDER_ENDPOINT)
    @Operation(summary = "Create supplier order", description = "Creates a new supplier order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supplier order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid supplier order data")
    })
    SupplierOrderDto save(@RequestBody SupplierOrderDto dto);

    @PatchMapping(SUPPLIER_ORDER_ENDPOINT + "/update/status/{orderId}/{status}")
    @Operation(summary = "Update order status", description = "Updates the status of a supplier order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    SupplierOrderDto updateOrderStatus(
            @PathVariable("orderId") Integer orderId,
            @PathVariable("status") OrderStatus status);

    @PatchMapping(SUPPLIER_ORDER_ENDPOINT + "/update/quantity/{orderId}/{orderLineId}/{quantity}")
    @Operation(summary = "Update order line quantity", description = "Updates the quantity of an item in the supplier order")
    SupplierOrderDto updateOrderLineQuantity(
            @PathVariable("orderId") Integer orderId,
            @PathVariable("orderLineId") Integer orderLineId,
            @PathVariable("quantity") BigDecimal quantity);

    @PatchMapping(SUPPLIER_ORDER_ENDPOINT + "/update/supplier/{orderId}/{supplierId}")
    @Operation(summary = "Update order supplier", description = "Changes the supplier associated with the order")
    SupplierOrderDto updateSupplier(
            @PathVariable("orderId") Integer orderId,
            @PathVariable("supplierId") Integer supplierId);

    @PatchMapping(SUPPLIER_ORDER_ENDPOINT + "/update/product/{orderId}/{orderLineId}/{productId}")
    @Operation(summary = "Update order line product", description = "Changes the product in a supplier order line")
    SupplierOrderDto updateProduct(
            @PathVariable("orderId") Integer orderId,
            @PathVariable("orderLineId") Integer orderLineId,
            @PathVariable("productId") Integer productId);

    @DeleteMapping(SUPPLIER_ORDER_ENDPOINT + "/delete/product/{orderId}/{orderLineId}")
    @Operation(summary = "Remove product from order", description = "Deletes a product line from the supplier order")
    SupplierOrderDto deleteProduct(
            @PathVariable("orderId") Integer orderId,
            @PathVariable("orderLineId") Integer orderLineId);

    @GetMapping(FIND_SUPPLIER_ORDER_BY_ID_ENDPOINT)
    @Operation(summary = "Find order by ID", description = "Retrieves a supplier order by its ID")
    SupplierOrderDto findById(@PathVariable("idSupplierOrder") Integer id);

    @GetMapping(FIND_SUPPLIER_ORDER_BY_CODE_ENDPOINT)
    @Operation(summary = "Find order by code", description = "Retrieves a supplier order by its code")
    SupplierOrderDto findByCode(@PathVariable("codeSupplierOrder") String code);

    @GetMapping(FIND_ALL_SUPPLIER_ORDER_ENDPOINT)
    @Operation(summary = "Get all supplier orders", description = "Retrieves all supplier orders")
    List<SupplierOrderDto> findAll();

    @GetMapping(SUPPLIER_ORDER_ENDPOINT + "/order-lines/{orderId}")
    @Operation(summary = "Get order lines", description = "Retrieves all order lines for a supplier order")
    List<SupplierOrderLineDto> findAllOrderLinesByOrderId(@PathVariable("orderId") Integer orderId);

    @DeleteMapping(DELETE_SUPPLIER_ORDER_ENDPOINT)
    @Operation(summary = "Delete supplier order", description = "Deletes a supplier order by ID")
    void delete(@PathVariable("idSupplierOrder") Integer id);

}
