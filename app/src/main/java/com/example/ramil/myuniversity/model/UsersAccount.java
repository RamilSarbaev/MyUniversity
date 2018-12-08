package com.example.ramil.myuniversity.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UsersAccount implements Parcelable {
    private String username;
    private String gender;
    private String group;
    private String faculty;
    private String photo;
    private String website;
    private String aboutme;

    public UsersAccount() {
    }

    public UsersAccount(String username, String gender, String group, String faculty, String photo, String website, String aboutme) {
        this.username = username;
        this.gender = gender;
        this.group = group;
        this.faculty = faculty;
        this.photo = photo;
        this.website = website;
        this.aboutme = aboutme;
    }

    protected UsersAccount(Parcel in) {
        username = in.readString();
        gender = in.readString();
        group = in.readString();
        faculty = in.readString();
        photo = in.readString();
        website = in.readString();
        aboutme = in.readString();
    }

    public static final Creator<UsersAccount> CREATOR = new Creator<UsersAccount>() {
        @Override
        public UsersAccount createFromParcel(Parcel in) {
            return new UsersAccount(in);
        }

        @Override
        public UsersAccount[] newArray(int size) {
            return new UsersAccount[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(gender);
        parcel.writeString(group);
        parcel.writeString(faculty);
        parcel.writeString(photo);
        parcel.writeString(website);
        parcel.writeString(aboutme);
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }

    @Override
    public String toString() {
        return "UsersAccount{" +
                "username='" + username + '\'' +
                ", gender='" + gender + '\'' +
                ", group='" + group + '\'' +
                ", faculty='" + faculty + '\'' +
                ", photo='" + photo + '\'' +
                ", website='" + website + '\'' +
                ", aboutme='" + aboutme + '\'' +
                '}';
    }
}
