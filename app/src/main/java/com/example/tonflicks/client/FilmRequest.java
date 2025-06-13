package com.example.tonflicks.client;


public class FilmRequest {
    private String title;
    private String genre;
    private String description;
    private String imageResId;
    private float rating;
    private int year;
    private String releaseDate;
    private int cinemaId;

    public FilmRequest(String title, String genre, String description, String imageResId,
                       float rating, int year, String releaseDate, int cinemaId) {
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.imageResId = imageResId;
        this.rating = rating;
        this.year = year;
        this.releaseDate = releaseDate;
        this.cinemaId = cinemaId;
    }

    // Геттеры и сеттеры (если нужны)
}
