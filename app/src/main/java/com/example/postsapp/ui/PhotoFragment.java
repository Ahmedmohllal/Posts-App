package com.example.postsapp.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.postsapp.R;
import com.example.postsapp.pojo.Photo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PhotoFragment extends Fragment {

    JsonPlaceHolderApi jsonPlaceHolderApi;
    RecyclerView recyclerView;
    PhotoRecyclerAdapter photoRecyclerAdapter;
    PostsViewModel postsViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.photo_rec);
        photoRecyclerAdapter = new PhotoRecyclerAdapter(getContext());
        recyclerView.setAdapter(photoRecyclerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        postsViewModel = new PostsViewModel();

        getPhoto();
        return view;
    }

    private void getPhoto() {
        Call<ArrayList<Photo>> call = jsonPlaceHolderApi.getPhoto();
        call.enqueue(new Callback<ArrayList<Photo>>() {
            @Override
            public void onResponse(Call<ArrayList<Photo>> call, Response<ArrayList<Photo>> response) {
                ArrayList<Photo> arrayList = response.body();
                postsViewModel.setPhotoMutableLiveData(arrayList);
                postsViewModel.photoMutableLiveData.observe(getActivity(), new Observer<ArrayList<Photo>>() {
                    @Override
                    public void onChanged(ArrayList<Photo> photos) {
                        photoRecyclerAdapter.setArrayList(arrayList);
                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Photo>> call, Throwable t) {

            }
        });
    }
}