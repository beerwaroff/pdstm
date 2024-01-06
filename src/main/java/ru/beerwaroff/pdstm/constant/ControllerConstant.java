package ru.beerwaroff.pdstm.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static ru.beerwaroff.pdstm.constant.SymbolConstant.SLASH;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ControllerConstant {
    public static final String LOGIN_URL = SLASH + "login";
    public static final String LOGIN_FAILURE_URL = SLASH + "login-failure";
    public static final String REGISTER_URL = SLASH + "register";
    public static final String LOGOUT_URL = SLASH + "logout";
    public static final String USERS_URL = SLASH + "users";
    public static final String PASSWORD_CHANGING_URL = SLASH + "password-changing";
    public static final String ACCOUNT_DELETING_URL = SLASH + "account-deleting";
    public static final String ACTIVATION_URL = SLASH + "activation";
    public static final String DOWNLOAD_URL = SLASH + "download";

    public static final String CODE_PATH_VARIABLE = "code";
    public static final String CODE_PATH_VARIABLE_URL = SLASH + "{" + CODE_PATH_VARIABLE + "}";
    public static final String LINK_PATH_VARIABLE = "link";
    public static final String LINK_PATH_VARIABLE_URL = SLASH + "{" + LINK_PATH_VARIABLE + "}";

    public static final String DOWNLOAD_PDF_MODEL_URL = DOWNLOAD_URL + LINK_PATH_VARIABLE_URL;
}
