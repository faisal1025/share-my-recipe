package com.airtribe.ShareMyRecipe.service;

import com.airtribe.ShareMyRecipe.dto.user.request.UserLoginDto;
import com.airtribe.ShareMyRecipe.dto.user.request.UserRegistrationDto;
import com.airtribe.ShareMyRecipe.dto.user.response.UserDto;
import com.airtribe.ShareMyRecipe.entity.User;
import com.airtribe.ShareMyRecipe.exception.UserNotFoundException;
import com.airtribe.ShareMyRecipe.exception.user.UserAlreadyExistsException;
import com.airtribe.ShareMyRecipe.repository.UserRepository;
import com.airtribe.ShareMyRecipe.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserManagementService {

    @Autowired
    private UserRepository _userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager _authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public UserDto register(UserRegistrationDto userRegistrationDto)
            throws UserAlreadyExistsException {
        if(_userRepository.existsByEmail(userRegistrationDto.getEmail())){
            throw new UserAlreadyExistsException("User with this email exists: "+ userRegistrationDto.getEmail());
        }
        User user = new User(userRegistrationDto.getFullName(),
                userRegistrationDto.getEmail(),
                passwordEncoder.encode(userRegistrationDto.getPassword()));

        User savedUser = _userRepository.save(user);

        UserDto userDto = new UserDto.UserBuilder()
                .setFullName(savedUser.getFullName())
                .setUserId(savedUser.getUserId())
                .setEmail(savedUser.getEmail())
                .setRole(savedUser.getRole())
                .setIsEnabled(savedUser.isEnabled())
                .build();
        userDto.setCreatedAt(savedUser.getCreatedAt());
        userDto.setUpdatedAt(savedUser.getUpdatedAt());
        return userDto;
    }

    public String login(UserLoginDto userLoginDto) throws UserNotFoundException{
        if(!_userRepository.existsByEmail(userLoginDto.getEmail())){
            throw new UserNotFoundException("User not found with given email "+userLoginDto.getEmail());
        }

        try{
            Authentication authentication = _authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLoginDto.getEmail(), userLoginDto.getPassword()));
            if(!authentication.isAuthenticated()){
                throw new AuthenticationException("Authentication Failed"){};
            }
            org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            return jwtTokenUtil.generateToken(userDetails);
        }catch (RuntimeException ex){
            throw new AuthenticationException("Authentication Failed"){};
        }

    }

}
