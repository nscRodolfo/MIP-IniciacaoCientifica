package com.example.manejointeligentedepragas;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.manejointeligentedepragas.Auxiliar.Utils;
import com.example.manejointeligentedepragas.RecyclerViewAdapter.CulturaCardAdapter;
import com.example.manejointeligentedepragas.RecyclerViewAdapter.PragaCardAdapter;
import com.example.manejointeligentedepragas.model.CulturaModel;
import com.example.manejointeligentedepragas.model.PragaModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Pragas extends AppCompatActivity {

    public FloatingActionButton fabAddPraga;
    public TextView tvAddPraga;
    private ArrayList<PragaModel> cards = new ArrayList<>();
    int codCultura;
    int Cod_Propriedade;
    boolean aplicado;
    String nome;
    ArrayList<String> pragasAdd = new ArrayList<String>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lateral, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.perfil:
                Intent i = new Intent(this, Perfil.class);
                startActivity(i);
                return true;
            case R.id.pragas:
                Intent k = new Intent(this, VisualizaPragas.class);
                startActivity(k);
                return true;
            case R.id.plantas:
                Intent j = new Intent(this, VisualizaPlantas.class);
                startActivity(j);
                return true;

            case R.id.metodo_de_controle:
                Intent l = new Intent(this, VisualizaMetodos.class);
                startActivity(l);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pragas);

        aplicado = getIntent().getBooleanExtra("Aplicado", false);
        codCultura = getIntent().getIntExtra("Cod_Cultura", 0);
        nome = getIntent().getStringExtra("NomeCultura");
        Cod_Propriedade = getIntent().getIntExtra("Cod_Propriedade", 0);

        resgatarDados();

        fabAddPraga = findViewById(R.id.fabAddPraga);
        fabAddPraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Pragas.this, AdicionarPraga.class);
                i.putExtra("Cod_Cultura", codCultura);
                i.putExtra("NomeCultura", nome);
                i.putExtra("Cod_Propriedade", Cod_Propriedade);
                i.putExtra("pragasAdd", pragasAdd);
                i.putExtra("Aplicado", aplicado);
                startActivity(i);
            }
        });

        tvAddPraga = findViewById(R.id.tvAddPraga);
        tvAddPraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Pragas.this, AdicionarPraga.class);
                i.putExtra("Cod_Cultura", codCultura);
                i.putExtra("NomeCultura", nome);
                i.putExtra("Cod_Propriedade", Cod_Propriedade);
                i.putExtra("pragasAdd", pragasAdd);
                i.putExtra("Aplicado", aplicado);
                startActivity(i);
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Pragas.this, AcoesCultura.class);
        i.putExtra("Cod_Cultura", codCultura);
        i.putExtra("NomeCultura", nome);
        i.putExtra("Cod_Propriedade", Cod_Propriedade);
        i.putExtra("Aplicado", aplicado);
        startActivity(i);
    }


    private void iniciarRecyclerView() {
        RecyclerView rv = findViewById(R.id.RVPraga);
        PragaCardAdapter adapter = new PragaCardAdapter(this, cards, codCultura, nome, Cod_Propriedade, aplicado);

        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

    }

    private void resgatarDados() {
        //Log.d(TAG, "resgatarDados: resgatou");

        Utils u = new Utils();
        if (!u.isConected(getBaseContext())) {
            Toast.makeText(this, "Habilite a conexão com a internet", Toast.LENGTH_LONG).show();
        } else { // se tem acesso à internet

            String url = "http://mip2.000webhostapp.com/resgatarPragas.php?Cod_Cultura=" + codCultura;

            RequestQueue queue = Volley.newRequestQueue(Pragas.this);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Parsing json
                    //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            PragaModel u = new PragaModel();
                            u.setCod_Praga(obj.getInt("Cod_Praga"));
                            u.setNome(obj.getString("Nome"));
                            u.setStatus(obj.getInt("Status"));
                            cards.add(u);
                            pragasAdd.add(obj.getString("Nome"));
                        }
                        iniciarRecyclerView();
                    } catch (JSONException e) {
                        Toast.makeText(Pragas.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Pragas.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }

    }
}
