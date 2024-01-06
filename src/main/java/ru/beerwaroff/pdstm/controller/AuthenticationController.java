package ru.beerwaroff.pdstm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import static ru.beerwaroff.pdstm.constant.ControllerConstant.LOGIN_FAILURE_URL;
import static ru.beerwaroff.pdstm.constant.ControllerConstant.LOGIN_URL;

@Controller
public class AuthenticationController {

    @GetMapping(LOGIN_URL)
    public String loginPage() {
        return "login";
    }

    @GetMapping(LOGIN_FAILURE_URL)
    public String loginFailurePage() {
        return "login_failure";
    }

    @PostMapping(LOGIN_URL)
    public String login() {
        return "redirect:index";
    }
}
