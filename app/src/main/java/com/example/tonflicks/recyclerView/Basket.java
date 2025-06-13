package com.example.tonflicks.recyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Basket {
    public static class Ticket {
        private String id;
        private String movieTitle;
        private String posterUrl;
        private String cinemaName;
        private String cinemaAddress;
        private String time;
        private String hall;
        private String ticketCode;
        private double price;
        private Date date;

        public Ticket(String id, String movieTitle, String posterUrl, String cinemaName,
                      String cinemaAddress, String time, String hall, String ticketCode,
                      double price, Date date) {
            this.id = id;
            this.movieTitle = movieTitle;
            this.posterUrl = posterUrl;
            this.cinemaName = cinemaName;
            this.cinemaAddress = cinemaAddress;
            this.time = time;
            this.hall = hall;
            this.ticketCode = ticketCode;
            this.price = price;
            this.date = date;
        }

        // Getters
        public String getId() { return id; }
        public String getMovieTitle() { return movieTitle; }
        public String getPosterUrl() { return posterUrl; }
        public String getCinemaName() { return cinemaName; }
        public String getCinemaAddress() { return cinemaAddress; }
        public String getTime() { return time; }
        public String getHall() { return hall; }
        public String getTicketCode() { return ticketCode; }
        public double getPrice() { return price; }
        public Date getDate() { return date; }
    }

    private List<Ticket> tickets = new ArrayList<>();
    private double totalPrice = 0.0;

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
        totalPrice += ticket.getPrice();
    }

    public boolean removeTicket(String ticketId) {
        for (Ticket ticket : tickets) {
            if (ticket.getId().equals(ticketId)) {
                tickets.remove(ticket);
                totalPrice -= ticket.getPrice();
                return true;
            }
        }
        return false;
    }

    public List<Ticket> getTickets() {
        return new ArrayList<>(tickets);
    }

    public void clear() {
        tickets.clear();
        totalPrice = 0.0;
    }

    public boolean isEmpty() {
        return tickets.isEmpty();
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}