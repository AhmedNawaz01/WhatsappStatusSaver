package com.andrinoapp.statussaver.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.appcompat.view.ActionMode;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.andrinoapp.statussaver.Adapter.WAImageAdapter;
import com.andrinoapp.statussaver.Model.WAImageModel;
import com.andrinoapp.statussaver.R;

import java.util.ArrayList;

public class WAAboutFragment extends Fragment {

    FragmentActivity activity;


    private static View v;

    public WAAboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_wa_about, container, false);

        activity = getActivity();
/*
        MobileAds.initialize(activity, getString(R.string.admob_app_id));
        mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());*/


        return v;
    }
}
