package org.example.auth;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.example.auth.dto.LoginRequestDTO;
import org.example.auth.dto.LoginResponseDTO;
import org.example.auth.dto.RegisterRequestDTO;
import org.example.auth.dto.SocialAuthRequestDTO;
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

    public LoginResponseDTO register(RegisterRequestDTO request, @Nullable String profilePic) throws UserAlreadyExistsException {
        Optional<User> u = userRepository.findByEmail(request.getEmail());

        if (u.isPresent()) {
            throw new UserAlreadyExistsException(request.getEmail());
        }

        User user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .profilePicLink(profilePic)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return LoginResponseDTO.builder()
                .token(jwtToken)
                .build();
    }

    public LoginResponseDTO authenticate(LoginRequestDTO request) throws InvalidLoginCredentialsException {
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

        return LoginResponseDTO.builder()
                .token(jwtToken)
                .build();
    }

    public LoginResponseDTO oauth2Login(SocialAuthRequestDTO socialAuthRequestDTO) throws InvalidLoginCredentialsException, UserAlreadyExistsException {
        Optional<User> u = userRepository.findByEmail(socialAuthRequestDTO.getEmailAddress());

        if (u.isPresent()) {
            LoginRequestDTO requestDTO = LoginRequestDTO.builder()
                    .email(socialAuthRequestDTO.getEmailAddress())
                    .password("mIWxzaCt&0YSZqq2n^4g$ZEav#19dw2!W66HPV&s3ta2qkvjLZ")
                    .build();
            return authenticate(requestDTO);
        }

        RegisterRequestDTO registerRequestDTO = RegisterRequestDTO.builder()
                .email(socialAuthRequestDTO.getEmailAddress())
                .firstname(socialAuthRequestDTO.getFirstname())
                .lastname(socialAuthRequestDTO.getLastname())
                .password("mIWxzaCt&0YSZqq2n^4g$ZEav#19dw2!W66HPV&s3ta2qkvjLZ")
                .build();
        return register(registerRequestDTO, socialAuthRequestDTO.getProfilePic());
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
