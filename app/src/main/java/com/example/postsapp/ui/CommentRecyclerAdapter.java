package com.example.postsapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postsapp.R;
import com.example.postsapp.pojo.Comment;

import java.util.ArrayList;

public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.CommentViewHolder> {
    ArrayList<Comment> arrayList = new ArrayList<>();
    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new CommentRecyclerAdapter.CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.userId_text.setText(arrayList.get(position).getPostId()+"");
        holder.id_text.setText(arrayList.get(position).getId()+"");
        holder.name_text.setText(arrayList.get(position).getName());
        holder.email_text.setText(arrayList.get(position).getEmail());
        holder.body_text.setText(arrayList.get(position).getBody());

    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setArrayList(ArrayList<Comment> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView userId_text,id_text,name_text,email_text,body_text;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            userId_text = itemView.findViewById(R.id.userid_view);
            id_text = itemView.findViewById(R.id.id_view);
            name_text = itemView.findViewById(R.id.name_view);
            email_text = itemView.findViewById(R.id.email_view);
            body_text = itemView.findViewById(R.id.body_view);
        }
    }
}
