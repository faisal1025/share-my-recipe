package com.airtribe.ShareMyRecipe.controller.v1;


import com.airtribe.ShareMyRecipe.dto.user.request.UserLoginDto;
import com.airtribe.ShareMyRecipe.dto.user.request.UserRegistrationDto;
import com.airtribe.ShareMyRecipe.dto.user.response.UserDto;
import com.airtribe.ShareMyRecipe.entity.AbstractUserBase;
import com.airtribe.ShareMyRecipe.entity.User;
import com.airtribe.ShareMyRecipe.exception.UserNotFoundException;
import com.airtribe.ShareMyRecipe.exception.user.UserAlreadyExistsException;
import com.airtribe.ShareMyRecipe.exception.verificationtoken.TokenExpiredException;
import com.airtribe.ShareMyRecipe.exception.verificationtoken.TokenNotFoundException;
import com.airtribe.ShareMyRecipe.service.UserManagementService;
import com.airtribe.ShareMyRecipe.service.VerificationTokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserV1Controller {

    @Autowired
    private UserManagementService _userManagementService;

    @Autowired
    private VerificationTokenService _verificationTokenService;

    @PostMapping("/auth/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto)
                throws UserAlreadyExistsException, UserNotFoundException {

        UserDto dto = _userManagementService.register(userRegistrationDto);
        _verificationTokenService.verifyUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping("/auth/verify-token")
    public ResponseEntity<UserDto> verifyToken(@RequestParam("token") String token)
            throws UserNotFoundException, TokenExpiredException, TokenNotFoundException {
        User user = (User)_verificationTokenService.validateToken(token);
        return ResponseEntity.ok(new UserDto.UserBuilder()
                .setUserId(user.getUserId())
                .setEmail(user.getEmail())
                .setFullName(user.getFullName())
                .setIsEnabled(user.isEnabled())
                .setRole(user.getRole())
                .setCreatedAt(user.getCreatedAt())
                .setUpdatedAt(user.getUpdatedAt())
                .build());
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody UserLoginDto userLoginDto)
            throws UserNotFoundException, RuntimeException{
        String token = _userManagementService.login(userLoginDto);
        return ResponseEntity.ok(token);
    }
}
