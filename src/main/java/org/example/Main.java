package org.example;

import org.example.Controller.StudentController;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        StudentController studentController=new StudentController();
        studentController.displayMenu();
    }
}