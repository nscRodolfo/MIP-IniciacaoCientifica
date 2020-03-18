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

public class infoMetodo extends AppCompatActivity {

    int codMetodo;

    TextView tvNome;
    TextView tvMateriaisNecessarios;
    TextView tvModoDePreparo;
    TextView tvIntervaloAplicacao;
    TextView tvEfeitoColateral;
    TextView tvObservacoes;
    TextView tvAtuacao;

    String Nome;
    String MateriaisNecessarios;
    String ModoDePreparo;
    int IntervaloAplicacao ;
    String EfeitoColateral;
    String Observacoes;
    String Atuacao;

    ArrayList<String> urlsMetodos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_metodo);

        tvNome = findViewById(R.id.InfoMetodoNome);
        tvMateriaisNecessarios = findViewById(R.id.InfoMetodoMateriais);
        tvModoDePreparo = findViewById(R.id.InfoMetodoPreparo);
        tvIntervaloAplicacao = findViewById(R.id.InfoMetodoIntervalo);
        tvEfeitoColateral = findViewById(R.id.IInfoMetodoEfeito);
        tvObservacoes = findViewById(R.id.InfoMetodoObs);
        tvAtuacao = findViewById(R.id.InfoMetodoAtuacao);

        codMetodo = getIntent().getIntExtra("Cod_Metodo",0);

        ViewPager viewPager = findViewById(R.id.ViewPagerMetodo);
        ResgatarUrlMetodos(viewPager, codMetodo);
        ResgataMetodos(codMetodo);
    }

    public void ResgataMetodos(int codM){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet
            String url = "http://mip2.000webhostapp.com/infoMetodo.php?Cod_Metodo="+codM;

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

                            Nome = obj.getString("Nome");
                            MateriaisNecessarios = obj.getString("MateriaisNecessarios");
                            ModoDePreparo = obj.getString("ModoDePreparo");
                            IntervaloAplicacao  = obj.getInt("IntervaloAplicacao");
                            EfeitoColateral = obj.getString("EfeitoColateral");
                            Observacoes = obj.getString("Observacoes");
                            Atuacao = obj.getString("Atuacao");
                        }

                         tvNome.setText("Nome: "+Nome);
                         tvMateriaisNecessarios.setText("Materiais necesários: "+MateriaisNecessarios);
                         tvModoDePreparo.setText("Modo de preparo: "+ModoDePreparo);
                         if (IntervaloAplicacao == 0){
                             tvIntervaloAplicacao.setText("Intervalo de aplicação(em dias): ver recomendação do distribuidor.");
                         }else{
                             tvIntervaloAplicacao.setText("Intervalo de aplicação(em dias): "+IntervaloAplicacao);
                         }
                         tvEfeitoColateral.setText("Efeitos colaterais: "+EfeitoColateral);
                         tvObservacoes.setText("Observações: "+Observacoes);
                         tvAtuacao.setText("Atuação: "+Atuacao);
                    } catch (JSONException e) {
                        Toast.makeText(infoMetodo.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(infoMetodo.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }
    }

    public void ResgatarUrlMetodos(final ViewPager viewPager, int codM){
        Utils u = new Utils();
        if (!u.isConected(getBaseContext())) {
            Toast.makeText(this, "Habilite a conexão com a internet", Toast.LENGTH_LONG).show();
        } else { // se tem acesso à internet

            String url = "http://mip2.000webhostapp.com/resgatarFotoMetodos.php?Cod_Metodo="+codM;

            RequestQueue queue = Volley.newRequestQueue(infoMetodo.this);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Parsing json
                    //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            urlsMetodos.add("http://mip2.000webhostapp.com/imagens/metodos/"+obj.getString("FotoMetodo"));
                        }
                        ViewPagerAdapter adapterMetodos = new ViewPagerAdapter(infoMetodo.this,urlsMetodos);
                        viewPager.setAdapter(adapterMetodos);
                    } catch (JSONException e) {
                        Toast.makeText(infoMetodo.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(infoMetodo.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }

    }

}
