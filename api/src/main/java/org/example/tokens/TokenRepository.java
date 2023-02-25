package org.example.tokens;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {
    @Query(value = """
            SELECT t FROM Token t INNER JOIN User u\s
            ON t.user.userId = u.userId\s
            WHERE u.userId = :id AND (t.expired = false OR t.revoked = false)\s
            """)
    List<Token> findAllValidTokenByUser(UUID id);

    Optional<Token> findByToken(String token);
}
