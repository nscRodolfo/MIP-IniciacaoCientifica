package com.example.manejointeligentedepragas;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.manejointeligentedepragas.Auxiliar.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdicionarPraga extends AppCompatActivity {

    ArrayList<String> nomePraga = new ArrayList<String>();
    ArrayList<Integer> codPraga = new ArrayList<Integer>();

    ArrayList<String> pragasAdd = new ArrayList<String>();

    Button salvarPraga;

    Integer codigoSelecionado;

    String nomeSelecionado;

    boolean aplicado;

    Boolean click = true; // verificação de click (nao adicionar dados duplos)

    int codCultura;

    int Cod_Propriedade;

    String nome;

    Button InfoPraga;

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
                Intent i= new Intent(this, Perfil.class);
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
        setContentView(R.layout.activity_adicionar_praga);

        aplicado = getIntent().getBooleanExtra("Aplicado", false);
        codCultura = getIntent().getIntExtra("Cod_Cultura", 0);
        nome = getIntent().getStringExtra("NomeCultura");
        Cod_Propriedade = getIntent().getIntExtra("Cod_Propriedade", 0);

        salvarPraga = findViewById(R.id.btnSalvrCultura);
        InfoPraga = findViewById(R.id.btnInfoPraga);

        pragasAdd = getIntent().getStringArrayListExtra("pragasAdd");


        Spinner dropdown = findViewById(R.id.dropdownPraga);

        ResgatarPragas(dropdown, codCultura);

        salvarPraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click) {
                    SalvarPraga();
                }else{
                    Toast.makeText(AdicionarPraga.this, "Aguarde um momento..." ,Toast.LENGTH_LONG).show();
                }
            }
        });

        InfoPraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(AdicionarPraga.this, InfoPraga.class);
                k.putExtra("Cod_Praga", codigoSelecionado);
                startActivity(k);
            }
        });

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                codigoSelecionado = codPraga.get(position);
                nomeSelecionado = nomePraga.get(position);
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(AdicionarPraga.this,Pragas.class);
        i.putExtra("Cod_Cultura", codCultura);
        i.putExtra("NomeCultura", nome);
        i.putExtra("Cod_Propriedade", Cod_Propriedade);
        i.putExtra("Aplicado", aplicado);
        startActivity(i);
    }

    public void ResgatarPragas(final Spinner dropdown, int codCultura){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet
            String url = "http://mip2.000webhostapp.com/selecionarPragasAtinge.php?cod_Cultura=" + codCultura;


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
                            nomePraga.add(obj.getString("Nome"));
                            codPraga.add(obj.getInt("Cod_Praga"));
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, nomePraga);
                        dropdown.setAdapter(adapter);


                    } catch (JSONException e) {
                        Toast.makeText(AdicionarPraga.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AdicionarPraga.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }
    }


    public void SalvarPraga(){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
        }else if(pragasAdd.contains(nomeSelecionado)){
            Toast.makeText(this,"Esta praga já existe em sua cultura.", Toast.LENGTH_LONG).show();
        }else{ // se tem acesso à internet
            click = false;
            String url = "http://mip2.000webhostapp.com/adicionarPraga.php?Cod_Praga="+codigoSelecionado+
                    "&&Cod_Cultura="+codCultura;
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Parsing json
                    //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                    try {
                        //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();

                        JSONObject obj1 = new JSONObject(response);
                        boolean confirmacao = obj1.getBoolean("confirmacao");

                        if(confirmacao){
                            Intent k = new Intent(AdicionarPraga.this, Pragas.class);
                            k.putExtra("Cod_Cultura", codCultura);
                            k.putExtra("NomeCultura", nome);
                            k.putExtra("Cod_Propriedade", Cod_Propriedade);
                            k.putExtra("Aplicado", aplicado);
                            startActivity(k);
                        }else{
                            Toast.makeText(AdicionarPraga.this, "Cultura não cadastrada! Tente novamente",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(AdicionarPraga.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AdicionarPraga.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }
    }
}
