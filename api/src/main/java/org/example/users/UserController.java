package org.example.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/users/all/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllUsers() {
        Iterable<UserModel> users = userRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}
