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
import com.example.manejointeligentedepragas.model.PragaModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InfoPraga extends AppCompatActivity {

    int codPraga;

    TextView tvDescricaoBioecologia;
    TextView tvInjurias;
    TextView tvNome;
    TextView tvFamilia;
    TextView tvOrdem;
    TextView tvNomeCientifico;
    TextView tvLocalizacaoAmostra;
    TextView tvAmbientePropicio;
    TextView tvCicloVida;
    TextView tvObservacoes;
    TextView tvHorarioAtuacao;
    TextView tvEstagioDeAtuacao;
    TextView tvControleCultural;
    Button btnInfoPragaMetodo;
    Button btnInfoPragaInimigo;

    String nome;
    String familia;
    String ordem;
    String descricao_bioecologia;
    String nomeCientificoEspecie;
    String localizacao_amostra;
    String ambientePropicio;
    String cicloVida;
    String injurias;
    String observacoes;
    String horarioAtuacao;
    String estagioDeAtuacao;
    String controleCultural;

    ArrayList<String> urlsPragas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_praga);

        tvDescricaoBioecologia = findViewById(R.id.Descricao);
        tvInjurias = findViewById(R.id.Injurias);
        tvNome = findViewById(R.id.InfoPragaNome);
        tvFamilia = findViewById(R.id.InfoPragaFamilia);
        tvOrdem = findViewById(R.id.InfoPragaOrdem);
        tvNomeCientifico = findViewById(R.id.InfoPragaNomeCientifico);
        tvAmbientePropicio = findViewById(R.id.InfoPragaAmbientePropicio);
        tvLocalizacaoAmostra = findViewById(R.id.InfoPragaAmostra);
        tvCicloVida = findViewById(R.id.InfoPragaCicloDeVida);
        tvObservacoes = findViewById(R.id.InfoPragaObservacoes);
        tvHorarioAtuacao = findViewById(R.id.InfoPragaHorarioDeAtuacao);
        tvEstagioDeAtuacao = findViewById(R.id.InfoPragaEstagioDeAtuacao);
        tvControleCultural = findViewById(R.id.InfoPragaControleCultural);
        btnInfoPragaMetodo = findViewById(R.id.btnInfoPragaMetodo);
        btnInfoPragaInimigo = findViewById(R.id.btnInfoPragaInimigo);
        ViewPager viewPager = findViewById(R.id.ViewPagerPraga);


        codPraga = getIntent().getIntExtra("Cod_Praga",0);

        ResgatarUrlPragas(viewPager, codPraga);
        ResgataPragas(codPraga);

        btnInfoPragaMetodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(InfoPraga.this,VisualizaMetodos.class);
                i.putExtra("Cod_Praga",codPraga);
                startActivity(i);
            }
        });

        btnInfoPragaInimigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(InfoPraga.this,VisualizaInimigos.class);
                i.putExtra("Cod_Praga",codPraga);
                startActivity(i);
            }
        });


    }

    public void ResgataPragas(int codP){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet
            String url = "http://mip2.000webhostapp.com/infoPraga.php?Cod_Praga="+codP;

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
                            familia = obj.getString("Familia");
                            ordem = obj.getString("Ordem");
                            nomeCientificoEspecie = obj.getString("NomeCientifico");
                            localizacao_amostra = obj.getString("Localizacao");
                            ambientePropicio = obj.getString("AmbientePropicio");
                            cicloVida = obj.getString("CicloVida");
                            observacoes = obj.getString("Observacoes");
                            horarioAtuacao = obj.getString("HorarioDeAtuacao");
                            estagioDeAtuacao = obj.getString("EstagioDeAtuacao");
                            injurias = obj.getString("Injurias");
                            descricao_bioecologia = obj.getString("Descricao");
                            controleCultural = obj.getString("ControleCultural");
                        }

                        tvDescricaoBioecologia.setText("Descrição: "+descricao_bioecologia);
                        tvInjurias.setText("Injúrias: "+injurias);
                         tvNome.setText("Nome: "+nome);
                         tvFamilia.setText("Família: "+familia);
                         tvOrdem.setText("Ordem: "+ordem);
                         tvNomeCientifico.setText("Nome científico: "+nomeCientificoEspecie);
                         tvLocalizacaoAmostra.setText("Amostra: "+localizacao_amostra);
                         tvAmbientePropicio.setText("Ambiente propício: "+ambientePropicio);
                         tvCicloVida.setText("Ciclo de vida: "+cicloVida);
                         tvObservacoes.setText("Observações: "+observacoes);
                         tvHorarioAtuacao.setText("Horário de Atuação: "+horarioAtuacao);
                         tvEstagioDeAtuacao.setText("Estágio de atuação: "+estagioDeAtuacao);
                         tvControleCultural.setText("Controles culturais: "+controleCultural);

                        setTitle("MIP² | "+nome);



                    } catch (JSONException e) {
                        Toast.makeText(InfoPraga.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(InfoPraga.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }
    }

    public void ResgatarUrlPragas(final ViewPager viewPager, int codP){
        Utils u = new Utils();
        if (!u.isConected(getBaseContext())) {
            Toast.makeText(this, "Habilite a conexão com a internet", Toast.LENGTH_LONG).show();
        } else { // se tem acesso à internet

            String url = "http://mip2.000webhostapp.com/resgatarFotoPragas.php?Cod_Praga="+codP;

            RequestQueue queue = Volley.newRequestQueue(InfoPraga.this);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Parsing json
                    //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            urlsPragas.add("http://mip2.000webhostapp.com/imagens/pragas/"+obj.getString("FotoPraga"));
                        }
                        ViewPagerAdapter adapterPragas = new ViewPagerAdapter(InfoPraga.this,urlsPragas);
                        viewPager.setAdapter(adapterPragas);
                    } catch (JSONException e) {
                        Toast.makeText(InfoPraga.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(InfoPraga.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }

    }



}