package com.example.tonflicks.client;

public class FilmParameterRequest {
    public String address;
    public String category;
    public String date;

    public FilmParameterRequest(String address, String category, String date) {
        this.address = address;
        this.category = category;
        this.date = date;
    }
}
