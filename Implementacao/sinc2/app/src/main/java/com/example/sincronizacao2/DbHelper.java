package com.example.sincronizacao2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final int VERSAO = 1;
    private static final String CREATE_TABLE = "create table "+ DbContract.NomeTabela
            +"(id integer primary key autoincrement,"+DbContract.Nome+" text,"
            +DbContract.syncStatus+" integer);" ;
    private static final String DROP_TABLE = "drop table if exists "+DbContract.NomeTabela;


    public DbHelper(Context context){
         super(context,DbContract.NomeBaseDeDados,null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void saveToLocalDataBase(String name, int syncStatus, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContract.Nome, name);
        contentValues.put(DbContract.syncStatus, syncStatus);
        database.insert(DbContract.NomeTabela, null, contentValues);
    }

    public Cursor readFromLocalDatabase (SQLiteDatabase database){
        String[] projection = {DbContract.Nome, DbContract.syncStatus};

        return (database.query(DbContract.NomeTabela, projection, null, null, null, null, null));
    }

    public void updateLocalDatabase(String nome, int syncStatus, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContract.syncStatus, syncStatus);
        String selection = DbContract.Nome+" LIKE ?";
        String[] selection_args = { nome };
        database.update(DbContract.NomeTabela, contentValues, selection, selection_args);
    }


}
