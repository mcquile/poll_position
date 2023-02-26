package org.example.users.auth;

import lombok.RequiredArgsConstructor;
import org.example.exceptions.InvalidLoginCredentialsException;
import org.example.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register/")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.ok(service.register(request));
        } catch (UserAlreadyExistsException userAlreadyExistsException) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("This email address already has a user associated with it.");
        }
    }

    @PostMapping("/authenticate/")
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            return ResponseEntity.ok(service.authenticate(request));
        } catch (InvalidLoginCredentialsException invalidLoginCredentialsException) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Invalid login credentials");
        }
    }
}
