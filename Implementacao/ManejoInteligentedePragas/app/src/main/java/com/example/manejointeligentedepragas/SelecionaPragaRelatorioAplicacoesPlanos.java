package com.example.manejointeligentedepragas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class SelecionaPragaRelatorioAplicacoesPlanos extends AppCompatActivity {

    ArrayList<String> nomePraga = new ArrayList<String>();
    ArrayList<Integer> codPraga = new ArrayList<Integer>();

    int Cod_Propriedade;
    int codCultura;
    String nome;
    boolean aplicado;
    String nomePropriedade;

    Integer codigoSelecionado;
    String nomeSelecionado;

    Button selecionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleciona_praga_relatorio_aplicacoes_planos);

        Spinner dropdown = findViewById(R.id.dropdownSelecionaSelecionaPragaAplicacao);
        Cod_Propriedade = getIntent().getIntExtra("Cod_Propriedade", 0);
        codCultura = getIntent().getIntExtra("Cod_Cultura", 0);
        nome = getIntent().getStringExtra("NomeCultura");
        aplicado = getIntent().getBooleanExtra("Aplicado", false);
        nomePropriedade = getIntent().getStringExtra("nomePropriedade");

        setTitle("MIP² | "+nome);

        ResgatarPragas(dropdown, codCultura);

        selecionar = findViewById(R.id.btnSelecionaSelecionarPragaAP);

        selecionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SelecionaPragaRelatorioAplicacoesPlanos.this, RelatorioAplicacoesPlanos.class);
                i.putExtra("Cod_Praga", codigoSelecionado);
                i.putExtra("nomePraga", nomeSelecionado);
                i.putExtra("Cod_Cultura", codCultura);
                i.putExtra("NomeCultura", nome);
                i.putExtra("Cod_Propriedade", Cod_Propriedade);
                i.putExtra("Aplicado", aplicado);
                i.putExtra("nomePropriedade", nomePropriedade);
                startActivity(i);
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

    public void ResgatarPragas(final Spinner dropdown, final int codCultura){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet
            String url = "http://mip2.000webhostapp.com/resgatarPragas.php?Cod_Cultura=" + codCultura;


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
                        if(nomePraga.size()  == 0 && codPraga.size() == 0){
                            selecionar.setVisibility(View.INVISIBLE);
                            AlertDialog.Builder dlgBox = new AlertDialog.Builder(SelecionaPragaRelatorioAplicacoesPlanos.this);
                            dlgBox.setTitle("Aviso!");
                            dlgBox.setMessage("Você não possui nenhuma praga cadastrada nessa cultura, deseja adicionar agora?");
                            dlgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ArrayList<String> pragasAdd = new ArrayList<String>();
                                    Intent i = new Intent(SelecionaPragaRelatorioAplicacoesPlanos.this, AdicionarPraga.class);
                                    i.putExtra("Cod_Cultura", codCultura);
                                    i.putExtra("NomeCultura", nome);
                                    i.putExtra("Cod_Propriedade", Cod_Propriedade);
                                    i.putExtra("pragasAdd", pragasAdd);
                                    i.putExtra("Aplicado", aplicado);
                                    i.putExtra("nomePropriedade", nomePropriedade);
                                    startActivity(i);
                                }
                            });

                            dlgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(SelecionaPragaRelatorioAplicacoesPlanos.this, AcoesCultura.class);
                                    i.putExtra("Cod_Cultura", codCultura);
                                    i.putExtra("NomeCultura", nome);
                                    i.putExtra("Cod_Propriedade", Cod_Propriedade);
                                    i.putExtra("Aplicado", aplicado);
                                    i.putExtra("nomePropriedade", nomePropriedade);
                                    startActivity(i);
                                }
                            });

                            dlgBox.show();
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, nomePraga);
                        dropdown.setAdapter(adapter);


                    } catch (JSONException e) {
                        Toast.makeText(SelecionaPragaRelatorioAplicacoesPlanos.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SelecionaPragaRelatorioAplicacoesPlanos.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }
    }
}
