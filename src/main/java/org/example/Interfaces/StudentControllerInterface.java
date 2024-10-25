package org.example.Interfaces;

import java.sql.SQLException;

public interface StudentControllerInterface {
    void displayMenu();
    void Login() throws SQLException;
    void registerStudent();
    void studentOperations();
    void searchBook();
    void getReview();
    void addReview() throws SQLException;
    void viewBooks();
    void likeBook() throws SQLException;
}
