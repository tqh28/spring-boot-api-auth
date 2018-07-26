package com.auth0.samples.authapi.constant;

public class SecurityConstants {

    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 600_00; // 60 seconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";

}
