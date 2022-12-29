package com.bjr.fullstackTutorial.controller;

import com.bjr.fullstackTutorial.exception.UserNotFoundException;
import com.bjr.fullstackTutorial.model.User;
import com.bjr.fullstackTutorial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // PostMapping is used to write data to the database
    @PostMapping("/user")
    // Give it an endpoint of your choosing
    User newUser(@RequestBody User newUser){
        // Using the User model, we throw on top of it the SpringBoot @RequestBody
        // which is, from my understanding, a http-response which is parsed to the newUser.
        return userRepository.save(newUser);
    }

    @GetMapping("/users")
    List<User> getAllUsers(){
        // The findAll()  method is provided by the JPA class that our interface extends.
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id){
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
    }

    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id){
        return userRepository.findById(id).map(user -> {
            user.setUsername(newUser.getUsername());
            user.setName(newUser.getName());
            user.setEmail(newUser.getEmail());
            return userRepository.save(user);
        }).orElseThrow(()->new UserNotFoundException(id));
    }

    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id) {
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return "User with id " + id + " was deleted successfully!";
    }
}
