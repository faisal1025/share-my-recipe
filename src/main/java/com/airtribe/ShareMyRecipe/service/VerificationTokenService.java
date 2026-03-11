package com.airtribe.ShareMyRecipe.service;

import com.airtribe.ShareMyRecipe.dto.UserBaseResponseDto;
import com.airtribe.ShareMyRecipe.dto.chef.response.ChefDto;
import com.airtribe.ShareMyRecipe.entity.AbstractUserBase;
import com.airtribe.ShareMyRecipe.entity.Chef;
import com.airtribe.ShareMyRecipe.entity.VerificationToken;
import com.airtribe.ShareMyRecipe.exception.UserNotFoundException;
import com.airtribe.ShareMyRecipe.exception.verificationtoken.TokenExpiredException;
import com.airtribe.ShareMyRecipe.exception.verificationtoken.TokenNotFoundException;
import com.airtribe.ShareMyRecipe.repository.AbstractUserBaseRepository;
import com.airtribe.ShareMyRecipe.repository.VerificationTokenRepository;
import com.airtribe.ShareMyRecipe.service.emailservice.EmailService;
import com.airtribe.ShareMyRecipe.util.VerificationTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VerificationTokenService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationTokenRepository _verificationTokenRepo;

    @Autowired
    private AbstractUserBaseRepository _abstractUserBaseRepo;

    public void verifyUser(UserBaseResponseDto dto) throws UserNotFoundException {
        if(dto == null){
            throw new IllegalArgumentException("UserDto can't be null");
        }
        Optional<AbstractUserBase> user = _abstractUserBaseRepo.findByUserId(dto.getUserId());
        if(!user.isPresent()){
            throw new UserNotFoundException("user not present with id: "+dto.getUserId());
        }
        VerificationToken token = VerificationTokenUtil.generateToken(user.get());
        VerificationToken savedToken = _verificationTokenRepo.save(token);
        String url = VerificationTokenUtil.generateUrl(savedToken.getToken());
        System.out.println("Please verify it your email: "+url+"\n Before: "+savedToken.getExpirationDate().getHour()+" Hours");
        emailService.sendEmail(new String[]{user.get().getEmail()},
                "Verify Your Email",
                "Please verify it your email: "+url+
                        "\n Before: "+savedToken.getExpirationDate().getHour()+
                        " Hours. Or use this token: " +savedToken.getToken()+ " to verify through API");
    }

    public AbstractUserBase validateToken(String token) throws TokenNotFoundException, TokenExpiredException, UserNotFoundException{
        Optional<VerificationToken> optionalFullToken = _verificationTokenRepo.findByToken(token);
        if(!optionalFullToken.isPresent()){
            throw new TokenNotFoundException("Token is not found");
        }
        VerificationToken fullToken = optionalFullToken.get();
        if(fullToken.getExpirationDate().isBefore(LocalDateTime.now())){
            _verificationTokenRepo.delete(fullToken);
            throw new TokenExpiredException("This token is expired");
        }
        Optional<AbstractUserBase> optionalUser = _abstractUserBaseRepo
                .findByUserId(fullToken.getUser().getUserId());
        if(!optionalUser.isPresent()){
            throw new UserNotFoundException("user associated with the token not found");
        }
        AbstractUserBase user = optionalUser.get();
        user.setEnabled(true);
        AbstractUserBase enabledUser = _abstractUserBaseRepo.save(user);
        _verificationTokenRepo.delete(fullToken);
        return enabledUser;
    }
}
