package ru.beerwaroff.pdstm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.beerwaroff.pdstm.dto.request.EmailRequest;
import ru.beerwaroff.pdstm.dto.request.PasswordChangingRequest;
import ru.beerwaroff.pdstm.mapper.UserMapper;
import ru.beerwaroff.pdstm.service.UserService;

import javax.validation.Valid;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static ru.beerwaroff.pdstm.constant.ControllerConstant.ACCOUNT_DELETING_URL;
import static ru.beerwaroff.pdstm.constant.ControllerConstant.ACTIVATION_URL;
import static ru.beerwaroff.pdstm.constant.ControllerConstant.CODE_PATH_VARIABLE;
import static ru.beerwaroff.pdstm.constant.ControllerConstant.CODE_PATH_VARIABLE_URL;
import static ru.beerwaroff.pdstm.constant.ControllerConstant.PASSWORD_CHANGING_URL;
import static ru.beerwaroff.pdstm.constant.ControllerConstant.REGISTER_URL;
import static ru.beerwaroff.pdstm.constant.SymbolConstant.SLASH;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final String REQUEST_MODEL_ATTRIBUTE = "request";
    private final String REDIRECT = "redirect:";
    private final String REGISTER_TEMPLATE = "register";
    private final String PASSWORD_CHANGING_TEMPLATE = "password_changing";

    private final UserService service;
    private final UserMapper mapper;

    @GetMapping(REGISTER_URL)
    public String getRegisterPage(Model model) {
        model.addAttribute(REQUEST_MODEL_ATTRIBUTE, new EmailRequest());
        return REGISTER_TEMPLATE;
    }

    @PostMapping(REGISTER_URL)
    public String register(@Valid @ModelAttribute(REQUEST_MODEL_ATTRIBUTE) EmailRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return REGISTER_TEMPLATE;
        }
        service.add(
                mapper.map(request)
        );
        return REDIRECT + ACTIVATION_URL;
    }

    @GetMapping(PASSWORD_CHANGING_URL)
    public String getPasswordChangingPage(Model model) {
        model.addAttribute(REQUEST_MODEL_ATTRIBUTE, new PasswordChangingRequest());
        model.addAttribute(CODE_PATH_VARIABLE, service.getByEmail(getEmailByContext()).getCode());
        return PASSWORD_CHANGING_TEMPLATE;
    }

    @GetMapping(PASSWORD_CHANGING_URL + CODE_PATH_VARIABLE_URL)
    public String getPasswordChangingByCodePage(@PathVariable(CODE_PATH_VARIABLE) String code, Model model) {
        model.addAttribute(REQUEST_MODEL_ATTRIBUTE, new PasswordChangingRequest());
        model.addAttribute(CODE_PATH_VARIABLE, code);
        return PASSWORD_CHANGING_TEMPLATE;
    }

    @PostMapping(PASSWORD_CHANGING_URL + CODE_PATH_VARIABLE_URL)
    public String changePasswordByCode(
            @PathVariable(CODE_PATH_VARIABLE) String code,
            @Valid @ModelAttribute(REQUEST_MODEL_ATTRIBUTE) PasswordChangingRequest request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return PASSWORD_CHANGING_TEMPLATE;
        }
        service.changePassword(code, request.getPassword(), request.getDuplicatePassword());
        return REDIRECT + SLASH;
    }

    @PostMapping(PASSWORD_CHANGING_URL)
    public String changePassword(
            @Valid @ModelAttribute(REQUEST_MODEL_ATTRIBUTE) PasswordChangingRequest request,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            return PASSWORD_CHANGING_TEMPLATE;
        }
        service.changePassword(
                service.getByEmail(this.getEmailByContext()).getCode(),
                request.getPassword(),
                request.getDuplicatePassword()
        );
        return REDIRECT + SLASH;
    }

    @GetMapping(ACCOUNT_DELETING_URL)
    public String getAccountDeletingPage(Model model) {
        model.addAttribute(REQUEST_MODEL_ATTRIBUTE, new EmailRequest());
        return "account_deleting";
    }

    @PostMapping(ACCOUNT_DELETING_URL)
    public String deleteAccount() {
        service.deleteByEmail(this.getEmailByContext());
        return REDIRECT + SLASH;
    }

    @GetMapping(ACTIVATION_URL)
    public String getActivationPage() {
        return "activation";
    }

    private String getEmailByContext() {
        return getContext().getAuthentication().getName();
    }
}
