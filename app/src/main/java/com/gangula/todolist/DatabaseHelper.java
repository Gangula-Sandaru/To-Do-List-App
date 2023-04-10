package com.gangula.todolist;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

import java.net.ConnectException;
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "to_do_list.db";
    public static final String TABLE_NAME= "student_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "TASK_TITLE";
    public static final String COL_3 = "TASK_DESCRIPTION";


    public DatabaseHelper(@Nullable Context context) {
        super(context,DATABASE_NAME, null, 1);

        // created a object  for created the database
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // SQL query for the database
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + "(ID INTEGER primary key autoincrement, " + "TASK_TITLE TEXT, TASK_DESCRIPTION TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public boolean insertData(String title, String description){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, title);
        contentValues.put(COL_3, description);


        long result  = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME , null);
        return result;

    }

    public boolean updateData(String id, String title, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, title);
        contentValues.put(COL_3, description);

        db.update(TABLE_NAME, contentValues, "id = ? ", new String[] {id});
        return  true;
    }


    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});

    }
}
