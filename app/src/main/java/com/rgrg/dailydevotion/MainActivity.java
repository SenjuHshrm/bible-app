package com.rgrg.dailydevotion;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.rgrg.dailydevotion.controller.*;
import com.rgrg.dailydevotion.database.DatabaseHelper;
import com.rgrg.dailydevotion.notification.*;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            DatabaseHelper a = new DatabaseHelper(this);
            a.createDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Intent intent_O = new Intent(this, DailyDevotionServicev2.class);
        Intent intent_low = new Intent(this, DailyDevotionServicev1.class);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            DailyDevotionServicev2.enqueueWork(this, intent_O);
//        } else if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.N){
//            startService(intent_low);
//        }
        ContextCompat.startForegroundService(this, intent_low);
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_con, new LoadingScreenFrag(), "LoadingScreenFrag").commit();
    }

    @Override
    public void onBackPressed(){
        FragmentManager fragManager = getSupportFragmentManager();
        Fragment currPage = fragManager.findFragmentById(R.id.frag_con);
        switch(currPage.getTag()){
            case "LoadingScreenFrag":
                finish();
                break;
            case "MainMenuFrag":
                finish();
                break;
            case "TestamentFrag":
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_con, new MainMenuFrag(), "MainMenuFrag").commit();
                break;
            case "BookMenuFrag":
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_con, new TestamentFrag(), "TestamentFrag").commit();
                break;
            case "ChapterListFrag":
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_con, BookMenuFrag.main(ChapterListFrag.T_PART), "BookMenuFrag").commit();
                break;
            case "ChapterViewFrag":
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_con, ChapterListFrag.main(ChapterViewFrag.B_CHAPTERS, ChapterViewFrag.TYPE,  ChapterViewFrag.BOOKNAME), "ChapterListFrag").commit();
                break;
            case "InstructionFrag":
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_con, new MainMenuFrag(), "MainMenuFrag").commit();
                break;
            case "MonthListFrag":
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_con, new MainMenuFrag(), "MainMenuFrag").commit();
                break;
            case "CalendarViewFrag":
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_con, new MonthListFrag(), "MonthListFrag").commit();
                break;
            case "JournalFrag":
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_con, CalendarViewFrag.main(JournalFrag.MONTH), "CalendarViewFrag").commit();
                break;
            case "VerseViewFrag":
                String mnt = VerseViewFrag.MONTH,
                        day = VerseViewFrag.DAY,
                        yr = VerseViewFrag.YEAR;
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_con, JournalFrag.main(mnt, day, yr), "JournalFrag").commit();
                break;
        }
    }
}
