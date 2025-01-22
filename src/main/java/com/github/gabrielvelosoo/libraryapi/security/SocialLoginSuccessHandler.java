package com.github.gabrielvelosoo.libraryapi.security;

import com.github.gabrielvelosoo.libraryapi.models.User;
import com.github.gabrielvelosoo.libraryapi.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SocialLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String DEFAULT_PASSWORD = "1234567";

    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws ServletException, IOException {
        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User auth2User = auth2AuthenticationToken.getPrincipal();
        String email = auth2User.getAttribute("email");
        User user = userService.findUserByEmail(email);
        if(user == null) {
            user = saveNewUserLoggedWithGoogle(email);
        }
        CustomAuthentication customAuthentication = new CustomAuthentication(user);
        SecurityContextHolder.getContext().setAuthentication(customAuthentication);
        super.onAuthenticationSuccess(request, response, customAuthentication);
    }

    private User saveNewUserLoggedWithGoogle(String email) {
        User user = new User();
        user.setLogin(getLoginByEmail(email));
        user.setEmail(email);
        user.setPassword(DEFAULT_PASSWORD);
        user.setRoles(List.of("OPERATOR"));
        userService.saveUser(user);
        return user;
    }

    private String getLoginByEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}
