package com.example.postsapp.ui;

import com.example.postsapp.pojo.Comment;
import com.example.postsapp.pojo.Photo;
import com.example.postsapp.pojo.Post;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {
    @GET("posts")
    Observable<ArrayList<Post>> getPost();

    @GET("comments")
    Observable<ArrayList<Comment>> getComment();

    @GET("photos")
    Call<ArrayList<Photo>> getPhoto();
}
