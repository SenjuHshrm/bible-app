package com.rgrg.dailydevotion.controller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rgrg.dailydevotion.R;

public class DevViewerFrag extends Fragment {
    private TextView sdate, sSc, sOb, sAp, sPr, header;

    public static DevViewerFrag main(String[] data, String date, String timeData){
        DevViewerFrag frag = new DevViewerFrag();
        Bundle args = new Bundle();
        args.putStringArray("DataArray", data);
        args.putString("date", date);
        args.putString("time", timeData);
        frag.setArguments(args);
        return frag;
    }
    public DevViewerFrag() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dev_viewer, container, false);
        Bundle bn = getArguments();
        String[] str = bn.getStringArray("DataArray");
        String dt = bn.getString("time");
        header = (TextView)view.findViewById(R.id.textView22);
        sdate = (TextView)view.findViewById(R.id.selDate);
        sSc = (TextView)view.findViewById(R.id.selDataSc);
        sOb = (TextView)view.findViewById(R.id.selDataOb);
        sAp = (TextView)view.findViewById(R.id.selDataAp);
        sPr = (TextView)view.findViewById(R.id.selDataPr);
        sdate.setText(bn.getString("date"));
        try {
            sSc.setText(str[0]);
            sOb.setText(str[1]);
            sAp.setText(str[2]);
            sPr.setText(str[3]);
            if(dt.equalsIgnoreCase("AM")){
                header.setText("Morning Devotion");
            } else if(dt.equalsIgnoreCase("PM")){
                header.setText("Evening Devotion");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }

}
