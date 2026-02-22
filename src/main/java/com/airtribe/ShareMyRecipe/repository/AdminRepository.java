package com.airtribe.ShareMyRecipe.repository;

import com.airtribe.ShareMyRecipe.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    public boolean existsByEmail(String email);
}
