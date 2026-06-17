package com.airtribe.ShareMyRecipe.service;


import com.airtribe.ShareMyRecipe.dto.chef.request.ChefLoginDto;
import com.airtribe.ShareMyRecipe.dto.chef.request.ChefRegistrationDto;
import com.airtribe.ShareMyRecipe.dto.chef.response.ChefDto;
import com.airtribe.ShareMyRecipe.dto.PageResponse;
import com.airtribe.ShareMyRecipe.dto.chef.response.ChefWithoutRecipeDto;
import com.airtribe.ShareMyRecipe.dto.recipe.response.RecipeWithoutChefDto;
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
        return mapChefToChefDto(savedChef);
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

    public ChefDto createChef(Chef chef) {
        Chef savedChef = _chefRepository.save(chef);
        return mapChefToChefDto(savedChef);
    }

    public List<ChefDto> getAllChefs() {
        List<Chef> chefs = _chefRepository.findAll();
        return chefs.stream().map(this::mapChefToChefDto).toList();
    }

    public ChefDto getChefById(Long id) throws ChefNotFoundException {
        if(_chefRepository.findById(id).isPresent()){
            Chef chef = _chefRepository.findById(id).get();
            return mapChefToChefDto(chef);
        }
        throw new ChefNotFoundException("Chef not found with id: " + id);
    }

    public List<ChefDto> getChefByName(String name) {
        List<Chef> chefs = _chefRepository.findByName(name);
        return chefs.stream().map(this::mapChefToChefDto).toList();
    }

    public void deleteChef(Long chefId) throws ChefNotFoundException{
        Optional<Chef> chef = _chefRepository.findById(chefId);
        if(chef.isEmpty()){
            throw new ChefNotFoundException("Chef not found with Chef Id: "+chefId);
        }
        _chefRepository.delete(chef.get());
    }

    public PageResponse<ChefDto> getPaginatedChefs(int pageNo, int pageSize, String sortBy, Sort.Direction sortDir) {
        Sort sort = Sort.by(sortDir, sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<ChefDto> paginatedChef = _chefRepository.findAll(pageable).map(this::mapChefToChefDtoWithoutRecipe);
        return new PageResponse<ChefDto> (
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

    private ChefDto mapChefToChefDto(Chef chef) {
        return new ChefDto.ChefBuilder()
                .setChefId(chef.getUserId())
                .setChefName(chef.getChefName())
                .setChefHandle(chef.getChefHandle())
                .setEmail(chef.getEmail())
                .setRecipes(chef.getRecipes().stream().map(RecipeWithoutChefDto::new).toList())
                .build();
    }

    private ChefDto mapChefToChefDtoWithoutRecipe(Chef chef) {
        return new ChefDto.ChefBuilder()
                .setChefId(chef.getUserId())
                .setChefName(chef.getChefName())
                .setChefHandle(chef.getChefHandle())
                .setEmail(chef.getEmail())
                .build();
    }
}
