package com.example.postsapp.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.postsapp.pojo.Comment;
import com.example.postsapp.pojo.Photo;
import com.example.postsapp.pojo.Post;

import java.util.ArrayList;

public class PostsViewModel extends ViewModel {
    MutableLiveData<ArrayList<Post>> postMutableLiveData = new MutableLiveData<>();
    MutableLiveData<ArrayList<Comment>> commentMutableLiveData = new MutableLiveData<>();
    MutableLiveData<ArrayList<Photo>> photoMutableLiveData = new MutableLiveData<>();

    private ArrayList<Post> getPost(ArrayList<Post> arrayList){
        return arrayList;
    }
    private ArrayList<Comment> getComment(ArrayList<Comment> arrayList){
        return arrayList;
    }
    private ArrayList<Photo> getPhoto(ArrayList<Photo> arrayList){
        return arrayList;
    }

    public void setPostMutableLiveData(ArrayList<Post> arrayList){
        postMutableLiveData.setValue(getPost(arrayList));
    }
    public void setCommentMutableLiveData(ArrayList<Comment> arrayList){
        commentMutableLiveData.setValue(getComment(arrayList));
    }
    public void setPhotoMutableLiveData(ArrayList<Photo> arrayList){
        photoMutableLiveData.setValue(getPhoto(arrayList));
    }
}
