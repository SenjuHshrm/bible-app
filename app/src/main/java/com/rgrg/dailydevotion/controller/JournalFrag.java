package com.rgrg.dailydevotion.controller;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rgrg.dailydevotion.R;
import com.rgrg.dailydevotion.database.DatabaseHelper;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class JournalFrag extends Fragment{
    public static String MONTH = "";
    public static String DAY = "";
    public static String YEAR = "";
    private TextView title, bverse, cdate, inS, inO, inA, inP;
    private Button btnSave, btnUpdate, btnDel;
    public JournalFrag() {
        // Required empty public constructor
    }

    public static JournalFrag main(String... str) {
        JournalFrag frag = new JournalFrag();
        Bundle args = new Bundle();
        args.putString("Month", str[0]);
        args.putString("Day", str[1]);
        args.putString("Year", str[2]);
        frag.setArguments(args);
        MONTH = str[0];
        DAY = str[1];
        YEAR = str[2];
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_journal, container, false);
        title = (TextView) view.findViewById(R.id.time_base_title);
        bverse = (TextView) view.findViewById(R.id.bible_verse);
        cdate = (TextView) view.findViewById(R.id.curr_date);
        String curDate = MONTH + " " + DAY + ", " + YEAR;
        cdate.setText(curDate);
        String TitleLbl = getTime();

        bverse.setText(getBibleVerse());
        inS = (TextView) view.findViewById(R.id.inScripture);
        inO = (TextView) view.findViewById(R.id.inObserve);
        inA = (TextView) view.findViewById(R.id.inApply);
        inP = (TextView) view.findViewById(R.id.inPrayer);
        inS.setOnClickListener(onTextViewClick());
        inO.setOnClickListener(onTextViewClick());
        inA.setOnClickListener(onTextViewClick());
        inP.setOnClickListener(onTextViewClick());
        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnUpdate = (Button) view.findViewById(R.id.btnUpdate);
        btnDel = (Button) view.findViewById(R.id.btnDelete);
        btnSave.setOnClickListener(onBtnClick());
        btnUpdate.setOnClickListener(onBtnClick());
        btnDel.setOnClickListener(onBtnClick());
        if(TitleLbl.equalsIgnoreCase("AM")){
            title.setText(getActivity().getString(R.string.lblMDev));
            GetSOAPM(view);
        } else {
            title.setText(getActivity().getString(R.string.lblEDev));
            GetSOAPE(view);
        }
        return view;
    }

    private String getBibleVerse() {
        String res;
        DatabaseHelper dbHelp = new DatabaseHelper(getContext());
        String qdate = MONTH + " " + DAY;
        String qtime = getTime();
        if(qtime.equalsIgnoreCase("AM")){
            res = dbHelp.getVerseMorning(qdate);
        } else {
            res = dbHelp.getVerseEvening(qdate);
        }
        return res;
    }

    private String getTime() {
        String res;
        Calendar cl = Calendar.getInstance();
        int time = cl.get(Calendar.HOUR_OF_DAY);
        if(time < 12){
            res = "AM";
        } else {
            res = "PM";
        }
        return res;
    }

    private void openEditor(final int id, final String str){
        final TextView txtView = (TextView) getActivity().findViewById(id);
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.layout_journal_input);
        Button d = (Button) dialog.findViewById(R.id.btnDone);
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTxt = (EditText) dialog.findViewById(R.id.editor_input);
                txtView.setText(editTxt.getText().toString());
                dialog.dismiss();
            }
        });
        Button c = (Button) dialog.findViewById(R.id.btnCancel);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        EditText edt = (EditText) dialog.findViewById(R.id.editor_input);
        edt.setText(str);
        dialog.show();
    }

    private View.OnClickListener onTextViewClick() {

        return new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int id = 0;
                String str = "";
                switch(v.getId()){
                    case R.id.inScripture:
                        id = R.id.inScripture;
                        break;
                    case R.id.inObserve:
                        id = R.id.inObserve;
                        break;
                    case R.id.inApply:
                        id = R.id.inApply;
                        break;
                    case R.id.inPrayer:
                        id = R.id.inPrayer;
                        break;
                }
                TextView txt = (TextView) v.findViewById(id);
                str = txt.getText().toString();
                openEditor(id, str);
            }
        };
    }

    private View.OnClickListener onBtnClick() {
        return new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch(view.getId()){
                    case R.id.btnSave:
                        if(getTime().equalsIgnoreCase("AM")){
                            SaveSOAPM(view);
                        } else {
                            SaveSOAPE(view);
                        }
                        break;
                    case R.id.btnUpdate:
                        if(getTime().equalsIgnoreCase("AM")){
                            UpdateSOAPM(view);
                        } else {
                            UpdateSOAPE(view);
                        }
                        break;
                    case R.id.btnDelete:
                        if(getTime().equalsIgnoreCase("AM")){
                            DelSOAPM(view);
                        } else {
                            DelSOAPE(view);
                        }
                        break;
                }
            }
        };
    }

    private void GetSOAPM(View view) {
        String currDate = MONTH + " " + DAY + ", " + YEAR;
        DatabaseHelper dbHelp = new DatabaseHelper(getContext());
        String[] data = dbHelp.CheckSOAPMorning(currDate);
        inS = (TextView) view.findViewById(R.id.inScripture);
        inO = (TextView) view.findViewById(R.id.inObserve);
        inA = (TextView) view.findViewById(R.id.inApply);
        inP = (TextView) view.findViewById(R.id.inPrayer);
        if(data == null){
            btnSave.setEnabled(true);
            btnUpdate.setEnabled(false);
            btnDel.setEnabled(false);
        } else {
            btnSave.setEnabled(false);
            btnUpdate.setEnabled(true);
            btnDel.setEnabled(true);
            inS.setText(data[0]);
            inO.setText(data[1]);
            inA.setText(data[2]);
            inP.setText(data[3]);
        }
    }

    private void GetSOAPE(View view) {
        String currDate = MONTH + " " + DAY + ", " + YEAR;
        DatabaseHelper dbHelp = new DatabaseHelper(getContext());
        String[] data = dbHelp.CheckSOAPEvening(currDate);
        inS = (TextView) view.findViewById(R.id.inScripture);
        inO = (TextView) view.findViewById(R.id.inObserve);
        inA = (TextView) view.findViewById(R.id.inApply);
        inP = (TextView) view.findViewById(R.id.inPrayer);
        if(data == null){
            btnSave.setEnabled(true);
            btnUpdate.setEnabled(false);
            btnDel.setEnabled(false);
        } else {
            btnSave.setEnabled(false);
            btnUpdate.setEnabled(true);
            btnDel.setEnabled(true);
            inS.setText(data[0]);
            inO.setText(data[1]);
            inA.setText(data[2]);
            inP.setText(data[3]);
        }
    }

    private void SaveSOAPM(View view) {
        DatabaseHelper dbHelp = new DatabaseHelper(getContext());
        cdate = (TextView) getActivity().findViewById(R.id.curr_date);
        inS = (TextView) getActivity().findViewById(R.id.inScripture);
        inO = (TextView) getActivity().findViewById(R.id.inObserve);
        inA = (TextView) getActivity().findViewById(R.id.inApply);
        inP = (TextView) getActivity().findViewById(R.id.inPrayer);
        btnSave = (Button) getActivity().findViewById(R.id.btnSave);
        btnUpdate = (Button) getActivity().findViewById(R.id.btnUpdate);
        btnDel = (Button) getActivity().findViewById(R.id.btnDelete);
        String[] req = new String[5];
        req[0] = inS.getText().toString();
        req[1] = inO.getText().toString();
        req[2] = inA.getText().toString();
        req[3] = inP.getText().toString();
        req[4] = cdate.getText().toString();
        if(dbHelp.saveSOAPMorning(req)) {
            Toast.makeText(getContext(), "Saved.", Toast.LENGTH_SHORT).show();
            btnSave.setEnabled(false);
            btnUpdate.setEnabled(true);
            btnDel.setEnabled(true);
        } else {
            Toast.makeText(getContext(), "Not saved.", Toast.LENGTH_SHORT).show();
        }
    }

    private void SaveSOAPE(View view) {
        DatabaseHelper dbHelp = new DatabaseHelper(getContext());
        cdate = (TextView) getActivity().findViewById(R.id.curr_date);
        inS = (TextView) getActivity().findViewById(R.id.inScripture);
        inO = (TextView) getActivity().findViewById(R.id.inObserve);
        inA = (TextView) getActivity().findViewById(R.id.inApply);
        inP = (TextView) getActivity().findViewById(R.id.inPrayer);
        btnSave = (Button) getActivity().findViewById(R.id.btnSave);
        btnUpdate = (Button) getActivity().findViewById(R.id.btnUpdate);
        btnDel = (Button) view.findViewById(R.id.btnDelete);
        String[] req = new String[5];
        req[0] = inS.getText().toString();
        req[1] = inO.getText().toString();
        req[2] = inA.getText().toString();
        req[3] = inP.getText().toString();
        req[4] = cdate.getText().toString();
        if(dbHelp.saveSOAPEvening(req)) {
            Toast.makeText(getContext(), "Saved.", Toast.LENGTH_SHORT).show();
            btnSave.setEnabled(false);
            btnUpdate.setEnabled(true);
            btnDel.setEnabled(true);
        } else {
            Toast.makeText(getContext(), "Not saved.", Toast.LENGTH_SHORT).show();
        }
    }

    private void UpdateSOAPM(View view) {
        DatabaseHelper dbHelp = new DatabaseHelper(getContext());
        cdate = (TextView) getActivity().findViewById(R.id.curr_date);
        inS = (TextView) getActivity().findViewById(R.id.inScripture);
        inO = (TextView) getActivity().findViewById(R.id.inObserve);
        inA = (TextView) getActivity().findViewById(R.id.inApply);
        inP = (TextView) getActivity().findViewById(R.id.inPrayer);
        btnSave = (Button) getActivity().findViewById(R.id.btnSave);
        btnUpdate = (Button) getActivity().findViewById(R.id.btnUpdate);
        btnDel = (Button) getActivity().findViewById(R.id.btnDelete);
        String[] req = new String[5];
        req[0] = inS.getText().toString();
        req[1] = inO.getText().toString();
        req[2] = inA.getText().toString();
        req[3] = inP.getText().toString();
        req[4] = cdate.getText().toString();
        if(dbHelp.updateSOAPMorning(req)) {
            Toast.makeText(getContext(), "Saved.", Toast.LENGTH_SHORT).show();
            btnSave.setEnabled(false);
            btnUpdate.setEnabled(true);
            btnDel.setEnabled(true);
        } else {
            Toast.makeText(getContext(), "Not saved.", Toast.LENGTH_SHORT).show();
        }
    }

    private void UpdateSOAPE(View view) {
        DatabaseHelper dbHelp = new DatabaseHelper(getContext());
        cdate = (TextView) getActivity().findViewById(R.id.curr_date);
        inS = (TextView) getActivity().findViewById(R.id.inScripture);
        inO = (TextView) getActivity().findViewById(R.id.inObserve);
        inA = (TextView) getActivity().findViewById(R.id.inApply);
        inP = (TextView) getActivity().findViewById(R.id.inPrayer);
        btnSave = (Button) getActivity().findViewById(R.id.btnSave);
        btnUpdate = (Button) getActivity().findViewById(R.id.btnUpdate);
        btnDel = (Button) getActivity().findViewById(R.id.btnDelete);
        String[] req = new String[5];
        req[0] = inS.getText().toString();
        req[1] = inO.getText().toString();
        req[2] = inA.getText().toString();
        req[3] = inP.getText().toString();
        req[4] = cdate.getText().toString();
        if(dbHelp.updateSOAPEvening(req)) {
            Toast.makeText(getContext(), "Updated.", Toast.LENGTH_SHORT).show();
            btnSave.setEnabled(false);
            btnUpdate.setEnabled(true);
            btnDel.setEnabled(true);
        } else {
            Toast.makeText(getContext(), "Not updated.", Toast.LENGTH_SHORT).show();
        }
    }

    private void DelSOAPM(View view) {
        DatabaseHelper dbHelp = new DatabaseHelper(getContext());
        cdate = (TextView) getActivity().findViewById(R.id.curr_date);
        inS = (TextView) getActivity().findViewById(R.id.inScripture);
        inO = (TextView) getActivity().findViewById(R.id.inObserve);
        inA = (TextView) getActivity().findViewById(R.id.inApply);
        inP = (TextView) getActivity().findViewById(R.id.inPrayer);
        btnSave = (Button) getActivity().findViewById(R.id.btnSave);
        btnUpdate = (Button) getActivity().findViewById(R.id.btnUpdate);
        btnDel = (Button) getActivity().findViewById(R.id.btnDelete);
        String[] req = new String[5];
        req[0] = inS.getText().toString();
        req[1] = inO.getText().toString();
        req[2] = inA.getText().toString();
        req[3] = inP.getText().toString();
        req[4] = cdate.getText().toString();
        if(dbHelp.deleteSOAPMorning(req)) {
            Toast.makeText(getContext(), "Deleted.", Toast.LENGTH_SHORT).show();
            btnSave.setEnabled(true);
            btnUpdate.setEnabled(false);
            btnDel.setEnabled(false);
            inS.setText(" ");
            inO.setText(" ");
            inA.setText(" ");
            inP.setText(" ");
        } else {
            Toast.makeText(getContext(), "Not deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    private void DelSOAPE(View view) {
        DatabaseHelper dbHelp = new DatabaseHelper(getContext());
        cdate = (TextView) getActivity().findViewById(R.id.curr_date);
        inS = (TextView) getActivity().findViewById(R.id.inScripture);
        inO = (TextView) getActivity().findViewById(R.id.inObserve);
        inA = (TextView) getActivity().findViewById(R.id.inApply);
        inP = (TextView) getActivity().findViewById(R.id.inPrayer);
        btnSave = (Button) getActivity().findViewById(R.id.btnSave);
        btnUpdate = (Button) getActivity().findViewById(R.id.btnUpdate);
        btnDel = (Button) getActivity().findViewById(R.id.btnDelete);
        String[] req = new String[5];
        req[0] = inS.getText().toString();
        req[1] = inO.getText().toString();
        req[2] = inA.getText().toString();
        req[3] = inP.getText().toString();
        req[4] = cdate.getText().toString();
        if(dbHelp.deleteSOAPEvening(req)) {
            Toast.makeText(getContext(), "Deleted.", Toast.LENGTH_SHORT).show();
            btnSave.setEnabled(true);
            btnUpdate.setEnabled(false);
            btnDel.setEnabled(false);
            inS.setText(" ");
            inO.setText(" ");
            inA.setText(" ");
            inP.setText(" ");
        } else {
            Toast.makeText(getContext(), "Not deleted.", Toast.LENGTH_SHORT).show();
        }
    }
}
