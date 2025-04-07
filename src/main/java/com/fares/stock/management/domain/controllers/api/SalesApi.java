package com.fares.stock.management.domain.controllers.api;

import com.fares.stock.management.domain.dto.sales.SalesDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import static com.fares.stock.management.core.constants.Constants.SALES_ENDPOINT;

@Tag(name = "Sales", description = "API for sales management")
public interface SalesApi {

    @PostMapping(SALES_ENDPOINT + "/create")
    @Operation(summary = "Create a sale record",
            description = "Creates a new sales transaction in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sale successfully recorded"),
            @ApiResponse(responseCode = "400", description = "Invalid sale data provided")
    })
    SalesDto createSale(@RequestBody SalesDto dto);

    @GetMapping(SALES_ENDPOINT + "/{saleId}")
    @Operation(summary = "Get sale by ID",
            description = "Retrieves sale details by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sale found"),
            @ApiResponse(responseCode = "404", description = "Sale not found with the provided ID")
    })
    SalesDto getSaleById(
            @Parameter(description = "ID of the sale to retrieve")
            @PathVariable("saleId") Integer id);

    @GetMapping(SALES_ENDPOINT + "/by-code/{saleCode}")
    @Operation(summary = "Get sale by code",
            description = "Retrieves sale details by its unique transaction code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sale found"),
            @ApiResponse(responseCode = "404", description = "Sale not found with the provided code")
    })
    SalesDto getSaleByCode(
            @Parameter(description = "Transaction code of the sale to retrieve")
            @PathVariable("saleCode") String code);

    @GetMapping(SALES_ENDPOINT + "/all")
    @Operation(summary = "Get all sales",
            description = "Retrieves a list of all sales transactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of sales retrieved successfully")
    })
    List<SalesDto> getAllSales();

    @DeleteMapping(SALES_ENDPOINT + "/delete/{saleId}")
    @Operation(summary = "Delete a sale",
            description = "Removes a sales transaction from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sale successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Sale not found with the provided ID")
    })
    void deleteSale(
            @Parameter(description = "ID of the sale to delete")
            @PathVariable("saleId") Integer id);

}
