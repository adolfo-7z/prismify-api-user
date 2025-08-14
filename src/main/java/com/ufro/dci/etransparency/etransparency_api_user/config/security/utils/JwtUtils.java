package com.ufro.dci.etransparency.etransparency_api_user.config.security.utils;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.*;

@Component
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secret;

    @Value("${jwt.expiration.time}")
    private String expiration;

    public String generateAccessToken(String username, Long id, String role) {
        try {
            Instant now = Instant.now();
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(username)
                    .claim("role", role)
                    .claim("id", id)
                    .issueTime(Date.from(now))
                    .expirationTime(Date.from(now.plusMillis(Long.parseLong(expiration))))
                    .build();
            JWSSigner signer = new MACSigner(secret.getBytes());
            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader(JWSAlgorithm.HS256),
                    claims);
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

    public boolean isTokenValid(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(secret.getBytes());
            boolean signatureValid = signedJWT.verify(verifier);
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            return signatureValid && expirationTime.after(new Date());
        } catch (JOSEException | ParseException e) {
            return false;
        }
    }

    public JWTClaimsSet getAllClaims(String token) {
        try {
            return SignedJWT.parse(token).getJWTClaimsSet();
        } catch (ParseException e) {
            throw new RuntimeException("Invalid token", e);
        }
    }

    public <T> T getClaim(String token, Function<JWTClaimsSet, T> claimsFunction) {
        JWTClaimsSet claims = getAllClaims(token);
        return claimsFunction.apply(claims);
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, JWTClaimsSet::getSubject);
    }

    public Long getIdFromToken(String token) {
        return getClaim(token, claims -> (Long) claims.getClaim("id"));
    }

    public String getRoleFromToken(String token) {
        return getClaim(token, claims -> (String) claims.getClaim("role"));
    }

}
