package com.boostcode.plannerappspringbackend.service;

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
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email");

        // Test creating the user
        User user1 = userService.createUser(user);
        // Verify the User is saved
        assertNotNull(user1.getId());

        // Check if the user saved everything correctly
        assertEquals(user.getUsername(), user1.getUsername());
        assertEquals(user.getPassword(), user1.getPassword());
        assertEquals(user.getEmail(), user1.getEmail());

        // Retrieving the user
        User temp = new User();
        temp.setUsername("nickname");
        temp.setPassword("patchword");
        temp.setEmail("gmail");

        User user2 = userService.updateUser(user1.getId(), temp);
        assertNotNull(user2.getId());
        assertEquals(temp.getUsername(), user2.getUsername());
        assertEquals(temp.getPassword(), user2.getPassword());
        assertEquals(temp.getEmail(), user2.getEmail());




    }

}
