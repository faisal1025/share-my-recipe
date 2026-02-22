package com.airtribe.ShareMyRecipe.repository;

import com.airtribe.ShareMyRecipe.entity.AbstractUserBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AbstractUserBaseRepository extends JpaRepository<AbstractUserBase, Long> {

    Optional<AbstractUserBase> findByEmail(String email);

    Optional<AbstractUserBase> findByUserId(Long userId);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM AbstractUserBase u WHERE u.userId = :userId AND u.isEnabled = true")
    Optional<AbstractUserBase> findByIdAndEnabled(@Param("userId") Long userId);
}