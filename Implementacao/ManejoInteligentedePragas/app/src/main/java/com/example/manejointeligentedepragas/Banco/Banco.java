package com.example.manejointeligentedepragas.Banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Banco extends SQLiteOpenHelper{

        private static final String NOME_BANCO = "bancoLocal.db";
        private static final int VERSAO = 1;

        public static final String Usuario = "Usuario";
        public static final String Cod_Usuario = "Cod_Usuario";
        public static final String Email = "Email";
        public static final String Senha = "Senha";
        public static final String Nome = "Nome";
        public static final String Telefone = "Telefone";


        public Banco(Context context) {
            super(context, NOME_BANCO,null,VERSAO);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = "CREATE TABLE "+Usuario+"("
                    + Cod_Usuario + " integer primary key,"
                    + Email+ " text,"
                    + Senha+ " text,"
                    + Nome+ " text,"
                    + Telefone+ " text"
                    + ")";

            db.execSQL(sql);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            onCreate(db);
        }
    }