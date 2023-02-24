package com.example.shop.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.experimental.UtilityClass;
import java.time.Instant;

@UtilityClass
public class JwtUtils {
    public final String BEARER = "Bearer ";
    public final String LOGIN_CLAIM = "login";

    public String generateToken(String userName){
        return BEARER + JWT.create()
                .withClaim(LOGIN_CLAIM, userName)
                .withExpiresAt(Instant.now().plusSeconds(3600))
                .sign(getAlgorithm());
    }

    public boolean validateToken(String token){
        DecodedJWT jwt = JWT.decode(token);
        getAlgorithm().verify(jwt);
        return jwt.getExpiresAt().toInstant().isAfter(Instant.now());
    }

    public String getLogin(String token){
        return JWT.decode(token).getClaim(LOGIN_CLAIM).asString();
    }
    private static Algorithm getAlgorithm() {
        return Algorithm.HMAC256("secret");
    }

}
