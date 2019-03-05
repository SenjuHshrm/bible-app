package com.rgrg.dailydevotion.controller;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.rgrg.dailydevotion.R;

import java.io.InputStream;


public class MonthListFrag extends Fragment implements View.OnClickListener{
    public MonthListFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_month_list, container, false);
        Button m1 = (Button) view.findViewById(R.id.bm1),
               m2 = (Button) view.findViewById(R.id.bm2),
               m3 = (Button) view.findViewById(R.id.bm3),
               m4 = (Button) view.findViewById(R.id.bm4),
               m5 = (Button) view.findViewById(R.id.bm5),
               m6 = (Button) view.findViewById(R.id.bm6),
               m7 = (Button) view.findViewById(R.id.bm7),
               m8 = (Button) view.findViewById(R.id.bm8),
               m9 = (Button) view.findViewById(R.id.bm9),
               m10 = (Button) view.findViewById(R.id.bm10),
               m11 = (Button) view.findViewById(R.id.bm11),
               m12 = (Button) view.findViewById(R.id.bm12);
        m1.setOnClickListener(this);
        m2.setOnClickListener(this);
        m3.setOnClickListener(this);
        m4.setOnClickListener(this);
        m5.setOnClickListener(this);
        m6.setOnClickListener(this);
        m7.setOnClickListener(this);
        m8.setOnClickListener(this);
        m9.setOnClickListener(this);
        m10.setOnClickListener(this);
        m11.setOnClickListener(this);
        m12.setOnClickListener(this);
        loadBg();
        return view;
    }

    private void loadBg(){
        try {
            FrameLayout fl = (FrameLayout) getActivity().findViewById(R.id.frag_con);
            InputStream is = getActivity().getAssets().open("bg/bg_bright.png");
            Drawable d = Drawable.createFromStream(is, null);
            fl.setBackground(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        Button b = (Button)view;
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in,R.anim.left_out)
                .replace(R.id.frag_con,CalendarViewFrag.main(b.getText().toString()),"CalendarViewFrag")
                .commit();
    }
}
