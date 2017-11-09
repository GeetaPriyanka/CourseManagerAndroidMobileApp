package com.example.group19_hw06.Model;

import java.util.Arrays;

/**
 * Created by vikhy on 11/2/2017.
 */

public class Instructor {
    String firstName;
    String lastName;
    String email;
    String personalWebsite;
    String username;

    @Override
    public String toString() {
        return "Instructor{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", personalWebsite='" + personalWebsite + '\'' +
                ", username='" + username + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Instructor that = (Instructor) o;

        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null)
            return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null)
            return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (personalWebsite != null ? !personalWebsite.equals(that.personalWebsite) : that.personalWebsite != null)
            return false;
        if (username != null ? !username.equals(that.username) : that.username != null)
            return false;
        return Arrays.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (personalWebsite != null ? personalWebsite.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    byte[] image;
public Instructor(){

}
    public Instructor(String firstName, String lastName, String email, String personalWebsite, byte[] image) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.personalWebsite = personalWebsite;
        this.image = image;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPersonalWebsite() {
        return personalWebsite;
    }

    public byte[] getImage() {
        return image;
    }

}
