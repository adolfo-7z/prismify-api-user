package com.ufro.dci.etransparency.etransparency_api_user.config.security.filters;

import org.springframework.security.core.AuthenticationException;

import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;
import java.text.SimpleDateFormat;
import java.util.*;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufro.dci.etransparency.etransparency_api_user.config.security.models.CustomUserDetails;
import com.ufro.dci.etransparency.etransparency_api_user.config.security.utils.JwtUtils;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity;
import com.ufro.dci.etransparency.etransparency_api_user.models.administrator.Administrator;
import com.ufro.dci.etransparency.etransparency_api_user.models.auditor.Auditor;
import com.ufro.dci.etransparency.etransparency_api_user.models.manager.Manager;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JwtUtils jwtUtils;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        UserEntity user = null;
        String username = "";
        String password = "";
        try {
            user = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
            username = user.getUsername();
            password = user.getPassword();
        } catch (Exception e) {
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            FilterChain filter, Authentication auth)
            throws IOException, ServletException, JsonProcessingException, java.io.IOException {

        CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();
        UserEntity userEntity = customUserDetails.getUserEntity();

        Long userId = getUserId(userEntity);
        String token = jwtUtils.generateAccessToken(userEntity.getUsername(), userId, userEntity.getRole().name());
        response.addHeader("Authorization", token);
        Map<String, Object> httpResponse = new HashMap<>();

        httpResponse.put("token", token);
        httpResponse.put("message", OPERATION_SUCCESSFUL);
        httpResponse.put("timestamp", dateFormat.format(new Date()));
        httpResponse.put("status", HttpStatus.OK.value());

        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();
    }

    private Long getUserId(UserEntity userEntity) {
        if (userEntity instanceof Administrator) {
            return ((Administrator) userEntity).getId();
        } else if (userEntity instanceof Auditor) {
            return ((Auditor) userEntity).getId();
        } else if (userEntity instanceof Manager) {
            return ((Manager) userEntity).getId();
        }
        return null;
    }

}
