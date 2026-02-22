package com.airtribe.ShareMyRecipe.service;

import com.airtribe.ShareMyRecipe.entity.AbstractUserBase;
import com.airtribe.ShareMyRecipe.repository.AbstractUserBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AbstractUserBaseRepository _abstractUserBaseRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AbstractUserBase> user = _abstractUserBaseRepo.findByEmail(username);
        if(!user.isPresent()){
            throw new UsernameNotFoundException("User not found with username" + username);
        }
        return User.withUsername(user.get().getEmail())
                .password(user.get().getPassword())
                .roles(user.get().getRole().getRoleName())
                .disabled(!user.get().isEnabled())
                .build();
    }
}
