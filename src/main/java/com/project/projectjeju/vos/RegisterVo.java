package com.project.projectjeju.vos;

import com.project.utility.Sha512;

public class RegisterVo {
    private static final String EMAIL_REGEX = "^(?=.{8,100}$)(?!.*[\\-]{2,}.*$)(?!.*[_]{2,}.*$)(?!.*[.]{2,}.*$)([0-9a-zA-Z][0-9a-zA-Z\\-_.]*[0-9a-zA-Z])@([a-z][a-z\\-]*[a-z])\\.([a-z]{2,15})(\\.[a-z]{2})?$";
    private static final String PASSWORD_REGEX = "^([0-9a-zA-Z~!@#$%^&*()\\-_=+\\[{\\]}\\\\|;:'\",<.>/?]{4,100})$";
    private static final String NAME_REGEX = "^([a-zA-Z가-힣]{2,10})$";
    private static final String NICKNAME_REGEX = "^([0-9a-zA-Z가-힣]{2,10})$";
    private static final String CONTACT_REGEX = "^([0-9]{11})$";
    private static final String BIRTH_REGEX = "^([0-9]{4,4})-([0-9]{2,2})-([0-9]{2,2})$";
    private final String email;
    private final String password;
    private final String hashedPassword;
    private final String name;
    private final String nickname;
    private final String contact;
    private final String birth;
    private final String image;
    private final boolean isNormalized;

    public RegisterVo(String email, String password, String name, String nickname, String contact, String birth, String image) {
        if (email.matches(RegisterVo.EMAIL_REGEX) &&
                password.matches(RegisterVo.PASSWORD_REGEX) &&
                name.matches(RegisterVo.NAME_REGEX) &&
                nickname.matches(RegisterVo.NICKNAME_REGEX) &&
                contact.matches(RegisterVo.CONTACT_REGEX) &&
                birth.matches(RegisterVo.BIRTH_REGEX)
        ) {
            this.isNormalized = true;
            this.email = email;
            this.password = password;
            this.hashedPassword = Sha512.hash(this.password);
            this.name = name;
            this.nickname = nickname;
            this.contact = contact;
            this.birth = birth;
            this.image = image;
        } else {
            this.email = null;
            this.password = null;
            this.hashedPassword = null;
            this.name = null;
            this.nickname = null;
            this.contact = null;
            this.birth = null;
            this.image = null;
            this.isNormalized = false;
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getContact() {
        return contact;
    }

    public String getBirth() {
        return birth;
    }

    public String getImage() {
        return image;
    }

    public boolean isNormalized() {
        return isNormalized;
    }
}
