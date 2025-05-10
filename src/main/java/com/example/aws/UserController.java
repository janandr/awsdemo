package com.example.aws;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final List<User> userList = new ArrayList<>();
    private Long nextId = 1L;

    @GetMapping("/")
    public String hello() {
        return "Hello from Spring Boot in Docker!";
    }
    // Get all users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userList;
    }

    // Get a single user by ID
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userList.stream().filter(u -> u.getId().equals(id)).findFirst();
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Create a new user
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setId(nextId++);
        userList.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    // Update an existing user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        for (int i = 0; i < userList.size(); i++) {
            User existingUser = userList.get(i);
            if (existingUser.getId().equals(id)) {
                existingUser.setName(user.getName());
                existingUser.setEmail(user.getEmail());
                return ResponseEntity.ok(existingUser);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // Delete a user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId().equals(id)) {
                userList.remove(i);
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.notFound().build();
    }
}