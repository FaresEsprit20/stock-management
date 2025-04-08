package com.fares.stock.management.domain.controllers.api;

import com.fares.stock.management.domain.dto.stock_movement.StockMovementDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.fares.stock.management.core.utils.constants.constants.Constants.APP_ROOT;

@Tag(name = "Stock Movements", description = "API for inventory stock movement management")
public interface StockMovementApi {

    @GetMapping(APP_ROOT + "/stockmovements/realstock/{articleId}")
    @Operation(summary = "Get real stock quantity",
            description = "Retrieves the current real stock quantity for an article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Real stock quantity retrieved"),
            @ApiResponse(responseCode = "404", description = "Article not found")
    })
    BigDecimal getRealStockByArticle(@PathVariable("articleId") Integer articleId);

    @GetMapping(APP_ROOT + "/stockmovements/filter/article/{articleId}")
    @Operation(summary = "Get stock movements by article",
            description = "Retrieves all stock movements for a specific article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of stock movements retrieved"),
            @ApiResponse(responseCode = "404", description = "Article not found")
    })
    List<StockMovementDto> getStockMovementsByArticle(@PathVariable("articleId") Integer articleId);

    @PostMapping(APP_ROOT + "/stockmovements/entry")
    @Operation(summary = "Record stock entry",
            description = "Records an inventory stock entry movement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock entry recorded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid stock movement data")
    })
    StockMovementDto recordStockEntry(@RequestBody StockMovementDto dto);

    @PostMapping(APP_ROOT + "/stockmovements/exit")
    @Operation(summary = "Record stock exit",
            description = "Records an inventory stock exit movement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock exit recorded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid stock movement data")
    })
    StockMovementDto recordStockExit(@RequestBody StockMovementDto dto);

    @PostMapping(APP_ROOT + "/stockmovements/positiveadjustment")
    @Operation(summary = "Record positive stock adjustment",
            description = "Records a positive inventory stock adjustment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Positive adjustment recorded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid stock movement data")
    })
    StockMovementDto recordPositiveStockAdjustment(@RequestBody StockMovementDto dto);

    @PostMapping(APP_ROOT + "/stockmovements/negativeadjustment")
    @Operation(summary = "Record negative stock adjustment",
            description = "Records a negative inventory stock adjustment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Negative adjustment recorded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid stock movement data")
    })
    StockMovementDto recordNegativeStockAdjustment(@RequestBody StockMovementDto dto);

}
