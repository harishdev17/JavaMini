package org.example.Controller;

import org.example.Model.Book;
import org.example.Model.Quiz;
import org.example.Model.Review;
import org.example.Model.Student;
import org.example.Service.StudentService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import org.example.Interfaces.StudentControllerInterface;
public class StudentController implements StudentControllerInterface {
    private final StudentService studentService;
    private Student loggedinStudent;

    public StudentController() throws SQLException {
        studentService = new StudentService();
    }

    public void displayMenu() {
        Scanner s = new Scanner(System.in);
        int choice;
        System.out.println("Welcome to E-library(Student):");
        System.out.println("1)Register");
        System.out.println("2)Login");
        System.out.println("3)Exit");
        try {
            System.out.println("Enter the choice:");
            choice = s.nextByte();
            switch (choice) {
                case 1:
                    registerStudent();
                    break;
                case 2:
                    Login();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    displayMenu();
            }
        } catch (InputMismatchException | SQLException E) {
            System.out.println(E);
        }
    }

    public void Login() throws SQLException {
        Scanner s = new Scanner(System.in);
        System.out.print("Username: ");
        String username = s.next();
        System.out.print("Password: ");
        String password = s.next();

        if (studentService.login(username, password)) {
            System.out.println("Login successful!");
            loggedinStudent = studentService.getStudentByUsername(username);
            studentOperations();
        } else {
            System.out.println("Invalid credentials. Please try again.");
            displayMenu();
        }
    }

