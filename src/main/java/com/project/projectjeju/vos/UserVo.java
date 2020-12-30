package com.project.projectjeju.vos;

public class UserVo {
    private final int index;
    private final String email;
    private final String name;
    private final String nickname;
    private final String contact;
    private final String birth;
    private final int level;

    public UserVo(int index, String email, String name, String nickname, String contact, String birth, int level) {
        this.index = index;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.contact = contact;
        this.birth = birth;
        this.level = level;
    }

    public int getIndex() {
        return index;
    }

    public String getEmail() {
        return email;
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

    public int getLevel() {
        return level;
    }
}
