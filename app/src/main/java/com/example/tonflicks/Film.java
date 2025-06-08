package com.example.tonflicks;

public class Film {
    private String title;
    private String genre;
    private int imageResId; // например, R.drawable.poster_placeholder

    public Film(String title, String genre, int imageResId) {
        this.title = title;
        this.genre = genre;
        this.imageResId = imageResId;
    }

    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public int getImageResId() { return imageResId; }
}
