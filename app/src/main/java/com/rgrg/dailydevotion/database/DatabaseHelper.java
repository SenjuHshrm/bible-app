package com.rgrg.dailydevotion.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper{
    static String DB_PATH = "/data/data/com.rgrg.dailydevotion/databases/";
    static String DB_NAME = "daily_dev_journal.db";
    SQLiteDatabase db;
    private final Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * This method will create database in application package /databases
     * directory when first time application launched
     **/
    public void createDataBase() throws Exception {
        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (Exception mIOException) {
                mIOException.printStackTrace();
                throw new Error("Error copying database");
            } finally {
                this.close();
            }
        }
    }

    /** This method checks whether database is exists or not **/
    private boolean checkDataBase() {
        try {
            final String mPath = DB_PATH + DB_NAME;
            final File file = new File(mPath);
            if (file.exists())
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method will copy database from /assets directory to application
     * package /databases directory
     **/
    private void copyDataBase() throws Exception {
        try {
            String db_path = "db/" + DB_NAME;
            InputStream mInputStream = mContext.getAssets().open(db_path);
            String outFileName = DB_PATH + DB_NAME;
            OutputStream mOutputStream = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = mInputStream.read(buffer)) > 0) {
                mOutputStream.write(buffer, 0, length);
            }
            mOutputStream.flush();
            mOutputStream.close();
            mInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** This method open database for operations **/
    public boolean openDataBase() throws Exception {
        String mPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(mPath, null,
                SQLiteDatabase.OPEN_READWRITE);
        return db.isOpen();
    }

    /** This method close database connection and released occupied memory **/
    @Override
    public synchronized void close() {
        if (db != null)
            db.close();
        SQLiteDatabase.releaseMemory();
        super.close();
    }

    public String getVerseMorning(String date){
        db = this.getWritableDatabase();
        Cursor req = db.query("verses", new String[]{"morning"}, "date = ?", new String[]{date}, null, null, null);
        StringBuilder str = new StringBuilder();
        while(req.moveToNext()){
            str.append(req.getString(0));
        }
        req.close();
        return str.toString();
    }

    public String getVerseEvening(String date){
        db = this.getWritableDatabase();
        Cursor req = db.query("verses", new String[]{"evening"}, "date = ?", new String[]{date}, null, null, null);
        StringBuilder str = new StringBuilder();
        while(req.moveToNext()){
            str.append(req.getString(0));
        }
        req.close();
        return str.toString();
    }

    public String[] getSOAPMorning(String date){
        String[] res = new String[5];
        db = this.getWritableDatabase();
        Cursor req = db.query("journal", new String[]{"scripture","observe","apply","pray"}, "date = ? AND time = ?", new String[]{date, "morning"}, null, null, null);
        while(req.moveToNext()){
            res[0] = req.getString(0);
            res[1] = req.getString(1);
            res[2] = req.getString(2);
            res[3] = req.getString(3);
        }
        req.close();
        return res;
    }

    public String[] getSOAPEvening(String date){
        String[] res = new String[5];
        db = this.getWritableDatabase();
        Cursor req = db.query("journal", new String[]{"scripture","observe","apply","pray"}, "date = ? AND time = ?", new String[]{date, "evening"}, null, null, null);
        while(req.moveToNext()){
            res[0] = req.getString(0);
            res[1] = req.getString(1);
            res[2] = req.getString(2);
            res[3] = req.getString(3);
        }
        req.close();
        return res;
    }

    public boolean saveSOAPMorning(String[] req){
        boolean res;
        db = this.getWritableDatabase();
        ContentValues contVal = new ContentValues();
        contVal.put("date", req[4]);
        contVal.put("scripture", req[0]);
        contVal.put("observe", req[1]);
        contVal.put("apply", req[2]);
        contVal.put("pray", req[3]);
        contVal.put("time", "morning");
        long response = db.insert("journal", null, contVal);
        if(response == -1){
            res = false;
        } else {
            res = true;
        }
        return res;
    }

    public boolean saveSOAPEvening(String[] req){
        boolean res;
        db = this.getWritableDatabase();
        ContentValues contVal = new ContentValues();
        contVal.put("date", req[4]);
        contVal.put("scripture", req[0]);
        contVal.put("observe", req[1]);
        contVal.put("apply", req[2]);
        contVal.put("pray", req[3]);
        contVal.put("time", "evening");
        long response = db.insert("journal", null, contVal);
        if(response == -1){
            res = false;
        } else {
            res = true;
        }
        return res;
    }

    public boolean updateSOAPMorning(String[] req){
        boolean res;
        db = this.getWritableDatabase();
        ContentValues contVal = new ContentValues();
        contVal.put("date", req[4]);
        contVal.put("scripture", req[0]);
        contVal.put("observe", req[1]);
        contVal.put("apply", req[2]);
        contVal.put("pray", req[3]);
        contVal.put("time", "morning");
        long response = db.update("journal", contVal, "date = ? AND time = ?", new String[]{req[4], "morning"});
        if(response == -1){
            res = false;
        } else {
            res = true;
        }
        return res;
    }

    public boolean updateSOAPEvening(String[] req){
        boolean res;
        db = this.getWritableDatabase();
        ContentValues contVal = new ContentValues();
        contVal.put("date", req[4]);
        contVal.put("scripture", req[0]);
        contVal.put("observe", req[1]);
        contVal.put("apply", req[2]);
        contVal.put("pray", req[3]);
        contVal.put("time", "evening");
        long response = db.update("journal", contVal, "date = ? AND time = ?", new String[]{req[4], "evening"});
        if(response == -1){
            res = false;
        } else {
            res = true;
        }
        return res;
    }

    public boolean deleteSOAPMorning(String[] req){
        boolean res;
        db = this.getWritableDatabase();
        long response = db.delete("journal", "date = ? AND time = ?", new String[]{req[4], "morning"});
        if(response == -1){
            res = false;
        } else {
            res = true;
        }
        return res;
    }

    public boolean deleteSOAPEvening(String[] req){
        boolean res;
        db = this.getWritableDatabase();
        long response = db.delete("journal", "date = ? AND time = ?", new String[]{req[4], "evening"});
        if(response == -1){
            res = false;
        } else {
            res = true;
        }
        return res;
    }

    public String[] CheckSOAPMorning(String date){
        String[] res = new String[5];
        db = this.getWritableDatabase();
        Cursor req = db.query("journal", new String[]{"scripture","observe","apply","pray"}, "date = ? AND time = ?", new String[]{date, "morning"}, null, null, null);
        if(req.getCount() == 0){
            res = null;
        }else{
            while(req.moveToNext()){
                res[0] = req.getString(0);
                res[1] = req.getString(1);
                res[2] = req.getString(2);
                res[3] = req.getString(3);
            }
        }
        req.close();
        return res;
    }

    public String[] CheckSOAPEvening(String date){
        String[] res = new String[5];
        db = this.getWritableDatabase();
        Cursor req = db.query("journal", new String[]{"scripture","observe","apply","pray"}, "date = ? AND time = ?", new String[]{date, "evening"}, null, null, null);
        if(req.getCount() == 0){
            res = null;
        } else {
            while(req.moveToNext()){
                res[0] = req.getString(0);
                res[1] = req.getString(1);
                res[2] = req.getString(2);
                res[3] = req.getString(3);
            }
        }
        req.close();
        return res;
    }

}
