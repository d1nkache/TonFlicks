package com.example.tonflicks.client;

public class Seat {
    private String seatNumber;
    private int price;

    public Seat(String seatNumber, int price) {
        this.seatNumber = seatNumber;
        this.price = price;
    }

    public String getSeatNumber() { return seatNumber; }
    public int getPrice() { return price; }
}
