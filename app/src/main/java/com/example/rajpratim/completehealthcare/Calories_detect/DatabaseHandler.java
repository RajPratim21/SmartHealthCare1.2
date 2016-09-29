package com.example.rajpratim.completehealthcare.Calories_detect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RajPratim on 7/7/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASEVERSION = 1;

    private static final String DATABASENAME = "acceralationManager";

    private static final String TABLE_CONTACTS = "acceralationData";

    private static final String KEY_ID = "id";
    private static final String KEY_DATA = "data";


    public DatabaseHandler(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATA + " TEXT"
                 + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    void addData(AceralationData aceralationData)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, aceralationData.get_id());
        values.put(KEY_DATA, aceralationData.getAcData());

        db.insert(TABLE_CONTACTS,null,values);
        db.close();
    }

    AceralationData getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_DATA }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if(cursor!= null)
        {
            cursor.moveToFirst();
        }

        AceralationData aceralationData= new AceralationData(Integer.parseInt(cursor.getString(0)),
                Float.parseFloat(cursor.getString(1)));
        return aceralationData;
    }

    public int updateData(AceralationData aceralationData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATA, aceralationData.getAcData());


        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(aceralationData.get_id()) });
    }

    public void deleteData(AceralationData aceralationData) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(aceralationData.get_id()) });
        db.close();
    }


    // Getting contacts Count
    public int getDataCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
    public List<AceralationData> getAllData(){
        List<AceralationData> dataList= new ArrayList<AceralationData>();

        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                AceralationData aceralationData = new AceralationData();
                aceralationData.set_id(Integer.parseInt(cursor.getString(0)));
                aceralationData.set_data(Float.parseFloat(cursor.getString(1)));
                dataList.add(aceralationData);
            }while (cursor.moveToNext());
    }
    return dataList;
    }
}
