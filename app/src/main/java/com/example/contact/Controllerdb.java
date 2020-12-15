package com.example.contact;
import android.content.ContentValues;
import  android.content.Context;
import android.database.SQLException;
import  android.database.sqlite.SQLiteDatabase;
import  android.database.sqlite.SQLiteOpenHelper;

public class Controllerdb extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME="SqliteListviewDB";
    public static final String KEY_ID="id";
    public static final String KEY_Organization="organization";
    public static final String KEY_Name="name";
    public static final String KEY_Number="number";


    public Controllerdb(Context applicationcontext) {
        super(applicationcontext, DATABASE_NAME, null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table to insert data
        String query;
        query = "CREATE TABLE IF NOT EXISTS UserDetails(Organization VARCHAR,Name VARCHAR,Number INTEGER UNIQUE);";
        db.execSQL(query);


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query ;
        query = "DROP TABLE IF EXISTS UserDetails";
        db.execSQL(query);
        onCreate(db);
    }

    public boolean UpdateData(String Organization,String Name,String Number, String[] oldervalues) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("organization",Organization);
        contentValues.put("name",Name);
        contentValues.put("number",Number);
        String whereClause = "Organization=? AND Name=? AND Number=?";
        String whereArgs[] = {oldervalues[0].toString(), oldervalues[1].toString(), oldervalues[2].toString()};
//        db.update( );
        db.update("UserDetails",
                contentValues,
                whereClause,
                whereArgs);

        return true;
    }

    public Boolean removeSingleContact(String orgnization, String name, String nu) {
        //Open the database
        SQLiteDatabase database = this.getWritableDatabase();

        //Execute sql query to remove from database
        //NOTE: When removing by String in SQL, value must be enclosed with ''
        String arg[] = {orgnization.toString(), name.toString(), nu.toString()};
        String whereClause = "Organization=? AND Name=? AND Number=?";
        try {
            database.delete("UserDetails",whereClause,arg);
            return true;
        }catch (SQLException se) {
            return false;
        }

        //Close the database
    }
}