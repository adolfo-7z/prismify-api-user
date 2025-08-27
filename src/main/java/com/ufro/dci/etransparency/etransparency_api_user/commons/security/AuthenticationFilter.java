package com.ufro.dci.etransparency.etransparency_api_user.commons.security;

import java.io.IOException;
import java.util.*;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final String secretKey;

    /**
     * Constructor que inyecta la clave secreta para verificar los tokens JWT.
     *
     * @param secretKey clave secreta configurada en la aplicación.
     */
    public AuthenticationFilter(@Value("${jwt.secret.key}") String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * Método principal del filtro que procesa cada solicitud entrante.
     * <p>
     * Extrae el encabezado {@code Authorization}, valida el token JWT y, si es
     * correcto, establece la autenticación en el contexto de seguridad.
     * </p>
     *
     * @param request     la solicitud HTTP entrante.
     * @param response    la respuesta HTTP en construcción.
     * @param filterChain la cadena de filtros que continúa el procesamiento.
     * @throws ServletException si ocurre un error en la ejecución del filtro.
     * @throws IOException      si ocurre un error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                SignedJWT signedJWT = SignedJWT.parse(token);
                JWSVerifier verifier = new MACVerifier(secretKey.getBytes());
                if (!signedJWT.verify(verifier)) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT signature");
                    return;
                }
                Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
                if (expirationTime != null && expirationTime.before(new Date())) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
                    return;
                }
                String username = signedJWT.getJWTClaimsSet().getSubject();
                String role = signedJWT.getJWTClaimsSet().getStringClaim("role");
                List<GrantedAuthority> authorities = new ArrayList<>();
                if (role != null) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                }
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

}
