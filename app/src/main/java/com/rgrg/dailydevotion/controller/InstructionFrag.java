package com.rgrg.dailydevotion.controller;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.rgrg.dailydevotion.R;

import java.io.InputStream;

public class InstructionFrag extends Fragment {


    public InstructionFrag() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loadBg();
        return inflater.inflate(R.layout.fragment_instruction, container, false);
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

}
