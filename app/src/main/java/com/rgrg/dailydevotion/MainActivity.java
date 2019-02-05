package com.rgrg.dailydevotion;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
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
import com.rgrg.dailydevotion.notification.NotifyUser;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private JobScheduler mSched;
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
        String timeStr = "06:00 am";
        long timeInMilli = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm aaa");
        try{
            Date mDate = sdf.parse(timeStr);
            timeInMilli = mDate.getTime();
            mSched = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            ComponentName serviceName = new ComponentName(getPackageName(), NotifyUser.class.getName());
            JobInfo.Builder build = new JobInfo.Builder(0, serviceName)
                    .setPeriodic(timeInMilli);
            JobInfo jInfo = build.build();
            mSched.schedule(jInfo);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void schedJobE() {
        String timeStr = "06:00 pm";
        long timeInMilli = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm aaa");
        try{
            Date mDate = sdf.parse(timeStr);
            timeInMilli = mDate.getTime();
            mSched = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            ComponentName serviceName = new ComponentName(getPackageName(), NotifyUser.class.getName());
            JobInfo.Builder build = new JobInfo.Builder(0, serviceName)
                    .setPeriodic(timeInMilli);
            JobInfo jInfo = build.build();
            mSched.schedule(jInfo);

        } catch (Exception e) {
            e.printStackTrace();
        }

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
