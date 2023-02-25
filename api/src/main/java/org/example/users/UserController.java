package org.example.users;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.StreamSupport;

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
        List<UserResponse> users = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(user -> {
                    UserResponse ur = new UserResponse();
                    ur.setEmailAddress(user.getEmail());
                    ur.setFirstName(user.getFirstName());
                    ur.setLastName(user.getLastName());
                    return ur;
                }).toList();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}
