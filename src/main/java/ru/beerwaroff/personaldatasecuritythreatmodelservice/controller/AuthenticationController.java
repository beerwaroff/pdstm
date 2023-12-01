package ru.beerwaroff.personaldatasecuritythreatmodelservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import static ru.beerwaroff.personaldatasecuritythreatmodelservice.constant.ControllerConstant.LOGIN_URL;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    @GetMapping
    public String home() {
        return "index";
    }

    @GetMapping(LOGIN_URL)
    public String loginPage() {
        return "login";
    }

    @PostMapping(LOGIN_URL)
    public String login() {
        return "redirect:index";
    }
}
