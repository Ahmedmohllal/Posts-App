package com.example.postsapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.postsapp.pojo.Comment;
import com.example.postsapp.pojo.Post;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Dao
public interface PostsDao {
    @Insert
    Completable insertPost(Post post);

    @Query("select * from posts_table")
    Observable<List<Post>> getAllPosts();

    @Insert
    Completable insertComment(Comment comment);

    @Query("select * from comment_table")
    Observable<List<Comment>> getAllComments();
}
