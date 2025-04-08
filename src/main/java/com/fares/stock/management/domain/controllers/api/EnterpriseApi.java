package com.fares.stock.management.domain.controllers.api;

import com.fares.stock.management.domain.dto.enterprise.EnterpriseDto;
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
import static com.fares.stock.management.core.utils.constants.constants.Constants.ENTERPRISE_ENDPOINT;


@Tag(name = "Enterprises", description = "API for enterprise management")
public interface EnterpriseApi {

    @PostMapping(ENTERPRISE_ENDPOINT + "/create")
    @Operation(summary = "Create or update an enterprise",
            description = "Saves or updates an enterprise in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Enterprise successfully saved/updated"),
            @ApiResponse(responseCode = "400", description = "Invalid enterprise data provided")
    })
    EnterpriseDto save(@RequestBody EnterpriseDto dto);

    @GetMapping(ENTERPRISE_ENDPOINT + "/{idEnterprise}")
    @Operation(summary = "Find enterprise by ID",
            description = "Retrieves an enterprise by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Enterprise found"),
            @ApiResponse(responseCode = "404", description = "Enterprise not found with the provided ID")
    })
    EnterpriseDto findById(@PathVariable("idEnterprise") Integer id);

    @GetMapping(ENTERPRISE_ENDPOINT + "/all")
    @Operation(summary = "Get all enterprises",
            description = "Retrieves a list of all enterprises in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of enterprises retrieved successfully")
    })
    List<EnterpriseDto> findAll();

    @DeleteMapping(ENTERPRISE_ENDPOINT + "/delete/{idEnterprise}")
    @Operation(summary = "Delete an enterprise",
            description = "Removes an enterprise from the system by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Enterprise successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Enterprise not found with the provided ID")
    })
    void delete(@PathVariable("idEnterprise") Integer id);

}