package com.rgrg.dailydevotion;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.rgrg.dailydevotion.controller.*;
import com.rgrg.dailydevotion.database.DatabaseHelper;

import java.io.InputStream;

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
        loadBg();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_con, new MainMenuFrag(), "MainMenuFrag").commit();
    }

    private void loadBg(){
        try {
            FrameLayout fl = (FrameLayout) findViewById(R.id.frag_con);
            InputStream is = getAssets().open("bg/ddev_bg.jpeg");
            Drawable d = Drawable.createFromStream(is, null);
            fl.setBackground(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed(){
        FragmentManager fragManager = getSupportFragmentManager();
        Fragment currPage = fragManager.findFragmentById(R.id.frag_con);
        switch(currPage.getTag()){
            case "MainMenuFrag":
                finish();
                break;
            case "TestamentFrag":
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.left_in,R.anim.fade_out).replace(R.id.frag_con, new MainMenuFrag(), "MainMenuFrag").commit();
                break;
            case "BookMenuFrag":
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.left_in,R.anim.fade_out).replace(R.id.frag_con, new TestamentFrag(), "TestamentFrag").commit();
                break;
            case "ChapterListFrag":
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.left_in,R.anim.fade_out).replace(R.id.frag_con, BookMenuFrag.main(ChapterListFrag.T_PART), "BookMenuFrag").commit();
                break;
            case "ChapterViewFrag":
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.left_in,R.anim.fade_out).replace(R.id.frag_con, ChapterListFrag.main(ChapterViewFrag.B_CHAPTERS, ChapterViewFrag.TYPE,  ChapterViewFrag.BOOKNAME), "ChapterListFrag").commit();
                break;
            case "InstructionFrag":
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.left_in,R.anim.fade_out).replace(R.id.frag_con, new MainMenuFrag(), "MainMenuFrag").commit();
                break;
            case "MonthListFrag":
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.left_in,R.anim.fade_out).replace(R.id.frag_con, new MainMenuFrag(), "MainMenuFrag").commit();
                break;
            case "CalendarViewFrag":
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.left_in,R.anim.fade_out).replace(R.id.frag_con, new MonthListFrag(), "MonthListFrag").commit();
                break;
            case "JournalFrag":
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.left_in,R.anim.fade_out).replace(R.id.frag_con, CalendarViewFrag.main(JournalFrag.MONTH), "CalendarViewFrag").commit();
                break;
            case "VerseViewFrag":
                String mnt = VerseViewFrag.MONTH,
                        day = VerseViewFrag.DAY,
                        yr = VerseViewFrag.YEAR;
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.left_in,R.anim.fade_out).replace(R.id.frag_con, JournalFrag.main(mnt, day, yr), "JournalFrag").commit();
                break;
        }
    }
}
