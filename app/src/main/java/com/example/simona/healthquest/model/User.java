package com.example.simona.healthquest.model;

/**
 * Created by Simona on 7/17/2017.
 */

public class User extends BaseEntity {
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Integer points;
    private Level level;
    private String profileImage;
    Boolean facebookAccount;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Boolean getFacebookAccount() {
        return facebookAccount;
    }

    public void setFacebookAccount(Boolean facebookAccount) {
        this.facebookAccount = facebookAccount;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
