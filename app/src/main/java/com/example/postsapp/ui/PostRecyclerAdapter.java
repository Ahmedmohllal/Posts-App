package com.example.postsapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postsapp.R;
import com.example.postsapp.pojo.Post;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.PostViewHolder> {
    ArrayList<Post> arrayList = new ArrayList<>();

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.userId_Text.setText(arrayList.get(position).getUserId()+"");
        holder.id_Text.setText(arrayList.get(position).getId()+"");
        holder.title_Text.setText(arrayList.get(position).getTitle());
        holder.body_Text.setText(arrayList.get(position).getBody());

    }
    //set all data to arraylist
    public void setList(ArrayList<Post> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView userId_Text,id_Text,title_Text,body_Text;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            userId_Text = itemView.findViewById(R.id.userid_view);
            id_Text = itemView.findViewById(R.id.id_view);
            title_Text = itemView.findViewById(R.id.title_view);
            body_Text = itemView.findViewById(R.id.body_view);
        }
    }
}
