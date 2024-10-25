

package org.example.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private List<Book> personalLibrary;
    private List<Book> readingHistory;
    private List<Review> reviews;
    private int points;


    public Student(String firstName, String role, String password, String username, long mobile, String emailId, String gender, LocalDate dob, int age, String lastName) {
        super(firstName, role, password, username, mobile, emailId, gender, dob, age, lastName);
        this.personalLibrary = new ArrayList<>();
        this.readingHistory = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.points = 0;
    }


    public Student(String firstName, String lastName, int age, LocalDate dob, String gender, String emailId, long mobileNumber, String username, String password, int points) {
        super(firstName, "STUDENT", password, username, mobileNumber, emailId, gender, dob, age, lastName);
        this.personalLibrary = new ArrayList<>();
        this.readingHistory = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.points = points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return this.points;
    }
}