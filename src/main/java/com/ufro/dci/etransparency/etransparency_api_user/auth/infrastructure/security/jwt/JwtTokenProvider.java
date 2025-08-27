package com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.security.jwt;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.*;

import com.ufro.dci.etransparency.etransparency_api_user.auth.domain.model.AuthUserDetails;
import com.ufro.dci.etransparency.etransparency_api_user.auth.domain.ports.out.TokenProvider;

@Component
public class JwtTokenProvider implements TokenProvider {

    @Value("${jwt.secret.key}")
    private String secret;

    @Value("${jwt.expiration.time}")
    private String expiration;

    @Override
    public String generateToken(AuthUserDetails user) {
        try {
            Instant now = Instant.now();
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(user.username())
                    .claim("role", user.role())
                    .claim("id", user.id())
                    .claim("isActive", user.isActive())
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

    @Override
    public boolean validateToken(String token) {
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

    @Override
    public JWTClaimsSet getClaims(String token) {
        try {
            return SignedJWT.parse(token).getJWTClaimsSet();
        } catch (ParseException e) {
            throw new RuntimeException("Invalid token", e);
        }
    }

    @Override
    public String extractUsername(String token) {
        return getClaim(token, JWTClaimsSet::getSubject);
    }

    @Override
    public Long extractUserId(String token) {
        return getClaim(token, claims -> (Long) claims.getClaim("id"));
    }

    @Override
    public String extractRole(String token) {
        return getClaim(token, claims -> (String) claims.getClaim("role"));
    }

    @Override
    public boolean extractIsActive(String token) {
        Boolean isActive = getClaim(token, claims -> (Boolean) claims.getClaim("isActive"));
        return isActive != null && isActive;
    }

    private <T> T getClaim(String token, Function<JWTClaimsSet, T> claimsFunction) {
        JWTClaimsSet claims = getClaims(token);
        return claimsFunction.apply(claims);
    }
}
