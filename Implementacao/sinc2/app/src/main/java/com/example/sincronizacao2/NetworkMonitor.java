package com.example.sincronizacao2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;

public class NetworkMonitor extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        if(checkNetworkConnection(context)) {
            final DbHelper dbHelper = new DbHelper(context);
            final SQLiteDatabase database = dbHelper.getWritableDatabase();

            Cursor cursor = dbHelper.readFromLocalDatabase(database);

            while(cursor.moveToNext()){

                int sync_Status = cursor.getInt(cursor.getColumnIndex(DbContract.syncStatus));
                if(sync_Status == DbContract.SYNC_STATUS_FAILED){
                    //precisa sincronizar com o server remoto
                    final String Name = cursor.getString(cursor.getColumnIndex(DbContract.Nome));


                    String url = "http://mip2.000webhostapp.com/sync.php?Nome=" + Name;
                    RequestQueue queue = Volley.newRequestQueue(context);
                    queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                  @Override
                                  public void onResponse(String response) {
                                      //Parsing json
                                      //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                                      try {
                                          JSONObject obj = new JSONObject(response);
                                          String Response = obj.getString("response");
                                          if(Response.equals("OK")){
                                              dbHelper.updateLocalDatabase(Name,DbContract.SYNC_STATUS_OK,database);
                                              context.sendBroadcast(new Intent(DbContract.UI_UPDATE_BROADCAST));
                                          }
                                      } catch (JSONException e) {
                                          e.printStackTrace();
                                      }
                                  }
                              }, new Response.ErrorListener() {
                                  @Override
                                  public void onErrorResponse(VolleyError error) {
                                      error.printStackTrace();
                                  }
                              })
                              {
                                  @Override
                                  protected Map<String, String> getParams() throws AuthFailureError
                                  {
                                      Map<String, String> params = new HashMap<>();
                                      params.put("Nome",Name);
                                      return params;
                                  }
                              }
                    );
                }
            }
            dbHelper.close();
        }
    }

    public boolean checkNetworkConnection(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!= null && networkInfo.isConnected());
    }

}
