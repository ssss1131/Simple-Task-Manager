package kz.ssss.taskmanager.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constant {

    public static final String BASE_URL = "/api";
    public static final String BASE_AUTH_URL = BASE_URL + "/auth";

    public static final String LOGIN_ENDPOINT = "/login";
    public static final String REGISTER_ENDPOINT = "/register";
    public static final String LOGIN_URL = BASE_AUTH_URL + LOGIN_ENDPOINT;
    public static final String REGISTER_URL = BASE_AUTH_URL + REGISTER_ENDPOINT;
    public static final String LOGOUT_URL = BASE_AUTH_URL + "/logout";
}
