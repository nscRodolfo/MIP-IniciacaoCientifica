package com.example.manejointeligentedepragas.Crontroller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.manejointeligentedepragas.Banco.Banco;
import com.example.manejointeligentedepragas.model.PresencaPragaModel;
import com.example.manejointeligentedepragas.model.TalhaoModel;

import java.util.ArrayList;

public class Controller_PresencaPraga {

    private SQLiteDatabase db;
    private Banco bancoLocal;

    public Controller_PresencaPraga(Context context){
        bancoLocal = new Banco(context);
    }

    public boolean addPresenca(int Status, int Cod_Talhao, int Cod_Praga, int Sync_Status, int Cod_Presenca){
        db = bancoLocal.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(bancoLocal.fk_Praga_Cod_Praga, Cod_Praga);
        cv.put(bancoLocal.fk_Talhao_Cod_Talhao, Cod_Talhao);
        cv.put(bancoLocal.Cod_PresencaPraga, Cod_Presenca);
        cv.put(bancoLocal.Status, Status);
        cv.put(bancoLocal.Sync_Status,Sync_Status);
        return db.insert(bancoLocal.PresencaPraga, null, cv) != -1;
    }

    public boolean addPresencaSemCod(int Status, int Cod_Talhao, int Cod_Praga, int Sync_Status){
        db = bancoLocal.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(bancoLocal.fk_Praga_Cod_Praga, Cod_Praga);
        cv.put(bancoLocal.fk_Talhao_Cod_Talhao, Cod_Talhao);
        cv.put(bancoLocal.Status, Status);
        cv.put(bancoLocal.Sync_Status,Sync_Status);
        return db.insert(bancoLocal.PresencaPraga, null, cv) != -1;
    }

    public boolean updatePresenca(PresencaPragaModel presencaPragaModel){
        db = bancoLocal.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(bancoLocal.fk_Praga_Cod_Praga, presencaPragaModel.getFk_Cod_Praga());
        cv.put(bancoLocal.fk_Talhao_Cod_Talhao, presencaPragaModel.getFk_Cod_Talhao());
        cv.put(bancoLocal.Cod_PresencaPraga, presencaPragaModel.getCod_PresencaPraga());
        cv.put(bancoLocal.Status, presencaPragaModel.getStatus());
        cv.put(bancoLocal.Sync_Status, presencaPragaModel.getStatus());
        String where = " Cod_PresencaPraga = '"+ presencaPragaModel.getCod_PresencaPraga()+"'";
        return db.update(bancoLocal.PresencaPraga, cv, where, null) > 0;
    }

    public ArrayList<PresencaPragaModel> getPresencaPraga(int codTalhao) {
        db = bancoLocal.getWritableDatabase();
        String query = "SELECT * FROM " + bancoLocal.PresencaPraga+" WHERE PresencaPraga.fk_Talhao_Cod_Talhao=" +codTalhao+";";
        ArrayList<PresencaPragaModel> presencas = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                PresencaPragaModel presencaPragaModel = new PresencaPragaModel();
                presencaPragaModel.setCod_PresencaPraga(c.getInt(0));
                presencaPragaModel.setStatus(c.getInt(1));
                presencaPragaModel.setFk_Cod_Praga((c.getInt(2)));
                presencaPragaModel.setFk_Cod_Talhao(c.getInt(3));
                presencaPragaModel.setSync_Status(c.getInt(4));
                presencas.add(presencaPragaModel);
            } while (c.moveToNext());
        }
        c.close();
        return presencas;
    }

    public void removerPresencaPraga(){
        db = bancoLocal.getWritableDatabase();
        String query = "";
        db.delete(bancoLocal.PresencaPraga, query, null);
    }

    public void removerPresencaPragaEspecifica(PresencaPragaModel presencaPragaModel){
        db = bancoLocal.getWritableDatabase();
        String where = " Cod_PresencaPraga = '"+ presencaPragaModel.getCod_PresencaPraga()+"'";
        db.delete(bancoLocal.PresencaPraga, where, null);
    }
}
