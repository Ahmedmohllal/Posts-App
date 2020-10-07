package com.example.postsapp.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.postsapp.database.PostsDatabase;
import com.example.postsapp.R;
import com.example.postsapp.pojo.Post;

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


public class PostFragment extends Fragment {


    JsonPlaceHolderApi jsonPlaceHolderApi;
    RecyclerView recyclerView;
    PostRecyclerAdapter postRecyclerAdapter;
    private static final String TAG = "PostFragment";
    private PostsViewModel postsViewModel;
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
        postsViewModel = new PostsViewModel();
        //Check network connection




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.post_rec);
        postRecyclerAdapter = new PostRecyclerAdapter();
        recyclerView.setAdapter(postRecyclerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        postsDatabase = PostsDatabase.getInstance(getActivity());
        ConnectivityManager cm = (ConnectivityManager)getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
            getPosts();
        } else {
            retriveDataFromRoom();
        }
        return view;

    }

    private void getPosts() {
        Observable<ArrayList<Post>> observable = jsonPlaceHolderApi.getPost()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<ArrayList<Post>> observer = new Observer<ArrayList<Post>>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(ArrayList<Post> posts) {
                storeDataToRoom(posts);
                postsViewModel.setPostMutableLiveData(posts);
                postsViewModel.postMutableLiveData.observe(getActivity(), new androidx.lifecycle.Observer<ArrayList<Post>>() {
                    @Override
                    public void onChanged(ArrayList<Post> arrayList) {
                        postRecyclerAdapter.setList(arrayList);
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

    private void storeDataToRoom(ArrayList<Post> posts){
            for (int i = 0 ; i < posts.size() ; i++){
                postsDatabase.postsDao().insertPost(posts.get(i))
                        .subscribeOn(Schedulers.io())
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



    }

    private ArrayList<Post> retriveDataFromRoom(){
        final ArrayList<Post> arrayListPosts = new ArrayList<>();
        postsDatabase.postsDao().getAllPosts()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Post>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Post> posts) {
                        for (int i = 0 ; i < posts.size() ; i++){
                            arrayListPosts.add(posts.get(i));
                        }
                        postRecyclerAdapter.setList(arrayListPosts);
                        Toast.makeText(getActivity(), "Network not connected"+posts.size(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "kkk onNext: done"+posts.size());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return arrayListPosts;
    }
}