package com.github.gabrielvelosoo.libraryapi.security;

import com.github.gabrielvelosoo.libraryapi.models.User;
import com.github.gabrielvelosoo.libraryapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String enteredPassword = authentication.getCredentials().toString();
        User foundUser = userService.findUserByLogin(login);
        if(foundUser == null) {
            throw getUserNotFoundError();
        }
        String encryptedPassword = foundUser.getPassword();
        boolean checkedPassword = checkPassword(enteredPassword, encryptedPassword);
        if(checkedPassword) {
            return new CustomAuthentication(foundUser);
        }
        throw getUserNotFoundError();
    }

    private boolean checkPassword(String enteredPassword, String encryptedPassword) {
        return encoder.matches(enteredPassword, encryptedPassword);
    }

    private UsernameNotFoundException getUserNotFoundError() {
        return new UsernameNotFoundException("Incorrect username and/or password!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
