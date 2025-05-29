package kz.ssss.taskmanager.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constant {

    public static final String BASE_URL = "/api";
    public static final String BASE_AUTH_URL = BASE_URL + "/auth";
    public static final String LOGOUT_URL = BASE_AUTH_URL + "/logout";
}
