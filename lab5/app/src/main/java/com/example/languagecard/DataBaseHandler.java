package com.example.languagecard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;


public class DataBaseHandler extends SQLiteOpenHelper {

    private final ArrayList<Word> wordItems = new ArrayList<>();

    public DataBaseHandler(@Nullable Context context){
        super(context, Constants.DATABASE_NAME,  null, Constants.DATABASE_VER);
        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(Constants.TABLE_NAME, null, null);      // when need to restart the database uncomment this line to reset the table.
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_DB_ITEM ="create Table " + Constants.TABLE_NAME + "(" + Constants.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Constants.MONGOLIA + " TEXT, " + Constants.ENGLISH + " TEXT);";
        sqLiteDatabase.execSQL(CREATE_DB_ITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + Constants.TABLE_NAME);
    }

    public long insertData(Word item){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.MONGOLIA, item.getMongolia());
        contentValues.put(Constants.ENGLISH, item.getEnglish());
        long result = DB.insert(Constants.TABLE_NAME, null, contentValues);
        return result;
    }

    public Boolean updateData(Word item){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.MONGOLIA, item.getMongolia());
        contentValues.put(Constants.ENGLISH, item.getEnglish());

        Cursor cursor = DB.rawQuery("Select * from " +Constants.TABLE_NAME +" where " + Constants.KEY_ID + " = ?", new String[]{String.valueOf(item.getId())});
        if(cursor.getCount() > 0){
            long result = DB.update(Constants.TABLE_NAME, contentValues, Constants.KEY_ID + " = ?", new String[]{String.valueOf(item.getId())});
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

    public Boolean deleteData(Word item){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from " +Constants.TABLE_NAME +" where " + Constants.KEY_ID + " = ?", new String[]{String.valueOf(item.getId())});
        if(cursor.getCount() > 0){
            long result = DB.delete(Constants.TABLE_NAME, Constants.KEY_ID+ " = ?", new String[]{String.valueOf(item.getId())});
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

    public ArrayList<Word> getWordItems(){
        wordItems.clear();
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from "+Constants.TABLE_NAME, null);

        if(cursor.getCount() ==0){
            return wordItems ;
        }
        else{
            Log.d("cursor", String.valueOf(cursor.getCount()));
            cursor.getPosition();

            while(cursor.moveToNext()){
                Word wordItem = new Word();
                wordItem.setId(cursor.getInt(0));
                wordItem.setMongolia(cursor.getString(1));
                wordItem.setEnglish(cursor.getString(2));
                wordItems.add(wordItem);
            }


        }
        DB.close();
        return wordItems;
    }





}
