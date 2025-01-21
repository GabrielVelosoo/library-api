package com.github.gabrielvelosoo.libraryapi.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginViewController {

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping(value = "/")
    @ResponseBody
    public String homePage(Authentication authentication) {
        return "Olá, " + authentication.getName();
    }
}
