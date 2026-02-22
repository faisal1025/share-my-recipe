package com.airtribe.ShareMyRecipe.service;

import com.airtribe.ShareMyRecipe.dto.admin.request.AdminLoginDto;
import com.airtribe.ShareMyRecipe.dto.admin.request.AdminRegisterDto;
import com.airtribe.ShareMyRecipe.dto.admin.response.AdminDto;
import com.airtribe.ShareMyRecipe.entity.Admin;
import com.airtribe.ShareMyRecipe.exception.UserNotFoundException;
import com.airtribe.ShareMyRecipe.exception.admin.AdminAlreadyExistsException;
import com.airtribe.ShareMyRecipe.repository.AdminRepository;
import com.airtribe.ShareMyRecipe.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminManagementService {

    @Autowired
    private AdminRepository _adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager _authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public AdminDto register(AdminRegisterDto adminRegisterDto) throws AdminAlreadyExistsException{
        if(_adminRepository.existsByEmail(adminRegisterDto.getEmail())){
            throw new AdminAlreadyExistsException("admin with email already exists: "+adminRegisterDto.getEmail());
        }

        Admin admin = new Admin(adminRegisterDto.getFullName(),
                adminRegisterDto.getEmail(),
                passwordEncoder.encode(adminRegisterDto.getPassword()));

        Admin savedAdmin = _adminRepository.save(admin);
        return new AdminDto(
                savedAdmin.getUserId(),
                savedAdmin.getEmail(),
                savedAdmin.isEnabled(),
                savedAdmin.getRole(),
                savedAdmin.getCreatedAt(),
                savedAdmin.getUpdatedAt(),
                savedAdmin.getFullName()
        );
    }

    public String login(AdminLoginDto adminLoginDto) throws  UserNotFoundException{
        if(!_adminRepository.existsByEmail(adminLoginDto.getEmail())){
            throw new UserNotFoundException("Admin user not found with this email: "+adminLoginDto.getEmail());
        }

        Authentication authentication = _authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                adminLoginDto.getEmail(), adminLoginDto.getPassword()
        ));

        if(!authentication.isAuthenticated()){
            throw new AuthenticationException("Authentication Failed"){};
        }

        UserDetails user = (User) authentication.getPrincipal();
        return jwtTokenUtil.generateToken(user);
    }
}
