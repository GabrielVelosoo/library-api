package com.github.gabrielvelosoo.libraryapi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginViewController {

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }
}
