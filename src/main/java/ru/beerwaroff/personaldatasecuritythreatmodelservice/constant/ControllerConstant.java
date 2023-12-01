package ru.beerwaroff.personaldatasecuritythreatmodelservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static ru.beerwaroff.personaldatasecuritythreatmodelservice.constant.SymbolConstant.SLASH;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ControllerConstant {
    public static final String LOGIN_URL = SLASH + "login";
    public static final String REGISTER_URL = SLASH + "register";
    public static final String LOGOUT_URL = SLASH + "logout";
    public static final String USERS_URL = SLASH + "users";
    public static final String PASSWORD_CHANGING_URL = SLASH + "password-changing";
    public static final String ACCOUNT_DELETING_URL = SLASH + "account-deleting";
    public static final String ACTIVATION_URL = SLASH + "activation";

    public static final String CODE_PATH_VARIABLE = "code";
    public static final String CODE_PATH_VARIABLE_URL = SLASH + "{" + CODE_PATH_VARIABLE + "}";
}
