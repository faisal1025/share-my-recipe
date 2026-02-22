package com.airtribe.ShareMyRecipe.repository;

import com.airtribe.ShareMyRecipe.entity.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChefRepository extends JpaRepository<Chef, Long> {
    @Query("SELECT c FROM Chef c WHERE c.chefName = ?1")
    List<Chef> findByName(String chefName);
    Optional<Chef> findByChefHandle(String chefHandle);
    boolean existsByChefHandle(String chefHandle);
    boolean existsByEmail(String email);
    Optional<Chef> findByEmail(String email);
}
