package com.example.manejointeligentedepragas;

import android.app.Activity;
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
import com.example.manejointeligentedepragas.VisualizaAdapter.VisuPragasAdapter;
import com.example.manejointeligentedepragas.model.PragaModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VisualizaPragas extends AppCompatActivity {

    ArrayList<String> nomePragas = new ArrayList<>();
    ArrayList<Integer> codPragas = new ArrayList<>();
    private ArrayList<PragaModel> cards = new ArrayList<>();

    EditText edtPesquisaPragas;
    private ArrayList<String> pesquisa = new ArrayList<String>();
    private ArrayList<Integer> codPragapesquisa = new ArrayList<Integer>();
    Boolean pesquisado = false;

    Integer cod_Planta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiza_pragas);

        cod_Planta = getIntent().getIntExtra("Cod_Planta", 0);

        final ListView listView = findViewById(R.id.ListViewPragas);
        edtPesquisaPragas = findViewById(R.id.PesquisaPragas);

        setTitle("MIP² | Pragas");


        //esconder teclado
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if(cod_Planta != 0){
            ResgataPragasEspecificas(listView,cod_Planta);
        }else{
            ResgataPragas(listView);
        }


        //pesquisa
        edtPesquisaPragas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Pesquisar();
                listView.setAdapter(new ArrayAdapter<String>(VisualizaPragas.this, android.R.layout.simple_list_item_1, pesquisa));
                if(s==null){
                    pesquisado = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Pesquisar();
                listView.setAdapter(new ArrayAdapter<String>(VisualizaPragas.this, android.R.layout.simple_list_item_1, pesquisa));
                if(s==null){
                    pesquisado = false;
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(pesquisado ==true) {
                    Intent i = new Intent(VisualizaPragas.this, InfoPraga.class);
                    i.putExtra("Cod_Praga", codPragapesquisa.get(position));
                    startActivity(i);
                }else{
                    Intent i = new Intent(VisualizaPragas.this, InfoPraga.class);
                    i.putExtra("Cod_Praga", codPragas.get(position));
                    startActivity(i);
                }
            }
        });

    }

    public void ResgataPragas(final ListView listView){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet
            String url = "http://mip2.000webhostapp.com/visualizaPragas.php";

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
                            nomePragas.add(obj.getString("NomePraga"));
                            codPragas.add(obj.getInt("Cod_Praga"));
                        }


                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<String>(VisualizaPragas.this, android.R.layout.simple_list_item_1, nomePragas);
                        listView.setAdapter(adapter);

                    } catch (JSONException e) {
                        Toast.makeText(VisualizaPragas.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(VisualizaPragas.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }
    }

    public void ResgataPragasEspecificas(final ListView listView, int codPlanta){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet
            String url = "http://mip2.000webhostapp.com/selecionarPragasEspecificas.php?Cod_Planta=" + codPlanta;
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i< array.length(); i++){
                            JSONObject obj = array.getJSONObject(i);
                            nomePragas.add(obj.getString("Nome"));
                            codPragas.add(obj.getInt("Cod_Praga"));
                        }

                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<String>(VisualizaPragas.this, android.R.layout.simple_list_item_1, nomePragas);
                        listView.setAdapter(adapter);


                    } catch (JSONException e) {
                        Toast.makeText(VisualizaPragas.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(VisualizaPragas.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }
    }

    public void Pesquisar(){
        int textlength = edtPesquisaPragas.getText().length();
        pesquisa.clear();
        codPragapesquisa.clear();
        pesquisado = true;

        for (int i = 0; i < nomePragas.size(); i++ ) {
            if (textlength <= nomePragas.get(i).length()) {
                //if (edtPesquisaPragas.getText().toString().equalsIgnoreCase((String)nomePragas.get(i).subSequence(0, textlength))) {
                if(nomePragas.get(i).contains(edtPesquisaPragas.getText().toString())){
                    pesquisa.add(nomePragas.get(i));
                    codPragapesquisa.add(codPragas.get(i));
                }else if(edtPesquisaPragas.getText().toString().equalsIgnoreCase((String)nomePragas.get(i).subSequence(0, textlength))){
                    pesquisa.add(nomePragas.get(i));
                    codPragapesquisa.add(codPragas.get(i));
                }
            }
        }
    }

    private void iniciarListView() {
        ListView lv = findViewById(R.id.ListViewPragas);
        ArrayAdapter adapter1 = new VisuPragasAdapter(this, cards);
        lv.setAdapter(adapter1);

    }
}
