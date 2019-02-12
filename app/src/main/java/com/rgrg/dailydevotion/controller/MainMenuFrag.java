package com.rgrg.dailydevotion.controller;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.rgrg.dailydevotion.R;

import java.io.InputStream;

public class MainMenuFrag extends Fragment implements View.OnClickListener{
    private ImageButton bibleBtn, ddBtn, infoBtn, viewBtn;
    private ImageButton[] imgBtns = {bibleBtn, ddBtn, infoBtn, viewBtn};
    private String[] btnImg = {"bg/bibleBtn.png", "bg/dDevBtn.png", "bg/infoBtn.png"};
    private int[] ids = {R.id.bibleBtn, R.id.ddBtn, R.id.infoBtn};
    public MainMenuFrag() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        loadBanner(view);
        loadBtn(view);
        return view;
    }

    private void loadBanner(View v) {
        ImageView banner = (ImageView) v.findViewById(R.id.bannerImg);
        try {
            InputStream is = getActivity().getAssets().open("bg/banner.png");
            Drawable d = Drawable.createFromStream(is, null);
            banner.setImageDrawable(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBtn(View v) {
        try {
            for(int x = 0; x < 3; x++) {
                imgBtns[x] = (ImageButton) v.findViewById(ids[x]);
                InputStream is = getActivity().getAssets().open(btnImg[x]);
                Drawable d = Drawable.createFromStream(is, null);
                imgBtns[x].setImageDrawable(d);
                imgBtns[x].setOnClickListener(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bibleBtn:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frag_con, new TestamentFrag(), "TestamentFrag").commit();
                break;
            case R.id.ddBtn:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frag_con, new MonthListFrag(), "MonthListFrag").commit();
                break;
            case R.id.infoBtn:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frag_con, new InstructionFrag(), "InstructionFrag").commit();
                break;
        }
    }
}
