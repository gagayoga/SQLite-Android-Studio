package com.example.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.text.Spannable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME="student_db";

    private static final int DATABASE_VERSION=1;

    private static final String TABLE_STUDENTS="students";

    private static final String KEY_NAME="name";

    private static final String KEY_ID="id";

    private static final String KEY_ADDRESS ="address";

    private static final String CREATE_TABLE_STUDENTS=
            "CREATE TABLE "+TABLE_STUDENTS
            +"("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +KEY_NAME+" TEXT,"
            +KEY_ADDRESS+" TEXT)";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_STUDENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '"+TABLE_STUDENTS+"'");
    }

    public long addStudents(String name, String address){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(KEY_NAME,name);
        values.put(KEY_ADDRESS, address);
        long insert=db.insert(TABLE_STUDENTS, null, values);
        return insert;
    }

    public ArrayList<Map<String, String>> getAllStudents(){
        ArrayList<Map<String, String>> arrayList = new ArrayList<>();
        String name="";
        String address="";
        int id=0;
        String sqlQuery="SELECT * FROM "+TABLE_STUDENTS;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c=db.rawQuery(sqlQuery, null);

        if(c.moveToFirst()){
            do {
                id=c.getInt(c.getColumnIndexOrThrow(KEY_ID));
                name=c.getString(c.getColumnIndexOrThrow(KEY_NAME));
                address=c.getString(c.getColumnIndexOrThrow(KEY_ADDRESS));
                Map<String, String> item=new HashMap<>();
                item.put(KEY_ID, id+"");
                item.put(KEY_NAME, name);
                item.put(KEY_ADDRESS, address);
                arrayList.add(item);

            }while (c.moveToNext());
        }
        return arrayList;
    }
}
