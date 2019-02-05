package com.rgrg.dailydevotion;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rgrg.dailydevotion.controller.BookMenuFrag;
import com.rgrg.dailydevotion.controller.CalendarViewFrag;
import com.rgrg.dailydevotion.controller.ChapterListFrag;
import com.rgrg.dailydevotion.controller.ChapterViewFrag;
import com.rgrg.dailydevotion.controller.JournalFrag;
import com.rgrg.dailydevotion.controller.LoadingScreenFrag;
import com.rgrg.dailydevotion.controller.MainMenuFrag;
import com.rgrg.dailydevotion.controller.MonthListFrag;
import com.rgrg.dailydevotion.controller.TestamentFrag;
import com.rgrg.dailydevotion.database.DatabaseHelper;
import com.rgrg.dailydevotion.notification.NotifReceiver;

import java.util.Calendar;

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
        schedJobM();
        schedJobE();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_con, new LoadingScreenFrag(), "LoadingScreenFrag").commit();
    }

    private void schedJobM() {
        Calendar Mrn = Calendar.getInstance();
        Mrn.set(Calendar.HOUR_OF_DAY, 6);
        Mrn.set(Calendar.MINUTE, 0);
        Mrn.set(Calendar.SECOND, 5);
        Mrn.set(Calendar.MILLISECOND, 0);

        Intent intent =  new Intent(getApplicationContext(), NotifReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(
                getApplicationContext(), 1010, intent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        AlarmManager alMn = (AlarmManager) getSystemService(ALARM_SERVICE);
        alMn.setRepeating(AlarmManager.RTC_WAKEUP, Mrn.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pIntent);
    }

    private void schedJobE() {
        Calendar Mrn = Calendar.getInstance();
        Mrn.set(Calendar.HOUR_OF_DAY, 18);
        Mrn.set(Calendar.MINUTE, 0);
        Mrn.set(Calendar.SECOND, 5);
        Mrn.set(Calendar.MILLISECOND, 0);

        Intent intent =  new Intent(getApplicationContext(), NotifReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(
                getApplicationContext(), 1010, intent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        AlarmManager alMn = (AlarmManager) getSystemService(ALARM_SERVICE);
        alMn.setRepeating(AlarmManager.RTC_WAKEUP, Mrn.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pIntent);
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
        }
    }
}
