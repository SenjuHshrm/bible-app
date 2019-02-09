package com.rgrg.dailydevotion.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rgrg.dailydevotion.R;

public class ViewDailyVerseFrag extends Fragment {
    public static String MONTH = "";
    public static String DAY = "";
    public static String YEAR = "";
    private static String VERSE = "";
    public ViewDailyVerseFrag() {

    }

    public static ViewDailyVerseFrag main(String... str) {
        ViewDailyVerseFrag frag = new ViewDailyVerseFrag();
        Bundle args = new Bundle();
        args.putString("Month", str[0]);
        args.putString("Day", str[1]);
        args.putString("Year", str[2]);
        args.putString("Verse", str[3]);
        frag.setArguments(args);
        MONTH = str[0];
        DAY = str[1];
        YEAR = str[2];
        VERSE = str[3];
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_daily_verse, container, false);
        TextView verse = (TextView) view.findViewById(R.id.verse_view);
        verse.setText(VERSE);
        return view;
    }
}
