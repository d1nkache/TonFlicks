package com.example.tonflicks.client;

import java.util.List;

public class FilmResponse {
    public Long id;
    public String title;
    public String genre;
    public String description;
    public int imageResId;
    public float rating;
    public int year;
    public String releaseDate;
    public String cinemaName;
    public List<Screening> screenings;
}
