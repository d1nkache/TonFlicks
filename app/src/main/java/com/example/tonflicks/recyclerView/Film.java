package com.example.tonflicks.recyclerView;

import com.example.tonflicks.client.Screening;

import java.util.List;

public class Film {
    private String title;
    private String genre;
    private String description;
    private int imageResId;
    private float rating;
    private int year;

    public Film(String title, String genre, String description, int imageResId) {
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.imageResId = imageResId;
    }

    public Film(String title, String genre, String description, int imageResId, float rating, int year) {
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.imageResId = imageResId;
        this.rating = rating;
        this.year = year;
    }

    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public String getDescription() { return description; }
    public int getImageResId() { return imageResId; }
    public float getRating() { return rating; }
    public int getYear() { return year; }
    public void setTitle(String title) { this.title = title; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setDescription(String description) { this.description = description; }
    public void setImageResId(int imageResId) { this.imageResId = imageResId; }
    public void setRating(float rating) { this.rating = rating; }
    public void setYear(int year) { this.year = year; }

    public String getShortInfo() {
        return title + " (" + year + ") - " + genre;
    }
}