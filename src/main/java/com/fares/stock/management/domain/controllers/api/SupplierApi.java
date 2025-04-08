package com.fares.stock.management.domain.controllers.api;

import com.fares.stock.management.domain.dto.supplier.SupplierDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.fares.stock.management.core.utils.constants.constants.Constants.SUPPLIER_ENDPOINT;


@Tag(name = "Suppliers", description = "API for supplier management")
public interface SupplierApi {

    @PostMapping(SUPPLIER_ENDPOINT + "/create")
    @Operation(summary = "Create or update a supplier",
            description = "Saves or updates a supplier in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supplier successfully saved/updated"),
            @ApiResponse(responseCode = "400", description = "Invalid supplier data provided")
    })
    SupplierDto save(@RequestBody SupplierDto dto);

    @GetMapping(SUPPLIER_ENDPOINT + "/{idSupplier}")
    @Operation(summary = "Find supplier by ID",
            description = "Retrieves a supplier by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supplier found"),
            @ApiResponse(responseCode = "404", description = "Supplier not found with the provided ID")
    })
    SupplierDto findById(@PathVariable("idSupplier") Integer id);

    @GetMapping(SUPPLIER_ENDPOINT + "/all")
    @Operation(summary = "Get all suppliers",
            description = "Retrieves a list of all suppliers in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of suppliers retrieved successfully")
    })
    List<SupplierDto> findAll();

    @DeleteMapping(SUPPLIER_ENDPOINT + "/delete/{idSupplier}")
    @Operation(summary = "Delete a supplier",
            description = "Removes a supplier from the system by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supplier successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Supplier not found with the provided ID")
    })
    void delete(@PathVariable("idSupplier") Integer id);


}
