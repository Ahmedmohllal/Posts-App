package com.example.postsapp.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.postsapp.R;
import com.example.postsapp.database.PostsDatabase;
import com.example.postsapp.pojo.Comment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentFragment extends Fragment {

    RecyclerView recyclerView;
    CommentRecyclerAdapter commentRecyclerAdapter;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    private static final String TAG = "CommentFragment";
    PostsViewModel postsViewModel;
    PostsDatabase postsDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        recyclerView = view.findViewById(R.id.comment_rec);
        commentRecyclerAdapter = new CommentRecyclerAdapter();
        recyclerView.setAdapter(commentRecyclerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        postsViewModel = new PostsViewModel();
        postsDatabase = PostsDatabase.getInstance(getActivity());
        ConnectivityManager cm = (ConnectivityManager)getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
            GetComments();
        } else {
            getAllCommentsFromRoom();
        }

        return view;
    }

    private void GetComments() {
        Observable<ArrayList<Comment>> observable = jsonPlaceHolderApi.getComment()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<ArrayList<Comment>> observer = new Observer<ArrayList<Comment>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ArrayList<Comment> comments) {
                storeCommentIntoRoom(comments);
                postsViewModel.setCommentMutableLiveData(comments);
                postsViewModel.commentMutableLiveData.observe(getActivity(), new androidx.lifecycle.Observer<ArrayList<Comment>>() {
                    @Override
                    public void onChanged(ArrayList<Comment> comments) {
                        commentRecyclerAdapter.setArrayList(comments);
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);
    }

    private void storeCommentIntoRoom(ArrayList<Comment> comments){
        for (int i = 0 ; i < comments.size() ; i++){
            postsDatabase.postsDao().insertComment(comments.get(i))
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onComplete() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });
        }
        Log.d(TAG, "qqq storeCommentIntoRoom: "+"donnnne");
    }

    private ArrayList<Comment> getAllCommentsFromRoom(){
        ArrayList<Comment> commentArrayList = new ArrayList<>();
        postsDatabase.postsDao().getAllComments()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Comment>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Comment> comments) {
                        for (int i = 0 ; i < comments.size() ; i++){
                            commentArrayList.add(comments.get(i));
                        }
                        commentRecyclerAdapter.setArrayList(commentArrayList);
                        Log.d(TAG, "rrr onNext: "+comments.size());

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return commentArrayList;
    }
}