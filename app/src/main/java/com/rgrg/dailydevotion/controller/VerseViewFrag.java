package com.rgrg.dailydevotion.controller;


import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rgrg.dailydevotion.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

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
        splitStrVerse(BIBLE_VERSE, view);
        return view;
    }

    private void splitStrVerse(String verse, View v) {
        if(!hasWhiteSpace(verse)){
            getAllChapters(verse, v);
        } else {
            if(!hasColon(verse)){
                extractChapters(verse, v);
            } else {
                extractVerses(verse, v);
            }
        }
    }

    private String getTestament(){
        String res = "";
        if(Calendar.getInstance().get(Calendar.AM_PM) == Calendar.AM){
            res = "bible/Old_Testament/";
        } else if(Calendar.getInstance().get(Calendar.AM_PM) == Calendar.PM){
            res = "bible/New_Testament/";
        }
        return res;
    }

    private boolean hasWhiteSpace(String str) {
        return str.contains(" ");
    }

    private boolean hasColon(String str) {
        return str.contains(":");
    }

    private void getAllChapters(String str, View v) {
        try {
            StringBuilder wholeBook = new StringBuilder();
            AssetManager assetManager = getActivity().getAssets();
            String[] files = assetManager.list(str);
            String test = getTestament();
            String dir = test + str + "/";
            for (String file : files) {
                StringBuilder strBuilder = new StringBuilder();
                try {
                    InputStream is = getActivity().getAssets().open(dir + file + ".txt");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        strBuilder.append(line);
                        strBuilder.append("\n\n");
                    }
                    strBuilder.append("\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                wholeBook.append(strBuilder.toString());
            }
            TextView txtView = (TextView) v.findViewById(R.id.chapter_cont);
            txtView.setText(wholeBook.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void extractChapters(String str, View v) {
        String[] ex = str.split(" ");
        String bookName = setBookName(ex[0], ex[1]);
        String DIR = getTestament() + bookName + "/";
        String[] chpt = chapterNums(ex);
        StringBuilder strBuild = new StringBuilder();
        for (String aChpt : chpt) {
            try {
                InputStream is = getActivity().getAssets().open(DIR + bookName + "_" + aChpt + ".txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = reader.readLine()) != null) {
                    strBuild.append(line);
                    strBuild.append("\n\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            strBuild.append("\n\n");
        }
        TextView txtView = v.findViewById(R.id.chapter_cont);
        txtView.setText(strBuild.toString());
    }

    private void extractVerses(String str, View v) {
        String[] ex = str.split(" ");
        String bookName = setBookName(ex[0], ex[1]);
        String DIR = getTestament() + bookName + "/";
        String[] verseRange = extChars(ex);
        int chpt = Integer.parseInt(verseRange[0]);
        int startVrs = Integer.parseInt(verseRange[1]) - 1;
        int endVrs = Integer.parseInt(verseRange[2]) - 1;
        StringBuilder strBuild = new StringBuilder();
        String file = DIR + bookName + "_" + Integer.toString(chpt) + ".txt";
        String[] fileLineArr = fileToArray(file);
        for(int i = 0; i < fileLineArr.length; i++){
            if(i >= startVrs && i <= endVrs){
                strBuild.append(fileLineArr[i]);
                strBuild.append("\n\n");
            }
        }
        TextView txtView = v.findViewById(R.id.chapter_cont);
        txtView.setText(strBuild.toString());
    }

    private int countLine(String file) {
        int cnt = 0;
        try {
            InputStream is = getActivity().getAssets().open(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            while(reader.readLine() != null){
                cnt++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cnt;
    }

    private String[] fileToArray(String file){
        String[] res = new String[countLine(file)];
        try {
            InputStream is = getActivity().getAssets().open(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            int cnt = -1;
            while((line = reader.readLine()) != null){
                cnt++;
                res[cnt] = line;
                Log.e("Verse", line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    private String[] extChars(String[] str) {
        String verse = "";
        for (String aStr : str) {
            if (aStr.contains(":")) {
                verse = aStr;
            }
        }
        verse = verse.replace(":", " ");
        verse = verse.replace("-", " ");
        return verse.split(" ");
    }

    private String setBookName(String... str){
        String res;
        if(checkBookCount(str[0])){
            res = str[0] + "_" + str[1];
        } else {
            res = str[0];
        }
        return res;
    }

    private String[] chapterNums(String[] str) {
        int startCnt;
        if(checkBookCount(str[0])){
            startCnt = 2;
        } else {
            startCnt = 1;
        }
        String[] chptrs  = new String[str.length];
        for(int i = 0; i < str.length; i++){
            if(i >= startCnt){
                chptrs[i] = str[i];
            }
        }
        return chptrs;
    }

    private boolean checkBookCount(String str) {
        try{
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
