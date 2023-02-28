package org.example.users;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.branches.BranchRepository;
import org.example.entities.Branch;
import org.example.users.dto.UserDTO;
import org.example.users.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.example.config.SecurityConfiguration.SECURITY_CONFIG_NAME;

@RestController
@SecurityRequirement(name = SECURITY_CONFIG_NAME)
@RequestMapping(value = "/api/v1/users")
public class UserController {
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;

    public UserController(UserRepository userRepository, BranchRepository branchRepository) {
        this.userRepository = userRepository;
        this.branchRepository = branchRepository;
    }

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

    @PostMapping(value = "/profile-pics/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> uploadImage(@RequestParam("file") MultipartFile file, Authentication authentication) throws IOException, URISyntaxException {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow();

        FileUploadService fileUploadService = new FileUploadService();

        String fileName = file.getOriginalFilename();
        fileName = UUID.randomUUID().toString().concat(fileUploadService.getExtension(fileName));

        File f = fileUploadService.convertToFile(file, file.getOriginalFilename());
        String profilePicLink = fileUploadService.uploadFile(f, fileName);

        user.setProfilePicLink(profilePicLink);
        User response = userRepository.save(user);
        f.delete();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
