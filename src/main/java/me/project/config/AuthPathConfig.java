package me.project.config;

public class AuthPathConfig {
    public static final String[] AUTH_EXCLUDED_PATHS = {
            "/login/**",
            "/api/comments",
//            HACKABLE
//            "/api/users"
    };
}
