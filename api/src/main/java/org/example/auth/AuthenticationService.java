package org.example.auth;

import lombok.RequiredArgsConstructor;
import org.example.auth.dto.AuthenticationRequest;
import org.example.auth.dto.AuthenticationResponse;
import org.example.auth.dto.RegisterRequest;
import org.example.config.JwtService;
import org.example.exceptions.InvalidLoginCredentialsException;
import org.example.exceptions.UserAlreadyExistsException;
import org.example.tokens.Token;
import org.example.tokens.TokenRepository;
import org.example.tokens.TokenType;
import org.example.users.UserRepository;
import org.example.users.models.Role;
import org.example.users.models.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws UserAlreadyExistsException {
        Optional<User> u = userRepository.findByEmail(request.getEmail());

        if (u.isPresent()) {
            throw new UserAlreadyExistsException(request.getEmail());
        }

        User user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws InvalidLoginCredentialsException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if (user.isEmpty()) {
            throw new InvalidLoginCredentialsException();
        }

        User u = user.get();
        String jwtToken = jwtService.generateToken(u);
        revokeAllUserTokens(u);
        saveUserToken(u, jwtToken);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findTokensByUserAndExpiredAndRevoked(user, false, false);
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
