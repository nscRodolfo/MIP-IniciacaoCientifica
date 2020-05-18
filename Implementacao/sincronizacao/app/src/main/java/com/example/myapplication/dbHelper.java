package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper {


    private static final int VERSAO = 1;
    private static final String createTable = "create table "+ dbContato.Tabela
            +"(id integer primary key autoincrement,"+dbContato.nome+" text,"
            +dbContato.syncStatus+" integer);" ;
    private static final String dropTable = "drop table if exists "+dbContato.Tabela;

    public dbHelper(Context context){
        super(context, dbContato.NomeBase, null, VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropTable);
        onCreate(db);
    }

    public void saveToLocalDataBase(String name, int syncStatus, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbContato.nome, name);
        contentValues.put(dbContato.syncStatus, syncStatus);
        database.insert(dbContato.Tabela, null, contentValues);
    }

    public Cursor readFromLocalDatabase (SQLiteDatabase database){
        String[] projection = {dbContato.nome, dbContato.syncStatus};

        return (database.query(dbContato.Tabela, projection, null, null, null, null, null));
    }

    public void updateLocalDatabase(String name, int syncStatus, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbContato.syncStatus, syncStatus);
        String selection = dbContato.nome+" LIKE ?";
        String[] selection_args = { name };
        database.update(dbContato.Tabela, contentValues, selection, selection_args);
    }
}
