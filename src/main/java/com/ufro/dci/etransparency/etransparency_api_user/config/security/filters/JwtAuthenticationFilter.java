package com.ufro.dci.etransparency.etransparency_api_user.config.security.filters;

import java.util.*;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.AuthenticationException;

import java.text.SimpleDateFormat;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.*;

import org.springframework.security.authentication.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;

import com.ufro.dci.etransparency.etransparency_api_user.config.security.models.CustomUserDetails;
import com.ufro.dci.etransparency.etransparency_api_user.config.security.utils.JwtUtils;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity;
import com.ufro.dci.etransparency.etransparency_api_user.models.administrator.Administrator;
import com.ufro.dci.etransparency.etransparency_api_user.models.auditor.Auditor;
import com.ufro.dci.etransparency.etransparency_api_user.models.manager.Manager;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtils jwtUtils;

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
            throw new AuthenticationServiceException("Unable to authenticate user", e);
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            FilterChain filter, Authentication auth)
            throws ServletException, IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
        if (userEntity instanceof Administrator administrator) {
            return administrator.getId();
        } else if (userEntity instanceof Auditor auditor) {
            return auditor.getId();
        } else if (userEntity instanceof Manager manager) {
            return manager.getId();
        }
        return null;
    }

}
