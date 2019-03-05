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

public class TestamentFrag extends Fragment implements  View.OnClickListener{

    public TestamentFrag() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_testament, container, false);
        Button btnOld = (Button) view.findViewById(R.id.btnOldT);
        Button btnNew = (Button) view.findViewById(R.id.btnNewT);
        btnOld.setOnClickListener(this);
        btnNew.setOnClickListener(this);
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
        String str = "";
        switch(view.getId()){
            case R.id.btnOldT:
                str = "OLD";
                break;
            case R.id.btnNewT:
                str = "NEW";
                break;
        }
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.left_out).replace(R.id.frag_con, BookMenuFrag.main(str), "BookMenuFrag").commit();
    }
}
