package com.example.jsonexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MovieHolder> {

    Context mctx;
    ArrayList<MovieModel> movieslist;

    public MoviesAdapter(Context mctx, ArrayList<MovieModel> movieslist) {
        this.mctx = mctx;
        this.movieslist = movieslist;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,parent,false);

        return new MovieHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {

        holder.mName.setText(movieslist.get(holder.getAdapterPosition()).getMovie_name());
        holder.mYear.setText(movieslist.get(holder.getAdapterPosition()).getYear());

        //get image from glide

        Glide.with(mctx)
                .load(movieslist.get(holder.getAdapterPosition()).getImgurl()).into(holder.mImage);


    }

    @Override
    public int getItemCount() {
        return movieslist.size();
    }
}

class MovieHolder extends RecyclerView.ViewHolder{

    TextView mName, mYear;
    ImageView mImage;
    public MovieHolder(@NonNull View itemView) {
        super(itemView);

        mName=itemView.findViewById(R.id.movie_name);
        mYear=itemView.findViewById(R.id.year);
        mImage=itemView.findViewById(R.id.movie_img);

    }
}
