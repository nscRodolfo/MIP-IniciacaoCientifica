package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
    adapter adapter;
    ArrayList<contato> arrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.rv);
        et = findViewById(R.id.name);
        lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        adapter = new adapter(arrayList);
        rv.setAdapter(adapter);

        readFromLocalStorage();
    }

    public void submitName(View view){
        String nome = et.getText().toString();
        saveToAppServer(nome);
        et.setText("");
    }

    public void readFromLocalStorage(){

        arrayList.clear();

        dbHelper dbHelper = new dbHelper(MainActivity.this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = dbHelper.readFromLocalDatabase(database);

        while (cursor.moveToNext()){
            String nome = cursor.getString(cursor.getColumnIndex(dbContato.nome));
            int syncStatus = cursor.getInt(cursor.getColumnIndex(dbContato.syncStatus));
            arrayList.add(new contato(nome, syncStatus));
        }

        adapter.notifyDataSetChanged();
        cursor.close();
        dbHelper.close();

    }

    private void saveToAppServer(final String nomee){
        if(checkNetworkConnection()){
            StringRequest  stringRequest = new StringRequest(Request.Method.POST, dbContato.SERVER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                String Response = jsonObject.getString("response");
                                if(Response.equals("OK")){
                                    saveToLocalStorage(nomee,dbContato.SYNC_STATUS_OK);
                                }
                                else
                                {
                                    saveToLocalStorage(nomee,dbContato.SYNC_STATUS_FAILED);
                                }

                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    saveToLocalStorage(nomee, dbContato.SYNC_STATUS_FAILED);
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("nome",nomee);
                    return params;
                }
            };
            MySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);
        }else{
            saveToLocalStorage(nomee, dbContato.SYNC_STATUS_FAILED);
        }

  /*
        if(checkNetworkConnection()){
            String url = "http://127.0.0.1/syncdemo/sync.php?name="+nome;
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Parsing json
                    //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        String Response = jsonObject.getString("confirmacao");
                        if(Response.equals("OK")){
                            saveToLocalStorage(nome,dbContato.SYNC_STATUS_OK);
                        }
                        else
                        {
                            saveToLocalStorage(nome,dbContato.SYNC_STATUS_FAILED);
                        }

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    saveToLocalStorage(nome, dbContato.SYNC_STATUS_FAILED);
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nome",nome);
                return params;
            }
            });

        }else{
            saveToLocalStorage(nome, dbContato.SYNC_STATUS_FAILED);
        }*/
    }

    private void saveToLocalStorage(String nome, int syncStatus) {

        dbHelper dbHelper = new dbHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.saveToLocalDataBase(nome, syncStatus, database);
        readFromLocalStorage();
        dbHelper.close();

    }



    public boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!= null && networkInfo.isConnected());
    }




}
