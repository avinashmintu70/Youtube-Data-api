package com.example.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterMain extends RecyclerView.Adapter<AdapterMain.MyViewHolderMain> {

    private Context context;
    private ArrayList<String> querry;

    private MyAdapter.onItemClickListener mListener;

    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public void setOnClickListener(MyAdapter.onItemClickListener listener){
        mListener = listener;
    }



    public AdapterMain(Context context, ArrayList<String> querry) {
        this.context = context;
        this.querry = querry;
    }

    @NonNull
    @Override
    public MyViewHolderMain onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_main,parent,false);
        return new MyViewHolderMain(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderMain holder, int position) {
        holder.textView.setText(querry.get(position));
    }

    @Override
    public int getItemCount() {
        return querry.size();
    }

    public static class MyViewHolderMain extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolderMain(@NonNull View itemView, MyAdapter.onItemClickListener listener) {
            super(itemView);

            textView = itemView.findViewById(R.id.course_name);
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
