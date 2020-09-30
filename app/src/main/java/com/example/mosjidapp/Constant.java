package com.example.mosjidapp;

public class Constant {
    public  static final String DATABASE_NAME="Reminder.db";
    public  static final int DATABASE_Version=3;
    public  static final String TABLE_NAME="MosQueInFormation";
    public  static final String COLUMN_ID="id";
    public  static final String COLUMN_START_TIME="StartTime";
    public  static final String COLUMN_END_TIME="EndTime";
    public  static final String COLUMN_SWITCH_STATUS="SwitchStatus";
    public  static final String COLUMN_START_TIME_MILLI="StartTimeMilli";
    public  static final String COLUMN_END_TIME_MILLI="EndTimeMilli";
    public  static final String COLUMN_AUDIO_MODE="AudioMode";


    public static final String CREATE_TABLE  = " CREATE TABLE "+TABLE_NAME+"("
            +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COLUMN_START_TIME+" TEXT, "
            +COLUMN_END_TIME+" TEXT, "
            +COLUMN_START_TIME_MILLI+" TEXT, "
            +COLUMN_END_TIME_MILLI+" TEXT, "
            +COLUMN_SWITCH_STATUS+" INTEGER, "
            +COLUMN_AUDIO_MODE+" TEXT "
            +")";

}
