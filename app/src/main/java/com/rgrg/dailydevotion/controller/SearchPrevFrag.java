package com.rgrg.dailydevotion.controller;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.rgrg.dailydevotion.R;
import com.rgrg.dailydevotion.database.DatabaseHelper;

import java.io.InputStream;
import java.util.Calendar;

public class SearchPrevFrag extends Fragment implements View.OnClickListener, CalendarView.OnDateChangeListener {
    private int year, day, month;

    public SearchPrevFrag() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_prev, container, false);
        CalendarView cv = (CalendarView) view.findViewById(R.id.calendarView2);
        cv.setDate(Calendar.getInstance().getTimeInMillis(),false,true);
        cv.setOnDateChangeListener(this);
        Button b = (Button) view.findViewById(R.id.searchDev);
        b.setOnClickListener(this);
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
        String selDate;
        if(checkVal(year)){
            Calendar cl = Calendar.getInstance();
            selDate = convertMonth(cl.get(Calendar.MONTH)) + " " + cl.get(Calendar.DATE) + ", " + cl.get(Calendar.YEAR);
        } else {
            selDate = convertMonth(month) + " " + Integer.toString(day) + ", " + Integer.toString(year);
        }
        RadioGroup rdg = (RadioGroup) getActivity().findViewById(R.id.radioGroup);
        int radioButtonID = rdg.getCheckedRadioButtonId();
        View radioButton = rdg.findViewById(radioButtonID);
        int idx = rdg.indexOfChild(radioButton);
        RadioButton r = (RadioButton) rdg.getChildAt(idx);
        String selRad = r.getText().toString();
        String[] data = new String[6];
        switch(selRad){
            case "AM":
                data = new DatabaseHelper(getContext()).CheckSOAPMorning(selDate);
                break;
            case "PM":
                data = new DatabaseHelper(getContext()).CheckSOAPEvening(selDate);
                break;
        }
        if(data == null){
            Toast.makeText(getContext(), "No saved data found.", Toast.LENGTH_SHORT).show();
        } else {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.left_out).replace(R.id.frag_con, DevViewerFrag.main(data,selDate,selRad),"DevViewerFrag")
                    .commit();
        }
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
        year = i;
        month = i1;
        day = i2;
    }

    private boolean checkVal(int i){
        return i == 0;
    }

    private String convertMonth(int i){
        String res = "";
        switch(i){
            case 0:
                res = "January";
                break;
            case 1:
                res = "February";
                break;
            case 2:
                res = "March";
                break;
            case 3:
                res = "April";
                break;
            case 4:
                res = "May";
                break;
            case 5:
                res = "June";
                break;
            case 6:
                res = "July";
                break;
            case 7:
                res = "August";
                break;
            case 8:
                res = "September";
                break;
            case 9:
                res = "October";
                break;
            case 10:
                res = "November";
                break;
            case 11:
                res = "December";
                break;
        }
        return res;
    }
}
