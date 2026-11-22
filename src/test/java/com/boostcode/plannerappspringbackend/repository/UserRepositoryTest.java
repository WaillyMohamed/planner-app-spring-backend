package com.boostcode.plannerappspringbackend.repository;
import com.boostcode.plannerappspringbackend.entity.User;
import com.boostcode.plannerappspringbackend.entity.UserAvailability;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/*
    This class will be used to test the User Repository Class. It tests the following:
    - Save a user
    - Retrieve a User by ID
    - Verifying the username and password (Might remove later)
 */

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE) // This annotations tells spring boot to use my postgreSQL datasource instead of making an in-memory database.
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUserTest(){

        // Create a new user
        User user = new User();

        // Set username and password
        user.setUsername("SuperTest1");
        user.setPassword("RidgewayPlaza");
        user.setEmail("Yippee@yahoo.ca");

        /*
        // Create user availability
        UserAvailability userAvailability = new UserAvailability();
        userAvailability.setStartTime(LocalTime.of(7, 45));
        userAvailability.setEndTime(LocalTime.of(23, 45));
        user.setAvailability(userAvailability);
        */

        // Save the user
        User saved = userRepository.save(user);

        // Test if the ID was generated
        assertNotNull(saved.getId());

        // Retrieve the user
        Optional<User> confirmUser = userRepository.findById(saved.getId());

        // Test if the same user was saved
        assertTrue(confirmUser.isPresent());
        assertEquals(saved.getUsername(), confirmUser.get().getUsername());
        assertEquals(saved.getPassword(), confirmUser.get().getPassword());
        assertEquals(saved.getEmail(), confirmUser.get().getEmail());

    }
}
