package com.rgrg.dailydevotion.controller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rgrg.dailydevotion.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestamentFrag extends Fragment implements  View.OnClickListener{

    public TestamentFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_testament, container, false);
        Button btnOld = (Button) view.findViewById(R.id.btnOldT);
        Button btnNew = (Button) view.findViewById(R.id.btnNewT);
        btnOld.setOnClickListener(this);
        btnNew.setOnClickListener(this);
        return view;
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
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frag_con, BookMenuFrag.main(str), "BookMenuFrag").commit();
    }
}
