package org.example.users;

import org.example.exceptions.NoAuthorisationHeaderException;
import org.example.users.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public class UserService {

    public static User getUserFromAuthentication(Authentication authentication, UserRepository userRepository) throws NoAuthorisationHeaderException {
        String email = authentication.getName();
        try {
            return getUserFromEmail(email, userRepository);
        } catch (ResponseStatusException e) {
            throw new NoAuthorisationHeaderException();
        }
    }

    public static User getUserFromEmail(String email, UserRepository userRepository) throws ResponseStatusException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No User with email: " + email);
        }
        return user.get();
    }


}
