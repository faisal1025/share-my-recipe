package com.airtribe.ShareMyRecipe.util;

import com.airtribe.ShareMyRecipe.entity.AbstractUserBase;
import com.airtribe.ShareMyRecipe.entity.VerificationToken;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

public class VerificationTokenUtil {

    @Value("${server-url: http://localhost:3056}")
    private static String serverUrl;

    public static VerificationToken generateToken(AbstractUserBase user) {
        VerificationToken returnToken = new VerificationToken();
        String token = UUID.randomUUID().toString();
        returnToken.setToken(token);
        returnToken.setUser(user);
        return returnToken;
    }
    public static String generateUrl(String token) {
        String url = serverUrl+"/api/v1/chefs/auth/verify-token?token="+token;
        return url;
    }
}
