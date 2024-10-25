package org.example.Model;

import java.time.LocalDate;

public abstract class User {
    private String firstName;
    private String lastName;
    private int age;
    private LocalDate dob;
    private String gender;
    private String emailId;
    private long mobile;
    private String username;
    private String password;
    private String role;

    public User(String firstName, String role, String password, String username, long mobile, String emailId, String gender, LocalDate dob, int age, String lastName) {
        this.firstName = firstName;
        this.role = role;

        this.password = password;
        this.username = username;
        this.mobile = mobile;
        this.emailId = emailId;
        this.gender = gender;
        this.dob = dob;
        this.age = age;
        this.lastName = lastName;
    }

    public User() {

    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }



    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
