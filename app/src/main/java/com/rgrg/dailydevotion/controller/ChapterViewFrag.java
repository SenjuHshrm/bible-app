package com.rgrg.dailydevotion.controller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rgrg.dailydevotion.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChapterViewFrag extends Fragment {
    public static int B_CHAPTERS = 0;
    public static int B_SPCHP = 0;
    public static String BOOKNAME = "";
    public static String TYPE = "";
    private TextView cont, bookname;

    public ChapterViewFrag() {
        // Required empty public constructor
    }

    public static ChapterViewFrag main(int int1, int int2, String str1, String str2) {
        ChapterViewFrag frag = new ChapterViewFrag();
        Bundle args = new Bundle();
        args.putString("BOOK_NAME", str1);
        args.putString("TYPE", str2);
        args.putInt("CHAPTERS", int1);
        args.putInt("SPECCHP", int2);
        B_CHAPTERS = int1;
        B_SPCHP = int2;
        BOOKNAME = str1;
        TYPE = str2;
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chapter_view, container, false);
        cont = (TextView) view.findViewById(R.id.ChapterContent);
        bookname = (TextView) view.findViewById(R.id.BookNameView);
        bookname.setText(BOOKNAME);
        GenerateContent(TYPE, cont);
        return view;
    }

    private void GenerateContent(String type, TextView txtView) {
        StringBuilder txt = new StringBuilder();
        String testament = "";
        switch(type){
            case "OLD":
                testament = "Old_Testament/";
                break;
            case "NEW":
                testament = "New_Testament/";
                break;
        }
        String SpecBook =  "bible/" + testament + repSpace(BOOKNAME )+ "/" + repSpace(BOOKNAME) + "_" + String.valueOf(B_SPCHP) + ".txt";
        try {
            InputStream is = getActivity().getAssets().open(SpecBook);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line = reader.readLine()) != null){
                txt.append(line);
                txt.append("\n\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtView.setText(txt.toString());
    }

    private String repSpace(String str){
        if(str.contains(" ")){
            return str.replace(" ","_");
        }
        return str;
    }

}
