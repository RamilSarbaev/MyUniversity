package com.example.ramil.myuniversity.model;

public class UsersAccount {
    private String username;
    private String group;
    private String faculty;
    private String photo;
    private String website;

    public UsersAccount() {
    }

    public UsersAccount(String username, String group, String faculty, String photo, String website) {
        this.username = username;
        this.group = group;
        this.faculty = faculty;
        this.photo = photo;
        this.website = website;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return "UsersAccount{" +
                "username='" + username + '\'' +
                ", group='" + group + '\'' +
                ", faculty='" + faculty + '\'' +
                ", photo='" + photo + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
