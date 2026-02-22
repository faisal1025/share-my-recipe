package com.airtribe.ShareMyRecipe.repository;

import java.util.Optional;
import com.airtribe.ShareMyRecipe.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
}
