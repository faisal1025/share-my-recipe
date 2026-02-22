package com.airtribe.ShareMyRecipe.dto.user.response;

import com.airtribe.ShareMyRecipe.dto.UserBaseResponseDto;
import com.airtribe.ShareMyRecipe.dto.user.request.UserRegistrationDto;
import com.airtribe.ShareMyRecipe.entity.Role;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class UserDto extends UserBaseResponseDto {

    private String fullName;

    public UserDto() {}

    public UserDto(UserBuilder builder) {
        super(builder.userId, builder.email, builder.isEnabled, builder.role,
                builder.createdAt, builder.updatedAt);
        this.fullName = builder.fullName;
    }

    public static class UserBuilder{
        private Long userId;
        private String fullName;
        private String email;
        private Role role;
        private boolean isEnabled;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public UserBuilder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }
        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }
        public UserBuilder setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }
        public UserBuilder setRole(Role role){
            this.role = role;
            return this;
        }
        public UserBuilder setIsEnabled(boolean isEnabled) {
            this.isEnabled = isEnabled;
            return this;
        }
        public UserBuilder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        public UserBuilder setUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }
        public UserDto build(){
            return new UserDto(this);
        }
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
