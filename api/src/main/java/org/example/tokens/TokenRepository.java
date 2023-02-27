package org.example.tokens;

import org.example.users.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {
    List<Token> findTokensByUserAndExpiredAndRevoked(User user, boolean expired, boolean revoked);

    Optional<Token> findByToken(String token);
}
