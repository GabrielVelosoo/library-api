package com.github.gabrielvelosoo.libraryapi.security;

import com.github.gabrielvelosoo.libraryapi.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityService {

    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof CustomAuthentication customAuthentication) {
            return customAuthentication.getUser();
        }
        return null;
    }
}
