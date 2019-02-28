package com.rgrg.dailydevotion.controller;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rgrg.dailydevotion.R;

import java.io.InputStream;

public class LoadingScreenFrag extends Fragment {

    public LoadingScreenFrag() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loading_screen, container, false);
        loadImg(view);
        loadSys();
        return view;
    }

    private void loadImg(View v) {
        ImageView img = (ImageView) v.findViewById(R.id.loadingScreenImg);
        try{
            InputStream is = getActivity().getAssets().open("bg/LoadingImg.png");
            Drawable d = Drawable.createFromStream(is, null);
            img.setImageDrawable(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSys() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.left_out).replace(R.id.frag_con, new MainMenuFrag(), "MainMenuFrag").commit();
            }
        }, 3000);
    }

}
