package com.example.coffee.utils;

import com.example.coffee.BuildConfig;

public final class Constants
{
    public static final String BASE_URL = BuildConfig.API_BASE_URL;
    public static final Boolean IS_DEBUG = BuildConfig.IS_DEBUG;
    public static final String PREF_NAME = "app_prefs";
    public static final String USER_ID = "USER_ID";
    public static final String USER_TOKEN = "USER_TOKEN";
    public static final String SERIAL_NUMBER = "SERIAL_NUMBER";
    public static final int DEFAULT_TIMEOUT = 30;
    // 错误码
    public static final String BAD_REQUEST_CODE = "1400";
    public static final String UNAUTHORIZED_CODE = "1401";
    public static final String FORBIDDEN_CODE = "1403";
    public static final String TOKEN_EXPIRED = "1998";
    public static final String TOKEN_INVALID = "1999";

    // 主题相关
    public static final String BACKGROUND = "BACKGROUND";
    public static final String MENU_BACKGROUND = "MENU_BACKGROUND";

    // 防止实例化
    private Constants()
    {
        throw new UnsupportedOperationException("Cannot instantiate this class");
    }
}
