package com.fares.stock.management.domain.controllers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import static com.fares.stock.management.core.constants.Constants.APP_ROOT;

@Tag(name = "Photos", description = "API for photo upload and management")
public interface PhotoApi {

    @PostMapping(APP_ROOT + "/photos/save/{id}/{title}/{context}")
    @Operation(summary = "Upload a photo",
            description = "Saves a photo with metadata to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Photo successfully uploaded"),
            @ApiResponse(responseCode = "400", description = "Invalid photo data or metadata"),
            @ApiResponse(responseCode = "500", description = "Photo processing error")
    })
    Object savePhoto(
            @Parameter(description = "Context where the photo will be used")
            @PathVariable("context") String context,

            @Parameter(description = "ID associated with the photo")
            @PathVariable("id") Integer id,

            @Parameter(description = "Photo file to upload")
            @RequestPart("file") MultipartFile photo,

            @Parameter(description = "Title for the photo")
            @PathVariable("title") String title
    ) throws IOException;


}