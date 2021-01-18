package com.example.helper;

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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private final Context context;
    ArrayList<Items> Examplelist;

    private onItemClickListener mListener;

    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public void setOnClickListener(onItemClickListener listener){
        mListener = listener;
    }

    public MyAdapter(Context context, ArrayList<Items> items) {
        this.context = context;
        this.Examplelist = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Items currentItem = Examplelist.get(position);
        holder.textView1.setText(currentItem.getChannelTitle());
        holder.textView.setText(currentItem.getTitle());
        Glide.with(context).load(currentItem.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return Examplelist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView,textView1;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView,onItemClickListener listener) {
            super(itemView);

            textView = itemView.findViewById(R.id.row_title);
            textView1 = itemView.findViewById(R.id.row_discription);
            imageView = itemView.findViewById(R.id.row_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }


}
