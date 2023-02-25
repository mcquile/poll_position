package org.example.users;

import org.example.users.dto.UserCreationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/auth/")
public class UserAuthController {
    @PostMapping(value = "/register/")
    public ResponseEntity<Object> createNewUser(@RequestBody UserCreationDTO userModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body("yes");
    }
}
