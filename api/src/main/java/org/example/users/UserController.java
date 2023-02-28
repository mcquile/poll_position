package org.example.users;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.example.branches.BranchRepository;
import org.example.entities.Branch;
import org.example.users.dto.UserDTO;
import org.example.users.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

import static org.example.config.SecurityConfiguration.SECURITY_CONFIG_NAME;

@RestController
@SecurityRequirement(name = SECURITY_CONFIG_NAME)
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PutMapping
    public ResponseEntity<Object> editUserProfile(UserDTO userDTO, Authentication authentication) {
        String userEmail = authentication.getName();

        User user;
        try {
            user = userRepository.findByEmail(userEmail).orElseThrow();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such user");
        }

        user.setEmail(userDTO.emailAddress());
        user.setFirstName(userDTO.firstName());
        user.setLastName(userDTO.lastName());

        Branch branch;
        try {
            branch = branchRepository.findBranchByBranchNameEqualsIgnoreCase(userDTO.branch()).orElseThrow();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such branch");
        }

        user.setBranch(branch);

        User response = userRepository.save(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
