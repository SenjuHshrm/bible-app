package com.rgrg.dailydevotion.controller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rgrg.dailydevotion.R;

public class VerseViewFrag extends Fragment {
    public static String MONTH = "";
    public static String DAY = "";
    public static String YEAR = "";
    public static String BIBLE_VERSE = "";

    public VerseViewFrag() {

    }

    public static VerseViewFrag main(String... args) {
        VerseViewFrag frag = new VerseViewFrag();
        Bundle arg = new Bundle();
        arg.putString("MONTH", args[0]);
        arg.putString("DAY", args[1]);
        arg.putString("YEAR", args[2]);
        arg.putString("BIBLE_VERSE", args[3]);
        frag.setArguments(arg);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verse_view, container, false);
        MONTH = getArguments().getString("MONTH");
        DAY = getArguments().getString("DAY");
        YEAR = getArguments().getString("YEAR");
        BIBLE_VERSE = getArguments().getString("BIBLE_VERSE");
        TextView vrsname = (TextView) view.findViewById(R.id.verse_name);
        vrsname.setText(BIBLE_VERSE);
        return view;
    }


}