    public void registerStudent() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Registering a new student");
            System.out.print("First Name: ");
            String firstName = scanner.next();
            System.out.print("Last Name: ");
            String lastName = scanner.next();
            System.out.print("Age: ");
            int age = scanner.nextInt();
            System.out.print("DOB (YYYY-MM-DD): ");
            String dob = scanner.next();
            System.out.print("Gender: ");
            String gender = scanner.next();
            System.out.print("Email ID: ");
            String emailId = scanner.next();
            System.out.print("Mobile Number: ");
            long mobile = scanner.nextLong();
            System.out.print("Username: ");
            String username = scanner.next();
            System.out.print("Password: ");
            String password = scanner.next();
            int points = 0;
            LocalDate dateOfBirth = null;
            try {
                dateOfBirth = LocalDate.parse(dob);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                return;
            }
            Student student = new Student(firstName, lastName, age, dateOfBirth, gender, emailId, mobile, username, password, points);
            if (studentService.registerStudent(student)) {
                System.out.println("Registration successful! You can now log in.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter the details correctly.");
            scanner.next();
        } catch (Exception e) {
            System.out.println("An error occurred during registration: " + e.getMessage());
        }
        displayMenu();
    }

    public void studentOperations() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome Student! Select an operation:");
        System.out.println("1. View Books");
        System.out.println("2. Add Review");
        System.out.println("3. Like a Book");
        System.out.println("4. Get Reviews for a Book");
        System.out.println("5. Search for a Book");
        System.out.println("6. Take a Quiz");
        System.out.println("7. Logout");

        try {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    viewBooks();
                    break;
                case 2:
                    addReview();
                    break;
                case 3:
                    likeBook();
                    break;
                case 4:
                    getReview();
                     break;
                case 5:
                    searchBook();
                    break;
                case 6:
                    quiz();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    studentOperations();
            }
        } catch (InputMismatchException | SQLException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
            studentOperations();
        }

    }
    public void quiz() throws SQLException {
        Scanner s=new Scanner(System.in);
        System.out.println("Select Your Preferred Domain:");
        System.out.println("1)Machine Learning");
        try{
            int choice=s.nextByte();
            switch (choice){
                case 1:
                    MachineLearningQuiz();
                    break;
                default:
                    System.out.println("Invalid Choice:");
                    studentOperations();
            }
        }
        catch (SQLException E){
            System.out.println(E);
        }
    }
    public void MachineLearningQuiz() throws SQLException {
        List<Quiz>listOfQuiz=studentService.MachineLearningQuiz();
        int count=1;
        int points=0;
        String option;
        Scanner s=new Scanner(System.in);
        for(Quiz list:listOfQuiz){
            System.out.println(count+")"+list.getQuestion_text());
            System.out.println("A) "+list.getOptionA());
            System.out.println("B) "+list.getOptionB());
            System.out.println("C) "+list.getOptionC());
            System.out.println("D) "+list.getOptionD());
            System.out.println("Enter Your Option:");
            option= String.valueOf(s.nextLine().charAt(0));
            if(option.equals(list.getCorrect_answer())){
                System.out.println("Yeah,You Got it Right");
                points++;
            }
            else{
                System.out.println("Oops,Wrong Answer.The Correct Option is "+list.getCorrect_answer());
            }
            count++;
        }
        if(points!=0){
            studentService.updatePoints(loggedinStudent.getUsername(),points);
        }
        System.out.println("Total Points Acquired:"+points);
        studentOperations();
    }
    public void searchBook() {
        Scanner scanner=new Scanner(System.in);
        String title;
        boolean found=false;
        Book book = null;
        System.out.println("Enter the book title:");
        title=scanner.nextLine();
        List<Book> books=studentService.getAllBooks();
        for(Book booki:books){
            if(booki.getTitle().equals(title)){
                found=true;
                book=booki;
            }
        }
        if(found){
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("Domain: " + book.getDomain());
            System.out.println("Points Required: " + book.getPointsRequired());
            System.out.println("Short Summary: " + book.getShortSummary());
            System.out.println("Long Summary: " + book.getLongSummary());
        }
        else{
            System.out.println("Book Not found");
        }
    }

    public void getReview() {
        Scanner s=new Scanner(System.in);
        String title;
        System.out.println("Enter the title of the book:");
        title=s.nextLine();
        boolean found=false;
        List<Book> books=studentService.getAllBooks();
        for(Book book:books){
            if(book.getTitle().equals(title)){
                found=true;
            }
        }
        if(found){
            List<Review>reviews=studentService.getReviews(title);
            for(Review review:reviews){
                System.out.println("Book Title:"+review.getBookTitle());
                System.out.println("Reviewer Name:"+review.getReviewerName());
                System.out.println("Content:"+review.getReviewText());
                System.out.println("Rating:"+review.getRating());
            }
        }
        else{
            System.out.println("Book Not found");
        }

    }

    public void addReview() throws SQLException {
        Scanner scanner=new Scanner(System.in);
        String title;
        boolean found=false;
        System.out.println("Enter the book title:");
        title=scanner.nextLine();
        List<Book> books=studentService.getAllBooks();
        for(Book book:books){
            if(book.getTitle().equals(title)){
                found=true;
            }
        }
        if(found){
            System.out.print("Book ID: ");
            int bookId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Review Content: ");
            String reviewContent = scanner.nextLine();
            System.out.print("Rating (1-5): ");
            int rating = scanner.nextInt();

            Review review = new Review(bookId,loggedinStudent.getUsername(),reviewContent, rating,title);
            studentService.addReview(review);
            System.out.println("Review Added Successfully");
        }
        else{
            System.out.println("Book Not found");
        }
    }

    public void viewBooks(){
    List<Book> books=studentService.getAllBooks();
        if (books == null || books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            System.out.println("Available Books:");
            for (Book book : books) {
                System.out.println("Title: " + book.getTitle());
                System.out.println("Author: " + book.getAuthor());
                System.out.println("Domain: " + book.getDomain());
                System.out.println("Points Required: " + book.getPointsRequired());
                System.out.println("Short Summary: " + book.getShortSummary());
                System.out.println("Long Summary: " + book.getLongSummary());
            }
        }
        studentOperations();
    }
    public void likeBook() throws SQLException {
        Scanner s=new Scanner(System.in);
        String title;
        System.out.println("Enter the title:");
        title=s.nextLine();
        boolean found=false;
        List<Book> books=studentService.getAllBooks();
        for(Book book:books){
            if(book.getTitle().equals(title)){
                found=true;
            }
        }
        if(found){
            studentService.likeBook(title);
            System.out.println("Book is liked");
        }
        else{
            System.out.println("Book Not Found");
        }
        studentOperations();
    }
}