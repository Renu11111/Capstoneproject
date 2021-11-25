package com.app.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.myapplication.model.Review;
import com.app.myapplication.R;

import java.util.List;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewViewHolder>  {
        Context context;
        List<Review> reviews;

public ReviewListAdapter(Context context, List<Review> reviews){
        this.context = context;
        this.reviews = reviews;
        }

@NonNull
@Override
public ReviewListAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_review,parent,false);
        return new ReviewListAdapter.ReviewViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull ReviewListAdapter.ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.txt1.setText(review.review_text);
        holder.txt2.setText("Review By: " + review.emailid);
        holder.ratingBar.setRating((float) review.rating);
        }

@Override
public int getItemCount() {
        return reviews.size();
        }

public class ReviewViewHolder extends RecyclerView.ViewHolder{

    TextView txt1,txt2;
    RatingBar ratingBar;

    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        txt1 = itemView.findViewById(R.id.txt_review_text);
        txt2 = itemView.findViewById(R.id.txt_review_by);
        ratingBar = itemView.findViewById(R.id.rating);
    }
}
}
