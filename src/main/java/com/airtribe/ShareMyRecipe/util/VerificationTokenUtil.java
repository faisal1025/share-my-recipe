package com.airtribe.ShareMyRecipe.util;

import com.airtribe.ShareMyRecipe.entity.AbstractUserBase;
import com.airtribe.ShareMyRecipe.entity.VerificationToken;

import java.util.UUID;

public class VerificationTokenUtil {
    public static VerificationToken generateToken(AbstractUserBase user) {
        VerificationToken returnToken = new VerificationToken();
        String token = UUID.randomUUID().toString();
        returnToken.setToken(token);
        returnToken.setUser(user);
        return returnToken;
    }
    public static String generateUrl(String token) {
        String url = "http://127.0.0.1:3056/api/v1/chefs/verify-token?token="+token;
        return url;
    }
}
