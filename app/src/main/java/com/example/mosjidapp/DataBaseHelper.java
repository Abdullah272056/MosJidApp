package com.example.mosjidapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    Context context;
    public DataBaseHelper(@Nullable Context context) {
        super(context, Constant.TABLE_NAME, null, Constant.DATABASE_Version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constant.CREATE_TABLE);
        Toast.makeText(context, "OnCreate is Called", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+Constant.TABLE_NAME);
        onCreate(db);
        Toast.makeText(context, "onUpgrade is Called", Toast.LENGTH_SHORT).show();


    }

    public List<Notes> getAllNotes(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        List<Notes> dataList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+Constant.TABLE_NAME,null);
        if (cursor.moveToFirst()){
            do {
                Notes note = new Notes(cursor.getInt(cursor.getColumnIndex(Constant.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(Constant.COLUMN_START_TIME)),
                        cursor.getString(cursor.getColumnIndex(Constant.COLUMN_END_TIME)),
                        cursor.getString(cursor.getColumnIndex(Constant.COLUMN_START_TIME_MILLI)),
                        cursor.getString(cursor.getColumnIndex(Constant.COLUMN_END_TIME_MILLI)),
                        cursor.getString(cursor.getColumnIndex(Constant.COLUMN_AUDIO_MODE)),
                        cursor.getInt(cursor.getColumnIndex(Constant.COLUMN_SWITCH_STATUS))
                );

                dataList.add(note);
            }while (cursor.moveToNext());
        }
        return dataList;
    }

    public int insertData(Notes notes){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Constant.COLUMN_START_TIME,notes.getStartTime());
        contentValues.put(Constant.COLUMN_END_TIME,notes.getEntTime());
        contentValues.put(Constant.COLUMN_START_TIME_MILLI,notes.getStartTimeMilli());
        contentValues.put(Constant.COLUMN_END_TIME_MILLI,notes.getEndTimeMilli());
        contentValues.put(Constant.COLUMN_SWITCH_STATUS,notes.getSwitchStatus());
        contentValues.put(Constant.COLUMN_AUDIO_MODE,notes.getAudioMode());
        int id= (int) sqLiteDatabase.insert(Constant.TABLE_NAME,null,contentValues);
        return id;
    }





}
