package com.example.ramil.myuniversity.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UsersAccount implements Parcelable {
    private String username;
    private String group;
    private String faculty;
    private String photo;
    private String website;

    public UsersAccount() {
    }

    public UsersAccount(String username, String group, String faculty,
                        String photo, String website) {
        this.username = username;
        this.group = group;
        this.faculty = faculty;
        this.photo = photo;
        this.website = website;
    }

    protected UsersAccount(Parcel in) {
        username = in.readString();
        group = in.readString();
        faculty = in.readString();
        photo = in.readString();
        website = in.readString();
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
        parcel.writeString(group);
        parcel.writeString(faculty);
        parcel.writeString(photo);
        parcel.writeString(website);
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
