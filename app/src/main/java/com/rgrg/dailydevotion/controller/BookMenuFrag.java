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
public class BookMenuFrag extends Fragment {

    private static final String[] oldBook = {"Genesis","Exodus","Leviticus","Numbers","Deuteronomy",
            "Joshua","Judges","Ruth","1 Samuel","2 Samuel",
            "1 Kings","2 Kings","1 Chronicles","2 Chronicles","Ezra",
            "Nehemiah","Esther","Job","Psalms","Proverbs",
            "Ecclesiastes","Song Of Solomon","Isaiah","Jeremiah","Lamentations",
            "Ezekiel","Daniel","Hosea","Joel","Amos",
            "Obadiah","Jonah","Micah","Nahum","Habakkuk",
            "Zephaniah","Haggai","Zechariah","Malachi"};
    private static final String[] oldChp = {"50","40","27","36","20",
            "24","21","4","31","24",
            "22","25","29","36","10",
            "13","10","42","150","31",
            "12","8","66","52","5",
            "48","12","14","3","9",
            "1","4","7","3","3",
            "3","2","14","4"};
    private static final String[] newBook = {"Matthew","Mark","Luke","John","Acts",
            "Romans","1 Corinthians","2 Corinthians","Galatians","Ephesians",
            "Philippians","Colossians","1 Thessalonians","2 Thessalonians","1 Timothy",
            "2 Timothy","Titus","Philemon","Hebrews","James",
            "1 Peter","2 Peter","1 John","2 John","3 John",
            "Jude","Revelation"};
    private static final String[] newChp = {"28","16","24","21","28",
            "16","16","13","6","6",
            "4","4","5","3","6",
            "4","3","1","13","5",
            "5","3","5","1","1",
            "1","22"};
    private String arg;
    public BookMenuFrag() {
        // Required empty public constructor
    }

    public static BookMenuFrag main(String part) {
        BookMenuFrag frag = new BookMenuFrag();
        Bundle args = new Bundle();
        args.putString("PART", part);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_menu, container, false);
        arg = getArguments().getString("PART");
        ListView ls = (ListView) view.findViewById(R.id.BookList);
        switch(arg){
            case "OLD":
                ListOldTestament(ls);
                break;
            case "NEW":
                ListNewTestament(ls);
                break;
        }
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView booknm = (TextView) view.findViewById(R.id.BookName);
                TextView chptr = (TextView) view.findViewById(R.id.ChapterNum);
                final String bkName = booknm.getText().toString();
                final int chapters = Integer.parseInt(chptr.getText().toString());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frag_con, ChapterListFrag.main(chapters, arg, bkName), "ChapterListFrag").commit();
            }
        });
        return view;
    }

    private void ListOldTestament(ListView ls){
        List<Map<String,String>> oldBk = new ArrayList<Map<String,String>>();
        for(int i = 0; i < oldBook.length; i++){
            Map<String,String> datum = new HashMap<String,String>(2);
            datum.put("Book", oldBook[i]);
            datum.put("Chapters", oldChp[i]);
            oldBk.add(datum);
        }
        SimpleAdapter adapter = new SimpleAdapter(getContext(), oldBk, R.layout.layout_book_listview, new String[]{"Book", "Chapters"}, new int[]{R.id.BookName, R.id.ChapterNum});
        ls.setAdapter(adapter);
    }

    private void ListNewTestament(ListView ls){
        List<Map<String,String>> oldBk = new ArrayList<Map<String,String>>();
        for(int i = 0; i < newBook.length; i++){
            Map<String,String> datum = new HashMap<String,String>(2);
            datum.put("Book", newBook[i]);
            datum.put("Chapters", newChp[i]);
            oldBk.add(datum);
        }
        SimpleAdapter adapter = new SimpleAdapter(getContext(), oldBk, R.layout.layout_book_listview, new String[]{"Book", "Chapters"}, new int[]{R.id.BookName, R.id.ChapterNum});
        ls.setAdapter(adapter);
    }


}
