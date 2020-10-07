package com.example.postsapp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.postsapp.ui.CommentFragment;
import com.example.postsapp.ui.PhotoFragment;
import com.example.postsapp.ui.PostFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    public SectionsPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                PostFragment postFragment = new PostFragment();
                return postFragment;
            case 1:
                CommentFragment commentFragment = new CommentFragment();
                return commentFragment;
            case 2:
                PhotoFragment photoFragment = new PhotoFragment();
                return photoFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Posts";
            case 1:
                return "Comments";
            case 2:
                return "Photos";
            default:
                return null;
        }
    }
}
