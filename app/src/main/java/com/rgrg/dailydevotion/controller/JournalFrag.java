package com.rgrg.dailydevotion.controller;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rgrg.dailydevotion.R;
import com.rgrg.dailydevotion.database.DatabaseHelper;

import java.io.InputStream;
import java.util.Calendar;

public class JournalFrag extends Fragment{
    public static String MONTH = "";
    public static String DAY = "";
    public static String YEAR = "";
    private TextView title, bverse, cdate, inS, inO, inA, inP;
    private Button btnSave, btnUpdate, btnDel;
    public JournalFrag() {}

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
        View view = inflater.inflate(R.layout.fragment_journal, container, false);
        title = (TextView) view.findViewById(R.id.time_base_title);
        bverse = (TextView) view.findViewById(R.id.bible_verse);
        cdate = (TextView) view.findViewById(R.id.curr_date);
        String curDate = MONTH + " " + DAY + ", " + YEAR;
        cdate.setText(curDate);
        String TitleLbl = getTime();
        String BVerse = getBibleVerse();
        bverse.setText(BVerse);
        bverse.setOnClickListener(onVerseClick());
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
        changeTimeStat(view);
        if(TitleLbl.equalsIgnoreCase("AM")){
            title.setText(getActivity().getString(R.string.lblMDev));
            GetSOAPM(view);
        } else {
            title.setText(getActivity().getString(R.string.lblEDev));
            GetSOAPE(view);
        }
        return view;
    }

    private void changeTimeStat(View v){
        try {
            String DIR = "";
            switch(Calendar.getInstance().get(Calendar.AM_PM)){
                case Calendar.AM:
                    DIR = "ic/morning.png";
                    break;
                case Calendar.PM:
                    DIR = "ic/evening.png";
                    break;
            }
            ImageView iv = (ImageView)v.findViewById(R.id.imgTimeStat);
            InputStream is = getActivity().getAssets().open(DIR);
            Drawable d = Drawable.createFromStream(is,null);
            iv.setImageDrawable(d);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        try {
            Window wndw = dialog.getWindow();
            wndw.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private View.OnClickListener onVerseClick() {
        final String verse = getBibleVerse();
        return new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in,R.anim.left_out)
                        .replace(R.id.frag_con, VerseViewFrag.main(MONTH,DAY,YEAR,verse), "VerseViewFrag")
                        .commit();
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
        btnDel = (Button) getActivity().findViewById(R.id.btnDelete);
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
        final DatabaseHelper dbHelp = new DatabaseHelper(getContext());
        final TextView ucdate = (TextView) getActivity().findViewById(R.id.curr_date);
        final TextView uinS = (TextView) getActivity().findViewById(R.id.inScripture);
        final TextView uinO = (TextView) getActivity().findViewById(R.id.inObserve);
        final TextView uinA = (TextView) getActivity().findViewById(R.id.inApply);
        final TextView uinP = (TextView) getActivity().findViewById(R.id.inPrayer);
        final TextView ubtnSave = (Button) getActivity().findViewById(R.id.btnSave);
        final TextView ubtnUpdate = (Button) getActivity().findViewById(R.id.btnUpdate);
        final TextView ubtnDel = (Button) getActivity().findViewById(R.id.btnDelete);
        final String[] req = new String[5];
        req[0] = uinS.getText().toString();
        req[1] = uinO.getText().toString();
        req[2] = uinA.getText().toString();
        req[3] = uinP.getText().toString();
        req[4] = ucdate.getText().toString();
        AlertDialog.Builder upd = new AlertDialog.Builder(getActivity());
        upd.setCancelable(true)
                .setMessage("Do you want to save changes?")
                .setPositiveButton("Yes", new AlertDialog.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(dbHelp.updateSOAPMorning(req)) {
                            Toast.makeText(getContext(), "Saved.", Toast.LENGTH_SHORT).show();
                            ubtnSave.setEnabled(false);
                            ubtnUpdate.setEnabled(true);
                            ubtnDel.setEnabled(true);
                        } else {
                            Toast.makeText(getContext(), "Not saved.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("No", null);
        AlertDialog b = upd.create();
        b.show();

    }

    private void UpdateSOAPE(View view) {
        final DatabaseHelper dbHelp = new DatabaseHelper(getContext());
        final TextView ucdate = (TextView) getActivity().findViewById(R.id.curr_date);
        final TextView uinS = (TextView) getActivity().findViewById(R.id.inScripture);
        final TextView uinO = (TextView) getActivity().findViewById(R.id.inObserve);
        final TextView uinA = (TextView) getActivity().findViewById(R.id.inApply);
        final TextView uinP = (TextView) getActivity().findViewById(R.id.inPrayer);
        final TextView ubtnSave = (Button) getActivity().findViewById(R.id.btnSave);
        final TextView ubtnUpdate = (Button) getActivity().findViewById(R.id.btnUpdate);
        final TextView ubtnDel = (Button) getActivity().findViewById(R.id.btnDelete);
        final String[] req = new String[5];
        req[0] = inS.getText().toString();
        req[1] = inO.getText().toString();
        req[2] = inA.getText().toString();
        req[3] = inP.getText().toString();
        req[4] = cdate.getText().toString();
        AlertDialog.Builder upd = new AlertDialog.Builder(getActivity());
        upd.setCancelable(true)
                .setMessage("Do you want to save changes?")
                .setPositiveButton("Yes", new AlertDialog.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(dbHelp.updateSOAPEvening(req)) {
                            Toast.makeText(getContext(), "Updated.", Toast.LENGTH_SHORT).show();
                            btnSave.setEnabled(false);
                            btnUpdate.setEnabled(true);
                            btnDel.setEnabled(true);
                        } else {
                            Toast.makeText(getContext(), "Not updated.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("No", null);
        AlertDialog d = upd.create();
        d.show();
    }

    private void DelSOAPM(View view) {
        final DatabaseHelper dbHelp = new DatabaseHelper(getContext());
        final TextView dcdate = (TextView) getActivity().findViewById(R.id.curr_date);
        final TextView dinS = (TextView) getActivity().findViewById(R.id.inScripture);
        final TextView dinO = (TextView) getActivity().findViewById(R.id.inObserve);
        final TextView dinA = (TextView) getActivity().findViewById(R.id.inApply);
        final TextView dinP = (TextView) getActivity().findViewById(R.id.inPrayer);
        final Button dbtnSave = (Button) getActivity().findViewById(R.id.btnSave);
        final Button dbtnUpdate = (Button) getActivity().findViewById(R.id.btnUpdate);
        final Button dbtnDel = (Button) getActivity().findViewById(R.id.btnDelete);
        final String[] req = new String[5];
        req[0] = dinS.getText().toString();
        req[1] = dinO.getText().toString();
        req[2] = dinA.getText().toString();
        req[3] = dinP.getText().toString();
        req[4] = dcdate.getText().toString();
        AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
        build.setCancelable(true)
                .setMessage("Do you want to delete current journal?")
                .setPositiveButton("Yes", new AlertDialog.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(dbHelp.deleteSOAPMorning(req)) {
                            Toast.makeText(getActivity(), "Deleted.", Toast.LENGTH_SHORT).show();
                            dbtnSave.setEnabled(true);
                            dbtnUpdate.setEnabled(false);
                            dbtnDel.setEnabled(false);
                            dinS.setText(" ");
                            dinO.setText(" ");
                            dinA.setText(" ");
                            dinP.setText(" ");
                        } else {
                            Toast.makeText(getContext(), "Not deleted.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("No", null);
        AlertDialog b = build.create();
        b.show();

    }

    private void DelSOAPE(View view) {
        final DatabaseHelper dbHelp = new DatabaseHelper(getContext());
        final TextView dcdate = (TextView) getActivity().findViewById(R.id.curr_date);
        final TextView dinS = (TextView) getActivity().findViewById(R.id.inScripture);
        final TextView dinO = (TextView) getActivity().findViewById(R.id.inObserve);
        final TextView dinA = (TextView) getActivity().findViewById(R.id.inApply);
        final TextView dinP = (TextView) getActivity().findViewById(R.id.inPrayer);
        final Button dbtnSave = (Button) getActivity().findViewById(R.id.btnSave);
        final Button dbtnUpdate = (Button) getActivity().findViewById(R.id.btnUpdate);
        final Button dbtnDel = (Button) getActivity().findViewById(R.id.btnDelete);
        final String[] req = new String[5];
        req[0] = dinS.getText().toString();
        req[1] = dinO.getText().toString();
        req[2] = dinA.getText().toString();
        req[3] = dinP.getText().toString();
        req[4] = dcdate.getText().toString();
        AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
        build.setCancelable(true)
                .setMessage("Do you want to delete current journal?")
                .setPositiveButton("Yes", new AlertDialog.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(dbHelp.deleteSOAPEvening(req)) {
                            Toast.makeText(getActivity(), "Deleted.", Toast.LENGTH_SHORT).show();
                            dbtnSave.setEnabled(true);
                            dbtnUpdate.setEnabled(false);
                            dbtnDel.setEnabled(false);
                            dinS.setText(" ");
                            dinO.setText(" ");
                            dinA.setText(" ");
                            dinP.setText(" ");
                        } else {
                            Toast.makeText(getContext(), "Not deleted.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("No", null);
        AlertDialog b = build.create();
        b.show();
    }
}
