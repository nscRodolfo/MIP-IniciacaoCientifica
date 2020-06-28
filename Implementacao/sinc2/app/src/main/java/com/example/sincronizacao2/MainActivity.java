package com.example.sincronizacao2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    EditText et;
    RecyclerView.LayoutManager lm;
    RecyclerAdapter adapter;
    ArrayList<Contato> contatos = new ArrayList<>();
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv);
        et = findViewById(R.id.name);
        lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);

        adapter = new RecyclerAdapter(contatos);
        rv.setAdapter(adapter);

        readFromLocalStorage();

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                readFromLocalStorage();
            }
        };

    }

    public void submitName(View view) {
        String nome = et.getText().toString();
        saveToAppServer(nome);
        et.setText("");
    }


    public void readFromLocalStorage(){

        contatos.clear();

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = dbHelper.readFromLocalDatabase(database);

        while (cursor.moveToNext()){
            String nome = cursor.getString(cursor.getColumnIndex(DbContract.Nome));
            int syncStatus = cursor.getInt(cursor.getColumnIndex(DbContract.syncStatus));
            contatos.add(new Contato(nome, syncStatus));
        }

        adapter.notifyDataSetChanged();
        cursor.close();
        dbHelper.close();

    }

    private void saveToAppServer(final String nome) {
        if(checkNetworkConnection()){
            String url = "http://mip2.000webhostapp.com/sync.php?Nome=" + nome;
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Parsing json
                    //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                    try {
                        JSONObject obj = new JSONObject(response);
                        String Response = obj.getString("response");
                        if(Response.equals("OK")){
                            saveToLocalStorage(nome,DbContract.SYNC_STATUS_OK);
                        }else{
                            saveToLocalStorage(nome,DbContract.SYNC_STATUS_FAILED);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    saveToLocalStorage(nome,DbContract.SYNC_STATUS_FAILED);
                }
            })
                  {
                      @Override
                      protected Map<String, String> getParams() throws AuthFailureError
                      {
                          Map<String, String> params = new HashMap<>();
                          params.put("Nome",nome);
                          return params;
                      }
                  }
            );

        }else{
            saveToLocalStorage(nome, DbContract.SYNC_STATUS_FAILED);
        }
    }



    public boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!= null && networkInfo.isConnected());
    }

    private void saveToLocalStorage(String nome, int sync_Status){

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.saveToLocalDataBase(nome, sync_Status, database);
        readFromLocalStorage();
        dbHelper.close();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(broadcastReceiver, new IntentFilter(DbContract.UI_UPDATE_BROADCAST));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
}
