package com.example.apexonnewsupdate.adapters;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;

import com.example.apexonnewsupdate.fragments.FragmentYouTube;
import com.example.apexonnewsupdate.models.OurYModel;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    Context context;
    OurYModel ourYModel;

    public ViewPagerAdapter(@NonNull FragmentManager fm, Context context, OurYModel ourYModel) {
        super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
        this.ourYModel = ourYModel;
    }

    @Override
    public int getCount() {
        return ourYModel.getYoutubeData().size();
    }

    @Nullable
    public CharSequence getPageTitle(int position){
        return ourYModel.getYoutubeData().get(position).getTitle();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        //Creating fragment
        Bundle bundle = new Bundle();
        bundle.putString("cid",ourYModel.getYoutubeData().get(position).getChannelId());

        FragmentYouTube fragmentYouTube = new FragmentYouTube();
        fragmentYouTube.setArguments(bundle);

        return fragmentYouTube;
    }

}
