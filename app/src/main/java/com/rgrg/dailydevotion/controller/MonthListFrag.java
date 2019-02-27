package com.rgrg.dailydevotion.controller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.rgrg.dailydevotion.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonthListFrag extends Fragment {
    private String[] monthlist = {"January","February","March","April",
                                    "May","June","July","August",
                                    "September","October","November","December"};
    private ListView listMonth;
    public MonthListFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_month_list, container, false);
        listMonth = (ListView) view.findViewById(R.id.monthList);
        GenMonth();
        listMonth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView monthnm = (TextView) view.findViewById(R.id.MonthName);
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.left_out).replace(R.id.frag_con, CalendarViewFrag.main(monthnm.getText().toString()), "CalendarViewFrag").commit();
            }
        });
        return view;
    }

    private void GenMonth() {
        List<Map<String,String>> mnt = new ArrayList<Map<String,String>>();
        for(int i = 0; i < monthlist.length; i++){
            Map<String,String> datum = new HashMap<String,String>(1);
            datum.put("Month", monthlist[i]);
            mnt.add(datum);
        }
        SimpleAdapter adapter = new SimpleAdapter(getContext(), mnt, R.layout.layout_month_listview, new String[]{"Month"}, new int[]{R.id.MonthName});
        listMonth.setAdapter(adapter);
    }

}
