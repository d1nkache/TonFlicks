package com.example.tonflicks.client;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CartResponse {

    @SerializedName("cartId")
    private long cartId;

    @SerializedName("userId")
    private long userId;

    @SerializedName("createdAt")
    private String createdAt; // ISO-8601, например: "2025-06-10T14:55:00"

    @SerializedName("tickets")
    private List<TicketResponse> tickets;

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<TicketResponse> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketResponse> tickets) {
        this.tickets = tickets;
    }
}
