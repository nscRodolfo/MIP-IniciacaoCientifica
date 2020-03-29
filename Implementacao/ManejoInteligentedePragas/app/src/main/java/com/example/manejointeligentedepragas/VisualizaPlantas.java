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

public class VisualizaPlantas extends AppCompatActivity {

    ArrayList<String> nomePlantas = new ArrayList<>();
    ArrayList<Integer> codPlantas = new ArrayList<>();

    //pesquisa
    EditText edtPesquisaPlantas;
    Boolean pesquisado = false;
    private ArrayList<String> pesquisa = new ArrayList<String>();
    private ArrayList<Integer> codPlantaPesquisa = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiza_plantas);

        edtPesquisaPlantas = findViewById(R.id.PesquisaPlantas);
        final ListView listView = findViewById(R.id.ListViewPlantas);

        setTitle("MIP² | Plantas");

        //esconder teclado
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ResgataPlantas(listView);

        //pesquisa
        edtPesquisaPlantas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Pesquisar();
                listView.setAdapter(new ArrayAdapter<String>(VisualizaPlantas.this, android.R.layout.simple_list_item_1, pesquisa));
                if(s==null){
                    pesquisado = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Pesquisar();
                listView.setAdapter(new ArrayAdapter<String>(VisualizaPlantas.this, android.R.layout.simple_list_item_1, pesquisa));
                if(s==null){
                    pesquisado = false;
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(pesquisado ==true) {
                    Intent i= new Intent(VisualizaPlantas.this,infoPlanta.class);
                    i.putExtra("Cod_Planta",codPlantaPesquisa.get(position));
                    startActivity(i);
                }else{
                    Intent i= new Intent(VisualizaPlantas.this,infoPlanta.class);
                    i.putExtra("Cod_Planta",codPlantas.get(position));
                    startActivity(i);
                }

            }
        });
    }


    public void ResgataPlantas(final ListView listView){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet
            String url = "http://mip2.000webhostapp.com/visualizaPlantas.php";

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
                            nomePlantas.add(obj.getString("NomePlanta"));
                            codPlantas.add(obj.getInt("Cod_Planta"));
                        }
                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<String>(VisualizaPlantas.this, android.R.layout.simple_list_item_1, nomePlantas);
                        listView.setAdapter(adapter);

                    } catch (JSONException e) {
                        Toast.makeText(VisualizaPlantas.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(VisualizaPlantas.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }
    }

    public void Pesquisar(){
        int textlength = edtPesquisaPlantas.getText().length();
        pesquisa.clear();
        codPlantaPesquisa.clear();
        pesquisado = true;

        for (int i = 0; i < nomePlantas.size(); i++ ) {
            if (textlength <= nomePlantas.get(i).length()) {
                //if (edtPesquisaPragas.getText().toString().equalsIgnoreCase((String)nomePragas.get(i).subSequence(0, textlength))) {
                if(nomePlantas.get(i).contains(edtPesquisaPlantas.getText().toString())){
                    pesquisa.add(nomePlantas.get(i));
                    codPlantaPesquisa.add(codPlantas.get(i));
                }else if(edtPesquisaPlantas.getText().toString().equalsIgnoreCase((String)nomePlantas.get(i).subSequence(0, textlength))){
                    pesquisa.add(nomePlantas.get(i));
                    codPlantaPesquisa.add(codPlantas.get(i));
                }
            }
        }
    }
}
