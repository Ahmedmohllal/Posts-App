package com.example.postsapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.postsapp.R;
import com.example.postsapp.pojo.Photo;

import java.util.ArrayList;

public class PhotoRecyclerAdapter extends RecyclerView.Adapter<PhotoRecyclerAdapter.PhotoViewHolder> {
    ArrayList<Photo> arrayList = new ArrayList<>();
    private Context mContext;

    public PhotoRecyclerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhotoRecyclerAdapter.PhotoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        holder.album_text.setText(arrayList.get(position).getAlbumId()+"");
        holder.id_text.setText(arrayList.get(position).getId()+"");
        holder.title_text.setText(arrayList.get(position).getTitle()+"");
        String url = arrayList.get(position).getThumbnailUrl();
        Glide.with(mContext)
                .load(url)
                .apply(RequestOptions.centerCropTransform())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setArrayList(ArrayList<Photo> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        TextView album_text,id_text,title_text;
        ImageView imageView;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            album_text = itemView.findViewById(R.id.albumId_view);
            id_text = itemView.findViewById(R.id.iD_view);
            title_text = itemView.findViewById(R.id.title_view);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
