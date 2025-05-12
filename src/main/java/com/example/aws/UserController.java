package com.example.aws;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
// add the following imports to your project
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.nio.file.Paths;
import java.net.InetSocketAddress;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.api.core.config.ProgrammaticDriverConfigLoaderBuilder;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import java.time.Duration;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {

    private  List<User> userList = new ArrayList<>();
    private Long nextId = 1L;

    // Get all users
    @GetMapping
    public String hello() {
        System.out.println("hello called");
        return "Hello";
    }
    // Get all users
    @GetMapping("/hello")
    public String helloWorld() {
         System.out.println("hello world called");
        return "Hello World";
    }

    // Get all users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        System.out.println("getAllUsers called");
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
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setId(nextId++);
        userList.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    // Update an existing user
    @PutMapping("/users/{id}")
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
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId().equals(id)) {
                userList.remove(i);
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    public static CqlSession createSession() {
        AwsCredentialsProvider credentialsProvider = DefaultCredentialsProvider.create(); // AWS SDK handles credentials

        return CqlSession.builder()
                .addContactPoint(new InetSocketAddress("cassandra.eu-north-1.amazonaws.com", 9142))
                .withAuthCredentials("janandraks-at-250740063307", "Zh+8oMMH+yPhZ0HFOOiDxxfWMWT2Pic8JVqlrzmxvVTebRiFttj+mbG2GXA=") // If using Cassandra authentication
                .build();
    }

    @GetMapping("/aws/ks/username")
    public String getAwsKSUserName() {
        //Use DriverConfigLoader to load your configuration file
        DriverConfigLoader loader = DriverConfigLoader.fromClasspath("application.conf");
        try (CqlSession session = CqlSession.builder()
                .withConfigLoader(loader)
                .build()) {

            ResultSet rs = session.execute("select * from my_keyspace.users");
            Row row = rs.one();
            System.out.println(row);
            System.out.println(row.getString("username"));
            return row.getString("username");
        }
    }
}