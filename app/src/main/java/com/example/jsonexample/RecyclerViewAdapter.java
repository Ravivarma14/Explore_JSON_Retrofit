package com.example.jsonexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    ArrayList<String> namelist=new ArrayList<>();
    ArrayList<String> emaillist=new ArrayList<>();
    ArrayList<String> mobilelist=new ArrayList<>();
    Context ctx;

    public RecyclerViewAdapter(Context context,ArrayList<String> n,ArrayList<String> e,ArrayList<String> m){
        ctx=context;
        namelist=n;
        emaillist=e;
        mobilelist=m;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_itemview,parent,false);
        MyViewHolder vh=new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.nametv.setText(namelist.get(holder.getAdapterPosition()));
        holder.emailtv.setText(emaillist.get(holder.getAdapterPosition()));
        holder.mobiletv.setText(mobilelist.get(holder.getAdapterPosition()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx,"name: "+ namelist.get(holder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return namelist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nametv,emailtv,mobiletv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nametv=itemView.findViewById(R.id.name);
            emailtv=itemView.findViewById(R.id.email);
            mobiletv=itemView.findViewById(R.id.mobile);

        }
    }
}
