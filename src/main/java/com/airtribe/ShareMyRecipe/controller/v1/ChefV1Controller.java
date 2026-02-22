package com.airtribe.ShareMyRecipe.controller.v1;

import com.airtribe.ShareMyRecipe.dto.chef.request.ChefLoginDto;
import com.airtribe.ShareMyRecipe.dto.chef.request.ChefRegistrationDto;
import com.airtribe.ShareMyRecipe.dto.chef.response.ChefDto;
import com.airtribe.ShareMyRecipe.dto.PageResponse;
import com.airtribe.ShareMyRecipe.entity.Chef;
import com.airtribe.ShareMyRecipe.entity.VerificationToken;
import com.airtribe.ShareMyRecipe.exception.UserNotFoundException;
import com.airtribe.ShareMyRecipe.exception.chef.ChefAlreadyExistsException;
import com.airtribe.ShareMyRecipe.exception.chef.ChefNotFoundException;
import com.airtribe.ShareMyRecipe.exception.verificationtoken.TokenExpiredException;
import com.airtribe.ShareMyRecipe.exception.verificationtoken.TokenNotFoundException;
import com.airtribe.ShareMyRecipe.repository.VerificationTokenRepository;
import com.airtribe.ShareMyRecipe.service.ChefManagementService;
import com.airtribe.ShareMyRecipe.service.VerificationTokenService;
import com.airtribe.ShareMyRecipe.util.VerificationTokenUtil;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chefs")
public class ChefV1Controller {

    @Autowired
    private ChefManagementService _chefManagementService;

    @Autowired
    private VerificationTokenService _verificationTokenService;

    @PostMapping("/auth/register")
    public ResponseEntity<ChefDto> registerChef (@Valid @RequestBody ChefRegistrationDto chefRegistrationDto)
            throws ChefAlreadyExistsException, UserNotFoundException {

        ChefDto dto =  _chefManagementService.register(chefRegistrationDto);
        _verificationTokenService.verifyUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping("/auth/verify-token")
    public ResponseEntity<ChefDto> verifyUser(@RequestParam("token") String token)
            throws TokenNotFoundException, TokenExpiredException, UserNotFoundException {
        Chef chef = (Chef)_verificationTokenService.validateToken(token);
        return ResponseEntity.ok(new ChefDto.ChefBuilder()
                .setChefName(chef.getChefName())
                .setChefId(chef.getUserId())
                .setChefHandle(chef.getChefHandle())
                .setEmail(chef.getEmail())
                .setEnabled(chef.isEnabled())
                .setRole(chef.getRole())
                .build()
        );
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> loginChef(@Valid @RequestBody ChefLoginDto chefLoginDto) throws ChefNotFoundException {
        String token = _chefManagementService.login(chefLoginDto);
        return ResponseEntity.ok(token);
    }



    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<Chef> getAllChefs(
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "userId") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir
    ) {

        // validation of arguments
        if(pageNo < 0) pageNo = 0;
        if(pageSize >= 200) pageSize = 10;
        if(!(sortDir.equals("asc") || sortDir.equals("desc"))) sortDir = "asc";
        if(!(sortBy.equals("userId") || sortBy.equals("chefName") || sortBy.equals("chefHandle"))) sortBy = "userId";

        Sort.Direction dirToSort = sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        return _chefManagementService.getPaginatedChefs(pageNo, pageSize, sortBy, dirToSort);
    }
}
