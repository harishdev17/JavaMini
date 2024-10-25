package org.example.Service;

import org.example.Model.Book;
import org.example.Model.Quiz;
import org.example.Model.Review;
import org.example.Model.Student;
import org.example.Util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private final Connection connection;

    public StudentService() throws SQLException {
       connection= DBConnection.getConnection();
    }

    public boolean registerStudent(Student student) throws SQLException {
        String sql = "INSERT INTO students (firstName, lastName, age, dob, gender, emailId, mobile, username, password, role, points) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'STUDENT', ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setInt(3, student.getAge());
            pstmt.setDate(4, Date.valueOf(student.getDob()));
            pstmt.setString(5, student.getGender());
            pstmt.setString(6, student.getEmailId());
            pstmt.setLong(7, student.getMobile());
            pstmt.setString(8, student.getUsername());
            pstmt.setString(9, student.getPassword());
            pstmt.setInt(10, student.getPoints());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    public List<Quiz> MachineLearningQuiz() throws SQLException {
        List<Quiz>listOfQuiz=new ArrayList<>();
        String sql = "SELECT * FROM ML ORDER BY RAND() LIMIT ?";
        try(PreparedStatement ps=connection.prepareStatement(sql)){
            ps.setInt(1,10);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Quiz quiz=new Quiz();
                quiz.setQuestion_text(rs.getString("question_text"));
                quiz.setOptionA(rs.getString("optionA"));
                quiz.setOptionB(rs.getString("optionB"));
                quiz.setOptionC(rs.getString("optionC"));
                quiz.setOptionD(rs.getString("optionD"));
                quiz.setCorrect_answer(rs.getString("correct_answer"));
                listOfQuiz.add(quiz);
            }
        } catch (SQLException E){
            System.out.println(E);
        }
        return listOfQuiz;
    }
    public boolean login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM students WHERE username = ? AND password = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public Student getStudentByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM students WHERE username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getInt("age"),
                            rs.getDate("dob").toLocalDate(),
                            rs.getString("gender"),
                            rs.getString("emailId"),
                            rs.getLong("mobile"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getInt("points")
                    );
                }
            }
        }
        return null;
    }
    public List<Book> getAllBooks(){
        List<Book> books=new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                books.add(new Book(
                        resultSet.getInt("bookid"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("isPremium"),
                        resultSet.getString("domain"),
                        resultSet.getString("shortSummary"),
                        resultSet.getString("longSummary"),
                        resultSet.getInt("pointsRequired")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public void addReview(Review review) throws SQLException {
        String sql = "INSERT INTO review (reviewer_name, book_title, review_text, rating, book_id) VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement ps=connection.prepareStatement(sql)){
            ps.setString(1,review.getReviewerName());
            ps.setString(2,review.getBookTitle());
            ps.setString(3,review.getReviewText());
            ps.setInt(4,review.getRating());
            ps.setInt(5,review.getBookId());
            ps.executeUpdate();
        }
        catch (SQLException E){
            E.printStackTrace();
        }
    }

    public void likeBook(String title) throws SQLException {
        String sql = "UPDATE books SET likes = likes + 1 WHERE title = ?";
        String selectSql = "SELECT likes FROM books WHERE title = ?";
        try(PreparedStatement ps=connection.prepareStatement(sql);
        PreparedStatement selectLike=connection.prepareStatement(selectSql)){
            ps.setString(1,title);
            ps.executeUpdate();
            selectLike.setString(1,title);
            ResultSet rs=selectLike.executeQuery();
            if(rs.next()){
                int newLikes=rs.getInt("likes");
                System.out.println("Book \"" + title + "\" now has " + newLikes + " likes.");
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Review> getReviews(String title) {
            List<Review> reviews = new ArrayList<>();
            String sql = "SELECT * FROM review WHERE book_title = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1,title);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    reviews.add(new Review(
                            resultSet.getInt("book_id"),
                            resultSet.getString("reviewer_name"),
                            resultSet.getString("review_text"),
                            resultSet.getInt("rating"),
                            resultSet.getString("book_title")
                    ));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return reviews;

    }

    public void updatePoints(String username, int points) throws SQLException {
        String sql="UPDATE students SET points=points + ? WHERE username=?";
        try(PreparedStatement ps=connection.prepareStatement(sql)){
            ps.setInt(1,points);
            ps.setString(2,username);
            ps.executeUpdate();
        }
        catch (Exception E){
            System.out.println(E);
        }
    }
}