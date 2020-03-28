package com.example.manejointeligentedepragas;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.manejointeligentedepragas.Auxiliar.Utils;
import com.example.manejointeligentedepragas.ImageAdapter.ViewPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class infoInimigoNatural extends AppCompatActivity {

    int codInimigo;

    TextView tvNomeInimigo;

    String nomeInimigo;

    ArrayList<String> urlsInimigos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_inimigo_natural);

        ViewPager viewPager = findViewById(R.id.ViewPagerInimigo);
        tvNomeInimigo = findViewById(R.id.InfoInimigoNome);

        codInimigo = getIntent().getIntExtra("Cod_Inimigo",0);
        if(codInimigo == 0){
            tvNomeInimigo.setText("Sem informações");
        }else{
            ResgatarUrlInimigos(viewPager, codInimigo);
            ResgataInimigos(codInimigo);
        }
    }


    public void ResgataInimigos(int codInimigo){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet
            String url = "http://mip2.000webhostapp.com/infoInimigoNatural.php?Cod_Inimigo="+codInimigo;

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Parsing json
                    //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                    try {
                        //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i< array.length(); i++){
                            JSONObject obj = array.getJSONObject(i);
                            nomeInimigo = obj.getString("Nome");
                        }
                        tvNomeInimigo.setText("Nome: "+nomeInimigo);

                        setTitle("MIP² | "+nomeInimigo);
                    } catch (JSONException e) {
                        Toast.makeText(infoInimigoNatural.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(infoInimigoNatural.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }
    }
    public void ResgatarUrlInimigos(final ViewPager viewPager, int codI){
        Utils u = new Utils();
        if (!u.isConected(getBaseContext())) {
            Toast.makeText(this, "Habilite a conexão com a internet", Toast.LENGTH_LONG).show();
        } else { // se tem acesso à internet

            String url = "http://mip2.000webhostapp.com/resgatarFotoInimigos.php?Cod_Inimigo="+codI;

            RequestQueue queue = Volley.newRequestQueue(infoInimigoNatural.this);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Parsing json
                    //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            urlsInimigos.add("http://mip2.000webhostapp.com/imagens/inimigos/"+obj.getString("FotoInimigo"));
                        }
                        ViewPagerAdapter adapterInimigos = new ViewPagerAdapter(infoInimigoNatural.this,urlsInimigos);
                        viewPager.setAdapter(adapterInimigos);
                    } catch (JSONException e) {
                        Toast.makeText(infoInimigoNatural.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(infoInimigoNatural.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }

    }

}
