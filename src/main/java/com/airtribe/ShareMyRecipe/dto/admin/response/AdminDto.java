package com.airtribe.ShareMyRecipe.dto.admin.response;

import com.airtribe.ShareMyRecipe.dto.UserBaseResponseDto;
import com.airtribe.ShareMyRecipe.entity.Role;

import java.time.LocalDateTime;

public class AdminDto extends UserBaseResponseDto {
    private String fullName;

    public AdminDto() {
    }

    public AdminDto(Long userId, String email, boolean isEnabled, Role role, String fullName) {
        super(userId, email, isEnabled, role);
        this.fullName = fullName;
    }

    public AdminDto(Long userId, String email, boolean isEnabled, Role role, LocalDateTime createdAt, LocalDateTime updatedAt, String fullName) {
        super(userId, email, isEnabled, role, createdAt, updatedAt);
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
