package com.github.gabrielvelosoo.libraryapi.services;

import com.github.gabrielvelosoo.libraryapi.models.User;
import com.github.gabrielvelosoo.libraryapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public void saveUser(User user) {
        String password = user.getPassword();
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
    }

    public User findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
