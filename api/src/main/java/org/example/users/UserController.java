package org.example.users;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.users.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.example.config.SecurityConfiguration.SECURITY_CONFIG_NAME;

@RestController
@SecurityRequirement(name = SECURITY_CONFIG_NAME)
@RequestMapping(value = "/api/v1/users/")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "all/")
    public ResponseEntity<Object> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(users);
    }
}
