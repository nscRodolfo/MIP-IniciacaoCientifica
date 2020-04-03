package com.example.manejointeligentedepragas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.manejointeligentedepragas.Auxiliar.Utils;
import com.example.manejointeligentedepragas.RecyclerViewAdapter.CulturaCardAdapter;
import com.example.manejointeligentedepragas.RecyclerViewAdapter.PlanosRealizadosAdapter;
import com.example.manejointeligentedepragas.model.CulturaModel;
import com.example.manejointeligentedepragas.model.PlanoAmostragemModel;
import com.jjoe64.graphview.GraphView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;


public class RelatorioPlanosRealizados extends AppCompatActivity {

    int Cod_Propriedade;
    int codCultura;
    int codPraga;
    String nome;
    boolean aplicado;
    String nomePropriedade;
    String nomePraga;

    private ArrayList<PlanoAmostragemModel> planos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_planos_realizados);

        Cod_Propriedade = getIntent().getIntExtra("Cod_Propriedade", 0);
        codCultura = getIntent().getIntExtra("Cod_Cultura", 0);
        nome = getIntent().getStringExtra("NomeCultura");
        aplicado = getIntent().getBooleanExtra("Aplicado", false);
        codPraga = getIntent().getIntExtra("Cod_Praga", 0);
        nomePropriedade = getIntent().getStringExtra("nomePropriedade");
        nomePraga = getIntent().getStringExtra("nomePraga");

        resgataDados(codCultura, 1);
    }

    public void resgataDados(final int codCultura, final int codPraga){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet
            String url = "http://mip2.000webhostapp.com/resgataDadosGraphPlantasPlanos.php?Cod_Cultura="+codCultura+"&&Cod_Praga="+codPraga;
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
                            PlanoAmostragemModel pa = new PlanoAmostragemModel();
                            pa.setDate(obj.getString("Data"));
                            pa.setPlantasAmostradas(obj.getInt("numPlantas"));
                            pa.setPlantasInfestadas(obj.getInt("popPragas"));
                            planos.add(pa);
                        }
                           iniciarRecyclerView();


                    } catch (JSONException e) {
                        Toast.makeText(RelatorioPlanosRealizados.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RelatorioPlanosRealizados.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }
    }

    private void iniciarRecyclerView(){
        RecyclerView rv = findViewById(R.id.rvPlanosRealizados);
        PlanosRealizadosAdapter adapter = new PlanosRealizadosAdapter(this, planos, 1,codCultura);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }
}
