package com.fares.stock.management.domain.controllers.api;

import com.fares.stock.management.domain.dto.customer_order_line.CustomerOrderLineDto;
import com.fares.stock.management.domain.dto.product.ProductDto;
import com.fares.stock.management.domain.dto.sale_line.SaleLineDto;
import com.fares.stock.management.domain.dto.supplier_order_line.SupplierOrderLineDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fares.stock.management.core.utils.constants.constants.Constants.APP_ROOT;

@Tag(name = "Products", description = "API for product operations")
public interface ProductApi {

    @PostMapping(value = APP_ROOT + "/products/create",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Save a product", description = "This method allows you to save or update a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The created/updated product object"),
            @ApiResponse(responseCode = "400", description = "The product object is not valid")
    })
    ProductDto save(@RequestBody ProductDto dto);

    @GetMapping(value = APP_ROOT + "/products/{idProduct}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find a product by ID", description = "This method allows you to search for a product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The product was found in the database"),
            @ApiResponse(responseCode = "404", description = "No product exists in the database with the provided ID")
    })
    ProductDto findById(@PathVariable("idProduct") Integer id);

    @GetMapping(value = APP_ROOT + "/products/filter/{productCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find a product by CODE", description = "This method allows you to search for a product by its CODE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The product was found in the database"),
            @ApiResponse(responseCode = "404", description = "No product exists in the database with the provided CODE")
    })
    ProductDto findByProductCode(@PathVariable("productCode") String productCode);

    @GetMapping(value = APP_ROOT + "/products/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get the list of products", description = "This method allows you to search and return the list of products that exist in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The list of products / An empty list")
    })
    List<ProductDto> findAll();

    @GetMapping(value = APP_ROOT + "/products/history/sales/{idProduct}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get sales history for a product")
    List<SaleLineDto> findSalesHistory(@PathVariable("idProduct") Integer idProduct);

    @GetMapping(value = APP_ROOT + "/products/history/customer-orders/{idProduct}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get customer order history for a product")
    List<CustomerOrderLineDto> findCustomerOrderHistory(@PathVariable("idProduct") Integer idProduct);

    @GetMapping(value = APP_ROOT + "/products/history/supplier-orders/{idProduct}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get supplier order history for a product")
    List<SupplierOrderLineDto> findSupplierOrderHistory(@PathVariable("idProduct") Integer idProduct);

    @GetMapping(value = APP_ROOT + "/products/filter/category/{idCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find all products by category ID")
    List<ProductDto> findAllProductsByCategoryId(@PathVariable("idCategory") Integer idCategory);

    @DeleteMapping(value = APP_ROOT + "/products/delete/{idProduct}")
    @Operation(summary = "Delete a product", description = "This method allows you to delete a product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The product was deleted")
    })
    void delete(@PathVariable("idProduct") Integer id);


}