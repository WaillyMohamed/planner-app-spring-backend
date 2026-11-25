package com.boostcode.plannerappspringbackend.service;

import com.boostcode.plannerappspringbackend.dto.request.UserRequestDTO;
import com.boostcode.plannerappspringbackend.dto.response.UserResponseDTO;
import com.boostcode.plannerappspringbackend.entity.User;
import com.boostcode.plannerappspringbackend.exception.UserNotFoundException;
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

    public UserResponseDTO createUser(UserRequestDTO dto) {
        User user = getUserEntity(dto);
        User savedUser = userRepository.save(user);
        return getUserResponseDTO(savedUser);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO userDTO) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setUsername(userDTO.username());
        user.setPassword(userDTO.password());
        user.setEmail(userDTO.email());

        User updatedUser = userRepository.save(user);

        return getUserResponseDTO(updatedUser);
    }

    public User getUserEntityById(long id) {
        Optional<User> person = userRepository.findById(id); // Find by id

        // Check if the user was actually received
        User user = null;
        if (person.isPresent()) {
            user = person.get();
            return user;
        }
        return user;
    }

    public UserResponseDTO getUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found - ID: " + id));


        return getUserResponseDTO(user);

    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    private UserResponseDTO getUserResponseDTO(User user){
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());
    }

    private User getUserEntity(UserRequestDTO userRequestDTO){
        User user = new User();
        user.setUsername(userRequestDTO.username());
        user.setEmail(userRequestDTO.email());
        user.setPassword(userRequestDTO.password());
        return user;

    }

}
