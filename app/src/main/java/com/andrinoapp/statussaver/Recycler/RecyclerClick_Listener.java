package com.andrinoapp.statussaver.Recycler;


import android.view.View;

public interface RecyclerClick_Listener {

    void onClick(View view, int position);

    void onLongClick(View view, int position);
}