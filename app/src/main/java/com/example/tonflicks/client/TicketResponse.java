package com.example.tonflicks.client;

import com.google.gson.annotations.SerializedName;

public class TicketResponse {

    @SerializedName("ticketId")
    private long ticketId;

    @SerializedName("filmTitle")
    private String filmTitle;

    @SerializedName("posterUrl")
    private String posterUrl;

    @SerializedName("cinemaName")
    private String cinemaName;

    @SerializedName("cinemaAddress")
    private String cinemaAddress;

    @SerializedName("time")
    private String time;

    @SerializedName("hallName")
    private String hallName;

    @SerializedName("ticketCode")
    private String ticketCode;

    @SerializedName("price")
    private double price;

    // --- Геттеры и сеттеры ---

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public void setFilmTitle(String filmTitle) {
        this.filmTitle = filmTitle;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getCinemaAddress() {
        return cinemaAddress;
    }

    public void setCinemaAddress(String cinemaAddress) {
        this.cinemaAddress = cinemaAddress;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
