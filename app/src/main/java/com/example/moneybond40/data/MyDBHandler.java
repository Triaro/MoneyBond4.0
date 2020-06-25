package com.example.moneybond40.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.moneybond40.model.Name;
import com.example.moneybond40.params.Params;

import java.util.ArrayList;
import java.util.List;

import static com.example.moneybond40.params.Params.TABLE_NAME2;

public class MyDBHandler extends SQLiteOpenHelper {
    public MyDBHandler(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create= "CREATE TABLE " + Params.TABLE_NAME + "("
                + Params.KEY_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + Params.KEY_NAME + " TEXT, " +
                Params.KEY_MONEY+ " TEXT, "+ Params.KEY_NUMBER +  " TEXT " +")";
        Log.d("dbAbhi", "Query being run is "+ create);
        db.execSQL(create);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        }

    public void addName(Name name){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(Params.KEY_ID, name.getId());
        values.put(Params.KEY_NAME, name.getName());
        values.put(Params.KEY_MONEY, name.getMoney());
        values.put(Params.KEY_NUMBER, name.getNumber());


        db.insert(Params.TABLE_NAME, null, values);
        Log.d("dbAbhi","Successfully Inserted");
        db.close();
    }

    public List<Name> getAllNames(){
        List<Name> nameList= new ArrayList<>();
        SQLiteDatabase db= this.getReadableDatabase();

        //generate the query to read from the database
        String select = "SELECT * FROM " + Params.TABLE_NAME;
        Cursor cursor= db.rawQuery(select, null);

        //Loop through row
        if(cursor.moveToFirst()){
            do {
                Name name = new Name();
                name.setId(Integer.parseInt(cursor.getString(0)));
                name.setName(cursor.getString(1));
                name.setMoney(cursor.getString(2));
                name.setNumber(cursor.getString(3));
                nameList.add(name);
            }while(cursor.moveToNext());
        }
        Log.d("ListCreated","List Successfully Created");
        return nameList;


    }


    public int updateName(Name name){
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(Params.KEY_NAME, name.getName());
        values.put(Params.KEY_MONEY, name.getMoney());
        values.put(Params.KEY_NUMBER, name.getNumber());

        //Lets update now
        return db.update(Params.TABLE_NAME, values, Params.KEY_ID
                + "=?", new String[]{String.valueOf(name.getId())});
    }

    public void deleteName(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Params.TABLE_NAME, Params.KEY_ID +"=?", new String[]{String.valueOf(id)});
        db.close();

        Log.d("delete", "DB Deleted Successfully");
    }
}
