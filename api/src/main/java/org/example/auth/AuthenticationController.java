package org.example.auth;

import lombok.RequiredArgsConstructor;
import org.example.auth.dto.LoginRequestDTO;
import org.example.auth.dto.RegisterRequestDTO;
import org.example.exceptions.InvalidLoginCredentialsException;
import org.example.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:4200/")
public class AuthenticationController {
    private final AuthenticationService service;


    @PostMapping("/register/")
    public ResponseEntity<Object> register(@RequestBody RegisterRequestDTO request) {
        try {
            return ResponseEntity.ok(service.register(request));
        } catch (UserAlreadyExistsException userAlreadyExistsException) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "This email address already has a user associated with it.");

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(response);
        }
    }

    @PostMapping("/login/")
    public ResponseEntity<Object> authenticate(@RequestBody LoginRequestDTO request) {
        try {
            return ResponseEntity.ok(service.authenticate(request));
        } catch (InvalidLoginCredentialsException invalidLoginCredentialsException) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Invalid login credentials");
        }
    }
}
