package com.boostcode.plannerappspringbackend.service;

import com.boostcode.plannerappspringbackend.entity.User;
import com.boostcode.plannerappspringbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
*   This Service Class will allow users to Create, Retrieve, Update, and Delete users using...
*   The User Repository class!
*/

@Service
public class UserService {

    // Create the user repository
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void deleteUserById(long id){
        userRepository.deleteById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
    /*
    public User updateUser(User user) {

    }
    */


    public User getUserById(long id) {
        Optional<User> person = userRepository.findById(id); // Find by id

        // Check if the user was actually received
        User user = null;
        if (person.isPresent()) {
            user = person.get();
            return user;
        }
        return user;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }


}
