package com.boostcode.plannerappspringbackend.service;

import com.boostcode.plannerappspringbackend.dto.request.UserRequestDTO;
import com.boostcode.plannerappspringbackend.dto.response.UserResponseDTO;
import com.boostcode.plannerappspringbackend.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void saveUserServiceTest() {
        // Create User DTO
        UserRequestDTO createRequest =
                new UserRequestDTO(
                        "username",
                        "password",
                        "email"
                );

        // Create User
        UserResponseDTO createdUser =
                userService.createUser(createRequest);

        // Verify User Saved
        assertNotNull(createdUser.id());

        // Verify Data
        assertEquals(
                createRequest.username(),
                createdUser.username()
        );

        assertEquals(
                createRequest.email(),
                createdUser.email()
        );

        // Update DTO
        UserRequestDTO updateRequest =
                new UserRequestDTO(
                        "nickname",
                        "patchword",
                        "gmail"
                );

        // Update User
        UserResponseDTO updatedUser =
                userService.updateUser(
                        createdUser.id(),
                        updateRequest
                );

        // Verify User Updated
        assertNotNull(updatedUser.id());

        assertEquals(
                updateRequest.username(),
                updatedUser.username()
        );

        assertEquals(
                updateRequest.email(),
                updatedUser.email()
        );
    }






}
