package com.study.projectjeju.vos;

public class LoginVo {
    private static final String EMAIL_REGEX = "^(?=.{8,100}$)(?!.*[\\-]{2,}.*$)(?!.*[_]{2,}.*$)(?!.*[.]{2,}.*$)([0-9a-zA-Z][0-9a-zA-Z\\-_.]*[0-9a-zA-Z])@([a-z][a-z\\-]*[a-z])\\.([a-z]{2,15})(\\.[a-z]{2})?$";
    private static final String PASSWORD_REGEX = "^([0-9a-zA-Z~!@#$%^&*()\\-_=+\\[{\\]}\\\\|;:'\",<.>/?]{4,100})$";
    private final String email;
    private final String password;
    private final boolean isNormalized;

    public LoginVo(String email, String password) {
        if (email.matches(EMAIL_REGEX) && password.matches(PASSWORD_REGEX)) {
            this.email = email;
            this.password = password;
            this.isNormalized = true;
        } else {
            this.email = null;
            this.password = null;
            this.isNormalized = false;
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isNormalized() {
        return isNormalized;
    }
}
