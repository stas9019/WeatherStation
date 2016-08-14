package com.android.stas.weatherstation.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by root on 10.07.16.
 */
public final class WeatherRecordContract {

    private WeatherRecordDbHelper mDbHelper;

    public WeatherRecordContract(Context context){
        mDbHelper = new WeatherRecordDbHelper(context);
    }

    public static final String[] PROJECTION = {
            WeatherEntry.COLUMN_NAME_DATE,
            WeatherEntry.COLUMN_NAME_TEMPERATURE,
            WeatherEntry.COLUMN_NAME_HUMIDITY,
            WeatherEntry._ID,
    };



    public void insert(String date, String temperature, String humidity ){
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(WeatherEntry.COLUMN_NAME_DATE, date);
        values.put(WeatherEntry.COLUMN_NAME_TEMPERATURE, temperature);
        values.put(WeatherEntry.COLUMN_NAME_HUMIDITY, humidity);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                WeatherEntry.TABLE_NAME,
                null,
                values);
    }

    public Cursor fetch(){

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String sortOrder = WeatherEntry.COLUMN_NAME_DATE + " DESC";

        Cursor c = db.query(
                WeatherEntry.TABLE_NAME,
                PROJECTION,
                null,
                null,
                null,
                null,
                sortOrder
                );

        return c;
    }
    public void clearTest() {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Define 'where' part of query.
        String selection = WeatherEntry.COLUMN_NAME_DATE + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {"test"};
        // Issue SQL statement.
        db.delete(WeatherEntry.TABLE_NAME, selection, selectionArgs);
    }

    public static abstract class WeatherEntry implements BaseColumns{

        public static final String TABLE_NAME = "record";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TEMPERATURE = "temperature";
        public static final String COLUMN_NAME_HUMIDITY = "humidity";

        private static final String TEXT_TYPE = " TEXT";
        private static final String DATE_TYPE = " INT";
        private static final String COMMA_SEP = ",";

        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + WeatherEntry.TABLE_NAME + " ( " +
                        WeatherEntry._ID + " INTEGER PRIMARY KEY," +
                        WeatherEntry.COLUMN_NAME_DATE + DATE_TYPE + COMMA_SEP +
                        WeatherEntry.COLUMN_NAME_TEMPERATURE + TEXT_TYPE + COMMA_SEP +
                        WeatherEntry.COLUMN_NAME_HUMIDITY + TEXT_TYPE +
                " )";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + WeatherEntry.TABLE_NAME;
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
            db.execSQL(WeatherEntry.SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(WeatherEntry.SQL_DELETE_ENTRIES);
            onCreate(db);
        }

//        @Override
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            // This database is only a cache for online data, so its upgrade policy is
//            // to simply to discard the data and start over
//            db.execSQL(WeatherEntry.SQL_DELETE_ENTRIES);
//            onCreate(db);
//        }
//        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            onUpgrade(db, oldVersion, newVersion);
//        }
    }

}
