package com.rgrg.dailydevotion.controller;


import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.rgrg.dailydevotion.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChapterListFrag extends Fragment implements View.OnClickListener{
    public static String T_PART = "";
    public static String B_BOOK = "";
    public static int B_CHAPTER = 0;
    private GridLayout gl;
    public ChapterListFrag() {
        // Required empty public constructor
    }

    public static ChapterListFrag main(int chp, String... str) {
        ChapterListFrag frag = new ChapterListFrag();
        Bundle args = new Bundle();
        args.putString("PART", str[0]);
        args.putString("BOOK", str[1]);
        args.putInt("CHAPTER", chp);
        frag.setArguments(args);
        T_PART = str[0];
        B_BOOK = str[1];
        B_CHAPTER = chp;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chapter_list, container, false);
        TextView BookName = (TextView) view.findViewById(R.id.BookNameChpList);
        gl = (GridLayout) view.findViewById(R.id.ChaptersList);
        BookName.setText(getArguments().getString("BOOK"));

        BuildChapterList(getArguments().getInt("CHAPTER"));
        return view;
    }

    private void BuildChapterList(int chpt) {
        Display disp = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        int row = (chpt / 5) + (chpt % 5);
        int btnWidth = size.x / 5;
        Log.e("GridLayout Width", Float.toString(btnWidth));
        gl.setColumnCount(5);
        gl.removeAllViews();
        gl.setRowCount(row);
        for(int i = 0 ; i < chpt; i++){
            String btnName = Integer.toString(i + 1);
            Button btn = new Button(getActivity());
            btn.setText(btnName);
            btn.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBg));
            btn.setTypeface(btn.getTypeface(), Typeface.BOLD);
            btn.setTextSize(20);
            btn.setLayoutParams(new ViewGroup.LayoutParams(btnWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
            btn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_chapter_bg));
            btn.setOnClickListener(this);
            gl.addView(btn);
        }
    }

    @Override
    public void onClick(View view) {
        Button b = (Button)view;
        getActivity()
                .getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_con, ChapterViewFrag.main(B_CHAPTER,Integer.parseInt(b.getText().toString()), B_BOOK,T_PART), "ChapterViewFrag").commit();
    }
}
