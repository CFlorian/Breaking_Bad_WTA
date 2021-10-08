package com.cflorian.breakingbadwta.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DbData {
    private static final int DBVER = 1  ;
    private static final String DBNAME = "BreakingDB";
    private static final String tabla_characters = "characters";
    private static final String tabla_occupation = "occupation";
    private DBHelper conn;
    @SuppressWarnings("unused")
    private Context ctx;
    private String sResultado;

    public DbData(Context ctx) {
        this.ctx = ctx;
        conn = new DBHelper(ctx);
    }

    class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, DBNAME, null, DBVER);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String strSql = "CREATE TABLE "+ tabla_characters +" ("
                    + " char_id INTEGER,"
                    + " name TEXT,"
                    + " birthday TEXT,"
                    + " img TEXT,"
                    + " status TEXT,"
                    + " nickname TEXT,"
                    + " portrayed TEXT,"
                    + " category TEXT,"
                    + " fav SMALLINT )";
            db.execSQL(strSql);

            strSql = "CREATE TABLE "+ tabla_occupation +" ("
                    + " char_id INTEGER,"
                    + " occupation TEXT )";
            db.execSQL(strSql);


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (newVersion > oldVersion) {
            }
        }
    }

    public void InsertarCharacter(int char_id, String name, String birthday,
                                    String img, String status, String nickname,
                                    String portrayed, String category){
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("char_id",char_id);
        values.put("name",name);
        values.put("birthday",birthday);
        values.put("img",img);
        values.put("status",status);
        values.put("nickname",nickname);
        values.put("portrayed",portrayed);
        values.put("category",category);
        values.put("fav",0);
        db.insert(tabla_characters,null,values);
        db.close();
    }

    public void InsertarOccupation(int char_id, String name){
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("char_id",char_id);
        values.put("occupation",name);
        db.insert(tabla_occupation,null,values);
        db.close();
    }

    public ArrayList<Map> getAllCharacters (){
        SQLiteDatabase db = conn.getWritableDatabase();
        ArrayList<Map> mapArrayList = new ArrayList<>();
        String strSql = "SELECT * FROM "+tabla_characters + " ORDER BY fav DESC";
        Cursor cursor = db.rawQuery(strSql,null);
        if(cursor.moveToFirst()){
            do{
                Map<String, String> character = new HashMap<>();
                character.put("char_id", cursor.getString(0));
                character.put("name", cursor.getString(1));
                character.put("birthday", cursor.getString(2));
                character.put("img", cursor.getString(3));
                character.put("status", cursor.getString(4));
                character.put("nickname", cursor.getString(5));
                character.put("portrayed", cursor.getString(6));
                character.put("category", cursor.getString(7));
                character.put("fav", cursor.getString(8));
                mapArrayList.add(character);
            }while(cursor.moveToNext());
        }
        db.close();
        return mapArrayList;
    }

    public ArrayList<Map> getDetailOccupation (int id){
        SQLiteDatabase db = conn.getWritableDatabase();
        ArrayList<Map> mapArrayList = new ArrayList<>();
        String strSql = "SELECT occupation FROM "+tabla_occupation+" WHERE char_id = '" + id + "'";
        Cursor cursor = db.rawQuery(strSql,null);
        if(cursor.moveToFirst()){
            do{
                Map<String, String> occupation = new HashMap<>();
                occupation.put("occupation", cursor.getString(0));
                mapArrayList.add(occupation);
            }while(cursor.moveToNext());
        }
        db.close();
        return mapArrayList;
    }

    public void updateFav(int charId, int fav_boolean){
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fav", fav_boolean);
        db.update(tabla_characters,values,"char_id = " + charId,null);
        db.close();
    }

    public int obtenerFav(int id) {
        SQLiteDatabase db;
        db = conn.getWritableDatabase();
        int resultado = 3;
        Cursor allrows = db.rawQuery("SELECT fav FROM "+ tabla_characters +" WHERE char_id = '" + id + "'", null);
        if (allrows.moveToFirst()) {
            resultado = allrows.getInt(0);
        }
        allrows.close();
        db.close();
        return resultado;
    }

}
