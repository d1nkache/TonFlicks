package com.example.tonflicks.recyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
import com.example.tonflicks.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {

    private List<Basket.Ticket> tickets;

    public BasketAdapter(List<Basket.Ticket> tickets) {
        this.tickets = tickets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.basket_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Basket.Ticket ticket = tickets.get(position);
        holder.tvMovieTitle.setText(ticket.getMovieTitle());
        holder.tvCinema.setText(ticket.getCinemaName() + ", " + ticket.getCinemaAddress());
        holder.tvDateTime.setText(formatDateTime(ticket.getDate(), ticket.getTime()));
        holder.tvHall.setText("Зал: " + ticket.getHall());
        holder.tvCode.setText("Код билета: " + ticket.getTicketCode());

//        Glide.with(holder.itemView.getContext())
//                .load(ticket.getPosterUrl())
//                .placeholder(R.drawable.poster_placeholder)
//                .into(holder.ivPoster);
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvMovieTitle, tvCinema, tvDateTime, tvHall, tvCode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            tvMovieTitle = itemView.findViewById(R.id.tvTitle);
            tvCinema = itemView.findViewById(R.id.tvLocation);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvHall = itemView.findViewById(R.id.tvHall);
            tvCode = itemView.findViewById(R.id.tvTicketCode);
        }
    }

    private String formatDateTime(java.util.Date date, String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        return sdf.format(date) + " в " + time;
    }
}
