package com.example.ramil.myuniversity.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.ramil.myuniversity.BR;

public class User extends BaseObservable implements Parcelable {

    private String uid;
    private String username;
    private String email;
    private long phone;
    private String gender;
    private String group;
    private String faculty;
    private String photo;
    private String website;
    private String aboutme;

    public User() {
    }

    public User(String uid, String username, String email, String group) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.phone = 1;
        this.gender = "Не указано";
        this.group = group;
        this.faculty = "";
        this.photo = "";
        this.website = "";
        this.aboutme = "";
    }

    public User(String uid, String username, String email, long phone, String gender,
                String group, String faculty, String photo, String website, String aboutme) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.group = group;
        this.faculty = faculty;
        this.photo = photo;
        this.website = website;
        this.aboutme = aboutme;
    }

    protected User(Parcel in) {
        uid = in.readString();
        username = in.readString();
        email = in.readString();
        phone = in.readLong();
        gender = in.readString();
        group = in.readString();
        faculty = in.readString();
        photo = in.readString();
        website = in.readString();
        aboutme = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uid);
        parcel.writeString(username);
        parcel.writeString(email);
        parcel.writeLong(phone);
        parcel.writeString(gender);
        parcel.writeString(group);
        parcel.writeString(faculty);
        parcel.writeString(photo);
        parcel.writeString(website);
        parcel.writeString(aboutme);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
        notifyPropertyChanged(BR.gender);
    }

    @Bindable
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
        notifyPropertyChanged(BR.group);
    }

    @Bindable
    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
        notifyPropertyChanged(BR.faculty);
    }

    @Bindable
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
        notifyPropertyChanged(BR.photo);
    }

    @Bindable
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
        notifyPropertyChanged(BR.website);
    }

    @Bindable
    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
        notifyPropertyChanged(BR.aboutme);
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", gender='" + gender + '\'' +
                ", group='" + group + '\'' +
                ", faculty='" + faculty + '\'' +
                ", photo='" + photo + '\'' +
                ", website='" + website + '\'' +
                ", aboutme='" + aboutme + '\'' +
                '}';
    }
}
