package com.airtribe.ShareMyRecipe.controller.v1;

import com.airtribe.ShareMyRecipe.dto.admin.request.AdminLoginDto;
import com.airtribe.ShareMyRecipe.dto.admin.request.AdminRegisterDto;
import com.airtribe.ShareMyRecipe.dto.admin.response.AdminDto;
import com.airtribe.ShareMyRecipe.entity.Admin;
import com.airtribe.ShareMyRecipe.exception.UserNotFoundException;
import com.airtribe.ShareMyRecipe.exception.admin.AdminAlreadyExistsException;
import com.airtribe.ShareMyRecipe.exception.verificationtoken.TokenExpiredException;
import com.airtribe.ShareMyRecipe.exception.verificationtoken.TokenNotFoundException;
import com.airtribe.ShareMyRecipe.service.AdminManagementService;
import com.airtribe.ShareMyRecipe.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminV1Controller {
    @Autowired
    private AdminManagementService _adminManagementService;

    @Autowired
    private VerificationTokenService _verificationTokenService;

    @PostMapping("/auth/register")
    public ResponseEntity<AdminDto> register(@RequestBody AdminRegisterDto adminRegisterDto)
            throws UserNotFoundException, AdminAlreadyExistsException {

        AdminDto adminDto = _adminManagementService.register(adminRegisterDto);
        _verificationTokenService.verifyUser(adminDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(adminDto);
    }

    @PostMapping("/auth/verify-token")
    public ResponseEntity<AdminDto> verifyToken(@RequestParam String token) throws TokenNotFoundException,
            TokenExpiredException, UserNotFoundException {

        Admin admin = (Admin)_verificationTokenService.validateToken(token);
        return ResponseEntity.ok(new AdminDto(
                admin.getUserId(),
                admin.getEmail(),
                admin.isEnabled(),
                admin.getRole(),
                admin.getCreatedAt(),
                admin.getUpdatedAt(),
                admin.getFullName()
        ));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> loginAdmin(@RequestBody AdminLoginDto adminLoginDto) throws
            UserNotFoundException {
        String jwtToken = _adminManagementService.login(adminLoginDto);
        return ResponseEntity.ok(jwtToken);
    }
}
