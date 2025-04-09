package com.fares.stock.management.domain.controllers.api;

import com.fares.stock.management.domain.dto.auth.ChangePasswordUserDto;
import com.fares.stock.management.domain.dto.user.UserDto;
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

import static com.fares.stock.management.core.utils.constants.Constants.USER_ENDPOINT;


@Tag(name = "Users", description = "API for user management")
public interface UserApi {

    @PostMapping(USER_ENDPOINT + "/create")
    @Operation(summary = "Create or update user",
            description = "Creates a new user or updates an existing one")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully saved/updated"),
            @ApiResponse(responseCode = "400", description = "Invalid user data provided")
    })
    UserDto save(@RequestBody UserDto dto);

    @PostMapping(USER_ENDPOINT + "/update/password")
    @Operation(summary = "Change user password",
            description = "Updates the password for an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password successfully changed"),
            @ApiResponse(responseCode = "400", description = "Invalid password data provided"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    UserDto changePassword(@RequestBody ChangePasswordUserDto dto);

    @GetMapping(USER_ENDPOINT + "/{userId}")
    @Operation(summary = "Find user by ID",
            description = "Retrieves user details by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found with the provided ID")
    })
    UserDto findById(
            @Parameter(description = "ID of the user to be retrieved")
            @PathVariable("userId") Integer id);

    @GetMapping(USER_ENDPOINT + "/find/{email}")
    @Operation(summary = "Find user by email",
            description = "Retrieves user details by email address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found with the provided email")
    })
    UserDto findByEmail(
            @Parameter(description = "Email address of the user to be retrieved")
            @PathVariable("email") String email);

    @GetMapping(USER_ENDPOINT + "/all")
    @Operation(summary = "Get all users",
            description = "Retrieves a list of all registered users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully")
    })
    List<UserDto> findAll();

    @DeleteMapping(USER_ENDPOINT + "/delete/{userId}")
    @Operation(summary = "Delete user",
            description = "Permanently deletes a user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully deleted"),
            @ApiResponse(responseCode = "404", description = "User not found with the provided ID")
    })
    void delete(
            @Parameter(description = "ID of the user to be deleted")
            @PathVariable("userId") Integer id);

}
