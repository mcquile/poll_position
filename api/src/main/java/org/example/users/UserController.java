package org.example.users;

import org.example.users.dto.UserCreationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/all/")
    public ResponseEntity<Object> getAllUsers() {
        Iterable<UserModel> users = userRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PostMapping(value = "/auth/register/")
    public ResponseEntity<Object> createNewUser(@RequestBody UserCreationDTO userModel) {
        System.out.println(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("yes");
    }
}
