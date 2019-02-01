package com.rgrg.dailydevotion.controller;


import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.rgrg.dailydevotion.R;
import com.rgrg.dailydevotion.database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarViewFrag extends Fragment implements View.OnClickListener{
    public static String MONTH_NAME = "";
    private GridLayout gl;
    private TextView monthName;


    public CalendarViewFrag() {
        // Required empty public constructor
    }

    public static CalendarViewFrag main(String mn) {
        CalendarViewFrag frag = new CalendarViewFrag();
        Bundle arg = new Bundle();
        arg.putString("month_name", mn);
        frag.setArguments(arg);
        MONTH_NAME = mn;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar_view, container, false);
        gl = (GridLayout) view.findViewById(R.id.month_days_view);
        monthName = (TextView) view.findViewById(R.id.MonthName);
        monthName.setText(MONTH_NAME);
        buildCalendar(MONTH_NAME);
        return view;
    }

    private void buildCalendar(String reqMonth){
        Display disp = getActivity().getWindowManager().getDefaultDisplay();
        Point p = new Point();
        disp.getSize(p);
        Drawable notYet = ContextCompat.getDrawable(getContext(), R.drawable.btn_not_yet);
        int CurrMon = Calendar.getInstance().get(Calendar.MONTH);
        int days = daysInMn(MONTH_NAME, Calendar.getInstance().get(Calendar.YEAR));
        int column = 5, row = days / column, btnWidth = p.x / column;
        String CurrDate = GetMonthString(CurrMon) + " " + Integer.toString(Calendar.getInstance().get(Calendar.DATE)) + ", " + Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
        gl.setColumnCount(column);
        gl.removeAllViews();
        gl.setRowCount(row);
        for(int i = 1; i <= days; i++){
            boolean btnEnable;
            Drawable btnColor;
            Date reqDate = new Date(), currDate = new Date();
            SimpleDateFormat formatDate = new SimpleDateFormat("MMMM dd, yyyy");
            String btnName = Integer.toString(i);
            Button btn = new Button(getActivity());
            String ReqDate = reqMonth + " " + Integer.toString(i) + ", " + Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
            String[] mJourn = GetRecordByDateM(ReqDate);
            String[] eJourn = GetRecordByDateE(ReqDate);
            try{
                reqDate = formatDate.parse(ReqDate);
                currDate = formatDate.parse(CurrDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(reqDate.compareTo(currDate) <= 0){
                btnEnable = true;
                btnColor = setColor(mJourn, eJourn);
            } else {
                btnEnable = false;
                btnColor = notYet;
            }
            btn.setText(btnName);
            btn.setOnClickListener(this);
            btn.setEnabled(btnEnable);
            btn.setBackground(btnColor);
            btn.setLayoutParams(new ViewGroup.LayoutParams(btnWidth,100));
            btn.setTextSize(18);
            gl.addView(btn);
        }
    }

    private int daysInMn(String month, int year) {
        int res = 0;
        switch(month){
            case "January":
                res = 31;
                break;
            case "February":
                if((year % 400 == 0) || (year % 4 == 0)){
                    res = 29;
                } else {
                    res = 28;
                }
                break;
            case "March":
                res = 31;
                break;
            case "April":
                res = 30;
                break;
            case "May":
                res = 31;
                break;
            case "June":
                res = 30;
                break;
            case "July":
                res = 31;
                break;
            case "August":
                res = 31;
                break;
            case "September":
                res = 30;
                break;
            case "October":
                res = 31;
                break;
            case "November":
                res = 30;
                break;
            case "December":
                res = 31;
                break;

        }
        return res;
    }

    private Drawable setColor(String[] param1, String[] param2) {
        Drawable ColorBtn;
        Drawable wholeDone = ContextCompat.getDrawable(getContext(), R.drawable.btn_whole_done);
        Drawable halfDone = ContextCompat.getDrawable(getContext(), R.drawable.btn_half_done);
        Drawable noWork = ContextCompat.getDrawable(getContext(), R.drawable.btn_no_work);
        if(param1 != null){
            if(param2 != null){
                ColorBtn = wholeDone;
            }else{
                ColorBtn = halfDone;
            }
        } else {
            if(param2 != null){
                ColorBtn = halfDone;
            }else{
                ColorBtn = noWork;
            }
        }
        return ColorBtn;
    }

    private String GetMonthString(int mnt) {
        String res = "";
        switch(mnt){
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

    private String[] GetRecordByDateM(String date){
        DatabaseHelper dbHelp = new DatabaseHelper(getContext());
        return dbHelp.CheckSOAPMorning(date);
    }

    private String[] GetRecordByDateE(String date){
        DatabaseHelper dbHelp = new DatabaseHelper(getContext());
        return dbHelp.CheckSOAPEvening(date);
    }

    @Override
    public void onClick(View view) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_con,
                        JournalFrag.main(MONTH_NAME, Integer.toString(Calendar.getInstance().get(Calendar.DATE)), Integer.toString(Calendar.getInstance().get(Calendar.YEAR))),
                        "JournalFrag").commit();
    }
}
