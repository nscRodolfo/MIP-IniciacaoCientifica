package com.example.manejointeligentedepragas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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

public class VisualizaInimigos extends AppCompatActivity {

    int codPraga;
    ArrayList<String> nomeInimigos = new ArrayList<>();
    ArrayList<Integer> codInimigos = new ArrayList<>();

    //pesquisa
    EditText edtPesquisaInimigos;
    Boolean pesquisado = false;
    private ArrayList<String> pesquisa = new ArrayList<String>();
    private ArrayList<Integer> codInimigoPesquisa = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiza_inimigos);

        edtPesquisaInimigos = findViewById(R.id.PesquisaInimigos);
        codPraga = getIntent().getIntExtra("Cod_Praga",0);

        final ListView listView = findViewById(R.id.ListViewInimigos);

        setTitle("MIP² | Inimigos Naturais");

        //esconder teclado
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ResgataInimigos(listView,codPraga);

        //pesquisa
        edtPesquisaInimigos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Pesquisar();
                listView.setAdapter(new ArrayAdapter<String>(VisualizaInimigos.this, android.R.layout.simple_list_item_1, pesquisa));
                if(s==null){
                    pesquisado = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Pesquisar();
                listView.setAdapter(new ArrayAdapter<String>(VisualizaInimigos.this, android.R.layout.simple_list_item_1, pesquisa));
                if(s==null){
                    pesquisado = false;
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(pesquisado ==true) {
                    Intent i= new Intent(VisualizaInimigos.this,infoInimigoNatural.class);
                    i.putExtra("Cod_Inimigo",codInimigoPesquisa.get(position));
                    startActivity(i);
                }else{
                    Intent i= new Intent(VisualizaInimigos.this,infoInimigoNatural.class);
                    i.putExtra("Cod_Inimigo",codInimigos.get(position));
                    startActivity(i);
                }

            }
        });
    }

    public void ResgataInimigos(final ListView listView, int codPraga){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet
            String url = "http://mip2.000webhostapp.com/visualizaInimigoNatural.php?Cod_Praga="+codPraga;

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
                            nomeInimigos.add(obj.getString("NomeInimigo"));
                            codInimigos.add(obj.getInt("Cod_Inimigo"));
                        }
                        if(nomeInimigos.isEmpty() && codInimigos.isEmpty()){
                            nomeInimigos.add("Esta praga não possui inimigos naturais cadastrados");
                            codInimigos.add(0);
                        }
                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<String>(VisualizaInimigos.this, android.R.layout.simple_list_item_1, nomeInimigos);
                        listView.setAdapter(adapter);

                    } catch (JSONException e) {
                        Toast.makeText(VisualizaInimigos.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(VisualizaInimigos.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }
    }

    public void Pesquisar(){
        int textlength = edtPesquisaInimigos.getText().length();
        pesquisa.clear();
        codInimigoPesquisa.clear();
        pesquisado = true;

        for (int i = 0; i < nomeInimigos.size(); i++ ) {
            if (textlength <= nomeInimigos.get(i).length()) {
                //if (edtPesquisaPragas.getText().toString().equalsIgnoreCase((String)nomePragas.get(i).subSequence(0, textlength))) {
                if(nomeInimigos.get(i).contains(edtPesquisaInimigos.getText().toString())){
                    pesquisa.add(nomeInimigos.get(i));
                    codInimigoPesquisa.add(codInimigos.get(i));
                }else if(edtPesquisaInimigos.getText().toString().equalsIgnoreCase((String)nomeInimigos.get(i).subSequence(0, textlength))){
                    pesquisa.add(nomeInimigos.get(i));
                    codInimigoPesquisa.add(codInimigos.get(i));
                }
            }
        }
    }
}
