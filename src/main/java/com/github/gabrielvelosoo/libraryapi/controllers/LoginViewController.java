package com.github.gabrielvelosoo.libraryapi.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Hidden
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

    @GetMapping(value = "/authorized")
    @ResponseBody
    public String getAuthorizationCode(@RequestParam("code") String code) {
        return "Your authorization code: " + code;
    }
}
