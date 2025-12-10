package prismify.auth.infrastructure.security.jwt;

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

import prismify.auth.domain.model.AuthUserDetails;
import prismify.auth.domain.ports.out.TokenProvider;
import prismify.auth.infrastructure.controllers.exception.custom.JWTException;

/**
 * Proveedor de tokens JWT que implementa la interfaz {@link TokenProvider}.
 * <p>
 * Esta clase se encarga de generar, validar y extraer información de
 * tokens JWT utilizando una clave secreta y un tiempo de expiración
 * configurables mediante propiedades de Spring.
 * 
 * @author Adolfo Plaza
 */
@Component
public class JwtTokenProvider implements TokenProvider {

    @Value("${jwt.secret.key}")
    private String secret;

    @Value("${jwt.expiration.time}")
    private String expiration;

    /**
     * Genera un token JWT para un usuario autenticado.
     *
     * @param user Detalles del usuario autenticado.
     * @return Token JWT firmado como cadena.
     * @throws RuntimeException Si ocurre un error al generar el token.
     */
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
            throw new JWTException("Error generating JWT token");
        }
    }

    /**
     * Valida un token JWT verificando su firma y fecha de expiración.
     *
     * @param token Token JWT a validar.
     * @return {@code true} si el token es válido; {@code false} en caso contrario.
     */
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

    /**
     * Obtiene el conjunto de claims de un token JWT.
     *
     * @param token Token JWT del cual extraer los claims.
     * @return Conjunto de claims {@link JWTClaimsSet}.
     * @throws RuntimeException Si el token no es válido o no puede ser parseado.
     */
    @Override
    public JWTClaimsSet getClaims(String token) {
        try {
            return SignedJWT.parse(token).getJWTClaimsSet();
        } catch (ParseException e) {
            throw new JWTException("Invalid token");
        }
    }

    /**
     * Extrae el nombre de usuario de un token JWT.
     *
     * @param token Token JWT.
     * @return Nombre de usuario contenido en el token.
     */
    @Override
    public String extractUsername(String token) {
        return getClaim(token, JWTClaimsSet::getSubject);
    }

    /**
     * Extrae el ID del usuario de un token JWT.
     *
     * @param token Token JWT.
     * @return ID del usuario contenido en el token.
     */
    @Override
    public Long extractUserId(String token) {
        return getClaim(token, claims -> (Long) claims.getClaim("id"));
    }

    /**
     * Extrae el rol del usuario de un token JWT.
     *
     * @param token Token JWT.
     * @return Rol del usuario contenido en el token.
     */
    @Override
    public String extractRole(String token) {
        return getClaim(token, claims -> (String) claims.getClaim("role"));
    }

    /**
     * Extrae el estado de actividad del usuario de un token JWT.
     *
     * @param token Token JWT.
     * @return {@code true} si el usuario está activo; {@code false} en caso
     *         contrario.
     */
    @Override
    public boolean extractIsActive(String token) {
        Boolean isActive = getClaim(token, claims -> (Boolean) claims.getClaim("isActive"));
        return isActive != null && isActive;
    }

    /**
     * Obtiene un claim específico del token aplicando una función sobre el
     * {@link JWTClaimsSet}.
     *
     * @param <T>            Tipo de dato del claim a extraer.
     * @param token          Token JWT.
     * @param claimsFunction Función que recibe los claims y devuelve el valor
     *                       deseado.
     * @return Valor del claim extraído.
     */
    private <T> T getClaim(String token, Function<JWTClaimsSet, T> claimsFunction) {
        JWTClaimsSet claims = getClaims(token);
        return claimsFunction.apply(claims);
    }

}
