package com.example.tonflicks.client;

import java.util.List;

public class CartRequest {
    private long userId;
    private List<TicketRequest> tickets;

    public CartRequest(long userId, List<TicketRequest> tickets) {
        this.userId = userId;
        this.tickets = tickets;
    }
}
