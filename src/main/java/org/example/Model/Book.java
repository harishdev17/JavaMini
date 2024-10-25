package org.example.Model;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private int bookId;
    private String title;
    private String author;
    private int isPremium;
    private String domain;
    private String shortSummary;
    private String longSummary;
    private int likes;
    private int pointsRequired;
    private List<Review> reviews;

    public Book(int bookId, String title, String author, int isPremium, String domain, String shortSummary, String longSummary, int pointsRequired) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isPremium = isPremium;
        this.domain = domain;
        this.shortSummary = shortSummary;
        this.longSummary = longSummary;
        this.likes = 0;
        this.pointsRequired = pointsRequired;
        this.reviews = new ArrayList<>();
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int isPremium() {
        return isPremium;
    }

    public String getDomain() {
        return domain;
    }

    public String getShortSummary() {
        return shortSummary;
    }

    public String getLongSummary() {
        return longSummary;
    }

    public int getLikes() {
        return likes;
    }

    public int getPointsRequired() {
        return pointsRequired;
    }

    public void addLike() {
        this.likes++;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public void setPointsRequired(int pointsRequired) {
        this.pointsRequired = pointsRequired;
    }
}
