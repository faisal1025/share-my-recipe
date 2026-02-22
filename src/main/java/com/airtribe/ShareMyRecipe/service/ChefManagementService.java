package com.airtribe.ShareMyRecipe.service;


import com.airtribe.ShareMyRecipe.dto.chef.request.ChefLoginDto;
import com.airtribe.ShareMyRecipe.dto.chef.request.ChefRegistrationDto;
import com.airtribe.ShareMyRecipe.dto.chef.response.ChefDto;
import com.airtribe.ShareMyRecipe.dto.PageResponse;
import com.airtribe.ShareMyRecipe.entity.Chef;
import com.airtribe.ShareMyRecipe.entity.Role;
import com.airtribe.ShareMyRecipe.exception.chef.ChefAlreadyExistsException;
import com.airtribe.ShareMyRecipe.exception.chef.ChefNotFoundException;
import com.airtribe.ShareMyRecipe.repository.ChefRepository;
import com.airtribe.ShareMyRecipe.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChefManagementService {

    @Autowired
    private ChefRepository _chefRepository;

    @Autowired
    private PasswordEncoder _passwordEncoder;

    @Autowired
    private  AuthenticationManager _authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public ChefDto register(ChefRegistrationDto chefRegistrationDto) throws ChefAlreadyExistsException{
        if(_chefRepository.existsByChefHandle(chefRegistrationDto.getChefHandle())){
            throw new ChefAlreadyExistsException("Chef handle already exists: " + chefRegistrationDto.getChefHandle());
        }
        if(_chefRepository.existsByEmail(chefRegistrationDto.getEmail())){
            throw new ChefAlreadyExistsException("Chef Email already exists: " + chefRegistrationDto.getEmail());
        }
        Chef chef = new Chef(
                chefRegistrationDto.getChefName(),
                chefRegistrationDto.getChefHandle(),
                chefRegistrationDto.getEmail(),
                _passwordEncoder.encode(chefRegistrationDto.getPassword()),
                false,
                new ArrayList<>()
        );
        Chef savedChef = _chefRepository.save(chef);
        return new ChefDto.ChefBuilder()
                .setChefId(savedChef.getUserId())
                .setChefName(savedChef.getChefName())
                .setChefHandle(savedChef.getChefHandle())
                .setEmail(savedChef.getEmail())
                .build();
    }

    public String login(ChefLoginDto chefLoginDto) throws ChefNotFoundException, AuthenticationException {
        Optional<Chef> chef = _chefRepository.findByEmail(chefLoginDto.getEmail());
        if(!chef.isPresent()){
            throw new ChefNotFoundException("chef is not present. please register first");
        }
        Authentication authentication = _authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                chefLoginDto.getEmail(), chefLoginDto.getPassword()));
        if (!authentication.isAuthenticated()) {
            throw new AuthenticationException("Authentication Failed") {};
        }
        User userDetails = (User)authentication.getPrincipal();
        return jwtTokenUtil.generateToken(userDetails);
    }

    public Chef createChef(Chef chef) {
        return _chefRepository.save(chef);
    }

    public List<Chef> getAllChefs() {
        return _chefRepository.findAll();
    }

    public Chef getChefById(Long id) throws ChefNotFoundException {
        if(_chefRepository.findById(id).isPresent()){
            return _chefRepository.findById(id).get();
        }
        throw new ChefNotFoundException("Chef not found with id: " + id);
    }

    public List<Chef> getChefByName(String name) {
        return _chefRepository.findByName(name);
    }

    public void deleteChef(Chef chef) {
        _chefRepository.delete(chef);
    }

    public PageResponse<Chef> getPaginatedChefs(int pageNo, int pageSize, String sortBy, Sort.Direction sortDir) {
        Sort sort = Sort.by(sortDir, sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Chef> paginatedChef = _chefRepository.findAll(pageable);
        return new PageResponse<Chef> (
                paginatedChef.getTotalPages(),
                paginatedChef.isFirst(),
                paginatedChef.isLast(),
                paginatedChef.hasNext(),
                paginatedChef.hasPrevious(),
                paginatedChef.getContent(),
                paginatedChef.getNumber(),
                paginatedChef.getSize(),
                paginatedChef.getTotalElements()
        );
    }
}
