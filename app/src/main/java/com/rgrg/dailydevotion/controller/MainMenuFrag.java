package com.rgrg.dailydevotion.controller;


import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.rgrg.dailydevotion.R;

import java.io.InputStream;
import java.util.Random;

public class MainMenuFrag extends Fragment implements View.OnClickListener{

    public MainMenuFrag() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        loadBanner(view);
        loadBtn(view);
        loadBg();
        return view;
    }

    private void loadBanner(View v) {
        ImageView banner = (ImageView) v.findViewById(R.id.bannerImg);
        try {
            Random rnd = new Random();
            int val = rnd.nextInt(11);
            String fileDir = "verses/" + Integer.toString(val) + ".png";
            InputStream is = getActivity().getAssets().open(fileDir);
            Drawable d = Drawable.createFromStream(is, null);
            banner.setImageDrawable(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBtn(View view) {
        String[] tags = new String[]{"Bible","Devotion","Pattern","BIBLE","DEVOTION","DEVOTION\nPATTERN"};
        String[] dirs = new String[]{"ic/bible.png","ic/devotion.png","ic/pattern.png"};
        Display disp = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        GridLayout gl = (GridLayout) view.findViewById(R.id.mmenu_btn_con);
        gl.setColumnCount(3);
        gl.removeAllViews();
        gl.setRowCount(2);
        int btnWidth = size.x / 3, btnHeight = size.y / 6;
        for(int i = 0; i < 6; i++){
            if(i <= 2){
                ImageButton btn = new ImageButton(getActivity());
                btn.setTag(tags[i]);
                insertIc(btn, dirs[i]);
                btn.setLayoutParams(new ViewGroup.LayoutParams(btnWidth, btnHeight));
                btn.setOnClickListener(this);
                gl.addView(btn);
            }
            if(i >= 3){
                TextView txtView = new TextView(getActivity());
                txtView.setGravity(Gravity.CENTER_HORIZONTAL);
                txtView.setText(tags[i]);
                txtView.setTextSize(20);
                txtView.setTypeface(txtView.getTypeface(), Typeface.BOLD);
                txtView.setLayoutParams(new ViewGroup.LayoutParams(btnWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
                gl.addView(txtView);
            }
        }
    }

    private void insertIc(ImageButton ib, String dir){
        try {
            InputStream is = getActivity().getAssets().open(dir);
            Drawable d = Drawable.createFromStream(is,null);
            ib.setBackground(d);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void loadBg(){
        try {
            FrameLayout fl = (FrameLayout) getActivity().findViewById(R.id.frag_con);
            InputStream is = getActivity().getAssets().open("bg/bg_dark.jpg");
            Drawable d = Drawable.createFromStream(is, null);
            fl.setBackground(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        Object tagObj = view.getTag();
        if(tagObj.equals("Bible")){
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.left_out).replace(R.id.frag_con, new TestamentFrag(), "TestamentFrag").commit();
        } else if(tagObj.equals("Devotion")){
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.left_out).replace(R.id.frag_con, new MonthListFrag(), "MonthListFrag").commit();
        } else if(tagObj.equals("Pattern")){
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.left_out).replace(R.id.frag_con, new InstructionFrag(), "InstructionFrag").commit();
        }
        ImageButton ib = (ImageButton) getActivity().findViewById(R.id.searchBtn);
        ib.setVisibility(View.INVISIBLE);
    }
}
