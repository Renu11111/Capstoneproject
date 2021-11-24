package com.example.myapplication.model;

public class Review {
    public String emailid;
    public String name;
    public String review_text;
    public double rating;

    public Review(String emailid, String name, String review_text, double rating) {
        this.emailid = emailid;
        this.name = name;
        this.review_text = review_text;
        this.rating = rating;
    }

    public Review() {
    }
}
