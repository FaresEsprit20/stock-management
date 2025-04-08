package com.fares.stock.management.domain.controllers.api;

import com.fares.stock.management.domain.dto.category.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fares.stock.management.core.utils.constants.constants.Constants.APP_ROOT;

@Tag(name = "Categories", description = "API for category operations")
public interface CategoryApi {

    @PostMapping(value = APP_ROOT + "/categories/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Save a category", description = "This method allows you to save or update a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The created/updated category object"),
            @ApiResponse(responseCode = "400", description = "The category object is not valid")
    })
    CategoryDto save(@RequestBody CategoryDto dto);

    @GetMapping(value = APP_ROOT + "/categories/{idCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find a category by ID", description = "This method allows you to search for a category by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The category was found in the database"),
            @ApiResponse(responseCode = "404", description = "No category exists in the database with the provided ID")
    })
    CategoryDto findById(@PathVariable("idCategory") Integer idCategory);

    @GetMapping(value = APP_ROOT + "/categories/filter/{codeCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find a category by CODE", description = "This method allows you to search for a category by its CODE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The category was found in the database"),
            @ApiResponse(responseCode = "404", description = "No category exists in the database with the provided CODE")
    })
    CategoryDto findByCode(@PathVariable("codeCategory") String codeCategory);

    @GetMapping(value = APP_ROOT + "/categories/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all categories", description = "This method returns the list of all categories in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of categories (may be empty)")
    })
    List<CategoryDto> findAll();

    @DeleteMapping(value = APP_ROOT + "/categories/delete/{idCategory}")
    @Operation(summary = "Delete a category", description = "This method allows you to delete a category by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The category was successfully deleted")
    })
    void delete(@PathVariable("idCategory") Integer id);
}