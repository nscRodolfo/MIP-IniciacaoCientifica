package com.example.manejointeligentedepragas;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class infoPlanta extends AppCompatActivity {

    int codPlanta;

    TextView tvNome;
    TextView tvNomeCientifico;
    TextView tvFamilia;
    TextView tvBotanica;
    TextView tvAmbientePropicio;
    TextView tvCultivo;
    TextView tvTratosCulturais;
    TextView tvCiclo;
    TextView tvTamanhoTalhao;
    Button btnInfoPlantaPragas;

    String nome;
    String nomeCientifico;
    String familia;
    String botanica;
    String ambientePropicio;
    String cultivo;
    String tratosCulturais;
    String ciclo;
    String tamanhoTalhao;

    ArrayList<String> urlsPlantas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_planta);

        tvNome = findViewById(R.id.InfoPlantaNome);
        tvNomeCientifico = findViewById(R.id.InfoPlantaNomeCientifico);
        tvFamilia = findViewById(R.id.InfoPlantaFamilia);
        tvBotanica = findViewById(R.id.InfoPlantaBotanica);
        tvAmbientePropicio = findViewById(R.id.InfoPlantaAmbientePropicio);
        tvCultivo = findViewById(R.id.InfoPlantaCultivo);
        tvTratosCulturais = findViewById(R.id.InfoPlantaTratosCulturais);
        tvCiclo = findViewById(R.id.InfoPlantaCiclo);
        tvTamanhoTalhao = findViewById(R.id.InfoPlantaTamanhoTalhao);
        btnInfoPlantaPragas = findViewById(R.id.btnInfoPlantaPragas);
        ViewPager viewPager = findViewById(R.id.ViewPagerPlanta);

        codPlanta = getIntent().getIntExtra("Cod_Planta",0);

        ResgatarUrlPlantas(viewPager, codPlanta);
        ResgataPlantas(codPlanta);

        btnInfoPlantaPragas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(infoPlanta.this, VisualizaPragas.class);
                k.putExtra("Cod_Planta",codPlanta);
                startActivity(k);
            }
        });
    }


    public void ResgataPlantas(int codP){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet
            String url = "http://mip2.000webhostapp.com/infoPlanta.php?Cod_Planta="+codP;

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

                            nome = obj.getString("Nome");
                            nomeCientifico = obj.getString("NomeCientifico");
                            familia = obj.getString("Familia");
                            botanica = obj.getString("Botanica");
                            ambientePropicio = obj.getString("AmbientePropicio");
                            cultivo = obj.getString("Cultivo");
                            tratosCulturais = obj.getString("TratosCulturais");
                            ciclo = obj.getString("Ciclo");
                            tamanhoTalhao = obj.getString("TamanhoTalhao");
                        }

                        tvNome.setText("Nome: "+nome);
                        tvNomeCientifico.setText("Nome científico: "+nomeCientifico);
                        tvFamilia.setText("Família: "+familia);
                        tvBotanica.setText("Botanica: "+botanica);
                        tvAmbientePropicio.setText("Ambiente Propício: "+ambientePropicio);
                        tvCultivo.setText("Cultivo: "+cultivo);
                        tvTratosCulturais.setText("Tratos Culturais: "+tratosCulturais);
                        tvCiclo.setText("Ciclo: "+ciclo);
                        tvTamanhoTalhao.setText("Tamanho de talhão necessário: "+tamanhoTalhao+" hectares");


                        setTitle("MIP² | "+nome);

                    } catch (JSONException e) {
                        Toast.makeText(infoPlanta.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(infoPlanta.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }
    }

    public void ResgatarUrlPlantas(final ViewPager viewPager, int codP){
        Utils u = new Utils();
        if (!u.isConected(getBaseContext())) {
            Toast.makeText(this, "Habilite a conexão com a internet", Toast.LENGTH_LONG).show();
        } else { // se tem acesso à internet

            String url = "http://mip2.000webhostapp.com/resgatarFotoPlantas.php?Cod_Planta="+codP;

            RequestQueue queue = Volley.newRequestQueue(infoPlanta.this);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Parsing json
                    //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            urlsPlantas.add("http://mip2.000webhostapp.com/imagens/plantas/"+obj.getString("FotoPlanta"));
                        }
                        ViewPagerAdapter adapterPlantas = new ViewPagerAdapter(infoPlanta.this,urlsPlantas);
                        viewPager.setAdapter(adapterPlantas);
                    } catch (JSONException e) {
                        Toast.makeText(infoPlanta.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(infoPlanta.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }

    }

}
