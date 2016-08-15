package com.android.stas.weatherstation.Interactor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.android.stas.weatherstation.model.WeatherEntry;

/**
 * Created by root on 10.07.16.
 */
public final class WeatherRecordContract {

    private WeatherRecordDbHelper mDbHelper;

    public WeatherRecordContract(Context context){
        mDbHelper = new WeatherRecordDbHelper(context);
    }

    public static final String[] PROJECTION = {
            WeatherTableEntry.COLUMN_NAME_DATE,
            WeatherTableEntry.COLUMN_NAME_TEMPERATURE,
            WeatherTableEntry.COLUMN_NAME_HUMIDITY,
            WeatherTableEntry._ID,
    };

    private static final int DATE_COLUMN = 2;
    private static final int TEMP_COLUMN = 3;
    private static final int HUM_COLUMN = 4;



    public void insert(String date, String temperature, String humidity ){
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(WeatherTableEntry.COLUMN_NAME_DATE, date);
        values.put(WeatherTableEntry.COLUMN_NAME_TEMPERATURE, temperature);
        values.put(WeatherTableEntry.COLUMN_NAME_HUMIDITY, humidity);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                WeatherTableEntry.TABLE_NAME,
                null,
                values);
    }

    public Cursor fetch(){
        return fetch(false);
    }

    public WeatherEntry fetchLast(){


        Cursor c = fetch(true);
        WeatherEntry entry = new WeatherEntry();

        if(c.moveToFirst()){
            entry.date = c.getString(c.getColumnIndex(WeatherTableEntry.COLUMN_NAME_DATE));
            entry.temperature = c.getString(c.getColumnIndex(WeatherTableEntry.COLUMN_NAME_TEMPERATURE));
            entry.humidity = c.getString(c.getColumnIndex(WeatherTableEntry.COLUMN_NAME_HUMIDITY));
        }

        return entry;
    }

    private Cursor fetch(boolean onlyLast){

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String sortOrder = WeatherTableEntry.COLUMN_NAME_DATE + " DESC";

        String limit =  onlyLast? "1" : null;

        Cursor c = db.query(
                WeatherTableEntry.TABLE_NAME,
                PROJECTION,
                null,
                null,
                null,
                null,
                sortOrder,
                limit
                );

        return c;
    }
    public void clearTest() {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Define 'where' part of query.
        String selection = WeatherTableEntry.COLUMN_NAME_DATE + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {"today"};
        // Issue SQL statement.
        db.delete(WeatherTableEntry.TABLE_NAME, selection, selectionArgs);
    }

    public static abstract class WeatherTableEntry implements BaseColumns{

        public static final String TABLE_NAME = "record";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TEMPERATURE = "temperature";
        public static final String COLUMN_NAME_HUMIDITY = "humidity";

        private static final String TEXT_TYPE = " TEXT";
        private static final String DATE_TYPE = " INT";
        private static final String COMMA_SEP = ",";

        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + WeatherTableEntry.TABLE_NAME + " ( " +
                        WeatherTableEntry._ID + " INTEGER PRIMARY KEY," +
                        WeatherTableEntry.COLUMN_NAME_DATE + DATE_TYPE + COMMA_SEP +
                        WeatherTableEntry.COLUMN_NAME_TEMPERATURE + TEXT_TYPE + COMMA_SEP +
                        WeatherTableEntry.COLUMN_NAME_HUMIDITY + TEXT_TYPE +
                " )";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + WeatherTableEntry.TABLE_NAME;
    }
    public class WeatherRecordDbHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "WeatherRecord.db";

        public WeatherRecordDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(WeatherTableEntry.SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(WeatherTableEntry.SQL_DELETE_ENTRIES);
            onCreate(db);
        }

//        @Override
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            // This database is only a cache for online data, so its upgrade policy is
//            // to simply to discard the data and start over
//            db.execSQL(WeatherTableEntry.SQL_DELETE_ENTRIES);
//            onCreate(db);
//        }
//        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            onUpgrade(db, oldVersion, newVersion);
//        }
    }

}
