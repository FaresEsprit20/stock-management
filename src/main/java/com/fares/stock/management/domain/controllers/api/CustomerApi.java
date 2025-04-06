package com.fares.stock.management.domain.controllers.api;

import com.fares.stock.management.domain.dto.customer.CustomerDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.fares.stock.management.core.constants.Constants.APP_ROOT;

@Tag(name = "Customers", description = "API for customer management")
public interface CustomerApi {

    @PostMapping(value = APP_ROOT + "/customers/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create or update a customer", description = "Saves or updates a customer in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer successfully saved/updated"),
            @ApiResponse(responseCode = "400", description = "Invalid customer data provided")
    })
    CustomerDto save(@RequestBody CustomerDto dto);

    @GetMapping(value = APP_ROOT + "/customers/{idCustomer}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find customer by ID", description = "Retrieves a customer by their unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found"),
            @ApiResponse(responseCode = "404", description = "Customer not found with the provided ID")
    })
    CustomerDto findById(@PathVariable("idCustomer") Integer id);

    @GetMapping(value = APP_ROOT + "/customers/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all customers", description = "Retrieves a list of all customers in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of customers retrieved successfully")
    })
    List<CustomerDto> findAll();

    @DeleteMapping(value = APP_ROOT + "/customers/delete/{idCustomer}")
    @Operation(summary = "Delete a customer", description = "Removes a customer from the system by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Customer not found with the provided ID")
    })
    void delete(@PathVariable("idCustomer") Integer id);


}