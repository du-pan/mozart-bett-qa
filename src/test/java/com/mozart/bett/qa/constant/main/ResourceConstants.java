package com.mozart.bett.qa.constant.main;

public class ResourceConstants {
    // --- File PATHs ---------------------------------------------------------------------
    public static final String API_TEST_JSON_PATH = "src/test/resources/json/api/";
    public static final String WEB_TEST_JSON_PATH = "src/test/resources/json/web/";
    public static final String SCREENSHOT_FILE_PATH = "src/test/resources/screenshots/";
    public static final String SCREENSHOT_FILE_NAME = "TEST-%s-%s.png";
    public static final String SCREENSHOT_ON_FAILURE_FILE_PATH = SCREENSHOT_FILE_PATH + "%s";
    public static final String PNG_FILE_EXTENSION = ".png";
    // ------------------------------------------------------------------------------------
    // --- API JSON File names ------------------------------------------------------------
    public static final String FORBIDDEN_RESPONSE_BODY =
            API_TEST_JSON_PATH + "forbiddenResponseBody.json";
    public static final String UNAUTHORIZED_RESPONSE_BODY =
            API_TEST_JSON_PATH + "unauthorizedResponseBody.json";
    // ------------------------------------------------------------------------------------
    // --- WEB JSON File names ------------------------------------------------------------
    public static final String ADMIN_LOGIN_JSON = WEB_TEST_JSON_PATH + "adminLogin.json";
    public static final String WRONG_PASSWORD_LOGIN_JSON = WEB_TEST_JSON_PATH + "wrongPasswordLogin.json";
    // ------------------------------------------------------------------------------------
}
