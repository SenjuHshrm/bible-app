package com.rgrg.dailydevotion;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

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
        loadSearchIcon();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_con, new MainMenuFrag(), "MainMenuFrag").commit();
    }

    private void loadSearchIcon() {
        try {
            Button b = (Button) findViewById(R.id.appBackBtn);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            ImageButton ib = (ImageButton) findViewById(R.id.searchBtn);
            InputStream is = getAssets().open("ic/search.png");
            Drawable d = Drawable.createFromStream(is,null);
            ib.setImageDrawable(d);
            ib.setVisibility(View.VISIBLE);
            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageButton ib = (ImageButton) findViewById(R.id.searchBtn);
                    ib.setVisibility(View.INVISIBLE);
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.left_out)
                            .replace(R.id.frag_con, new SearchPrevFrag(), "SearchPrevFrag").commit();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed(){
        ImageButton ib = (ImageButton) findViewById(R.id.searchBtn);
        FragmentManager fragManager = getSupportFragmentManager();
        Fragment currPage = fragManager.findFragmentById(R.id.frag_con);
        switch(currPage.getTag()){
            case "MainMenuFrag":
                finish();
                break;
            case "TestamentFrag":
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.left_in,R.anim.fade_out).replace(R.id.frag_con, new MainMenuFrag(), "MainMenuFrag").commit();
                ib.setVisibility(View.VISIBLE);
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
                ib.setVisibility(View.VISIBLE);
                break;
            case "MonthListFrag":
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.left_in,R.anim.fade_out).replace(R.id.frag_con, new MainMenuFrag(), "MainMenuFrag").commit();
                ib.setVisibility(View.VISIBLE);
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
            case "SearchPrevFrag":
                ib.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.left_in,R.anim.fade_out).replace(R.id.frag_con, new MainMenuFrag(), "MainMenuFrag").commit();
                break;
            case "DevViewerFrag":
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.left_in,R.anim.fade_out)
                        .replace(R.id.frag_con,new SearchPrevFrag(),"SearchPrevFrag").commit();
        }
    }
}
