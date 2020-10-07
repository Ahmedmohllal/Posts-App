package com.example.postsapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.postsapp.pojo.Comment;
import com.example.postsapp.pojo.Post;

@Database(entities={Post.class, Comment.class}, version = 2)
public abstract class PostsDatabase extends RoomDatabase {
    private static PostsDatabase instance;
    public abstract PostsDao postsDao();

    public static synchronized PostsDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext()
                    ,PostsDatabase.class,"post_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
