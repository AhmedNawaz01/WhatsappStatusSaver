package com.andrinoapp.statussaver.Adapter;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.andrinoapp.statussaver.Fragments.WAImageFragment;
import com.andrinoapp.statussaver.Fragments.WAVideoFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewPagerWAAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    private FragmentManager mFragmentManager;

    public ViewPagerWAAdapter(FragmentManager fm, ArrayList<Fragment> fragmentArrayList) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mFragmentManager = fm;
        this.fragmentArrayList = fragmentArrayList;

    }


    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }


}