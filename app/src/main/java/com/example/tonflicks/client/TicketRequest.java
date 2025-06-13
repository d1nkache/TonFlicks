package com.example.tonflicks.client;

public class TicketRequest {
    private long screeningId;
    private String seatNumber;
    private int price;

    public TicketRequest(long screeningId, String seatNumber, int price) {
        this.screeningId = screeningId;
        this.seatNumber = seatNumber;
        this.price = price;
    }
}
