package com.example.todo2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.provider.SyncStateContract;
import android.util.Log;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;



public class DataBaseHandler extends SQLiteOpenHelper {

    private final ArrayList<ToDoItem> toDoItems = new ArrayList<>();

    public DataBaseHandler(@Nullable Context context){
        super(context, Constants.DATABASE_NAME,  null, Constants.DATABASE_VER);
        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(Constants.TABLE_NAME, null, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_DB_ITEM ="create Table " + Constants.TABLE_NAME + "(" + Constants.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Constants.TODO + " TEXT, " + Constants.STATUS + " INTEGER, " + Constants.DATE +" TEXT);";
        sqLiteDatabase.execSQL(CREATE_DB_ITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + Constants.TABLE_NAME);
    }

    public long insertData(ToDoItem item){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//
        contentValues.put(Constants.STATUS, item.getStatus());
        contentValues.put(Constants.DATE, item.getEndDate().toString());
        contentValues.put(Constants.TODO, item.getToDO());
        long result = DB.insert(Constants.TABLE_NAME, null, contentValues);
        return result;
    }

    public Boolean updateData(ToDoItem item){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.TODO, item.getToDO());
        contentValues.put(Constants.STATUS, item.getStatus());
        contentValues.put(Constants.DATE, item.getEndDate().toString());

        Cursor cursor = DB.rawQuery("Select * from " +Constants.TABLE_NAME +" where " + Constants.KEY_ID + " = ?", new String[]{String.valueOf(item.getID())});
        if(cursor.getCount() > 0){
            long result = DB.update(Constants.TABLE_NAME, contentValues, Constants.KEY_ID + " = ?", new String[]{String.valueOf(item.getID())});
            if(result ==-1){
                return false;
            }
            else{
                return true;
            }
        }else{
            DB.close();
            return false;
        }
    }

    public Boolean deleteData(ToDoItem item){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from " +Constants.TABLE_NAME +" where " + Constants.KEY_ID + " = ?", new String[]{String.valueOf(item.getID())});
        if(cursor.getCount() > 0){
            long result = DB.delete(Constants.TABLE_NAME, Constants.KEY_ID+ " = ?", new String[]{String.valueOf(item.getID())});
            if(result ==-1){
                return false;
            }
            else{
                return true;
            }
        }else{
            DB.close();
            return false;
        }
    }

    public Cursor getData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from "+Constants.TABLE_NAME, null);
        return cursor;
    }

    public ArrayList<ToDoItem> getToDoItemList(){
        toDoItems.clear();
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from "+Constants.TABLE_NAME, null);

        if(cursor.getCount() ==0){
            return toDoItems ;
        }
        else{
            Log.d("cursor", String.valueOf(cursor.getCount()));
            cursor.getPosition();

            while(cursor.moveToNext()){
                ToDoItem toDoItem = new ToDoItem();
                toDoItem.setID(cursor.getInt(0));
                toDoItem.setToDO(cursor.getString(1));
                toDoItem.setStatus(cursor.getInt(2));
                toDoItem.setEndDate(LocalDate.parse(cursor.getString(3)));
                toDoItems.add(toDoItem);
            }


        }
        DB.close();
        return toDoItems;
    }





}
