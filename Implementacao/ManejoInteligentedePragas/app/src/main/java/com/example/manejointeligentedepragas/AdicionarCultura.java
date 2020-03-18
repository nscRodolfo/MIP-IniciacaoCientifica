package com.example.manejointeligentedepragas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.manejointeligentedepragas.Auxiliar.Utils;
import com.example.manejointeligentedepragas.Crontroller.Controller_Usuario;
import com.example.manejointeligentedepragas.model.PropriedadeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdicionarCultura extends AppCompatActivity {


    //String nomePlantas[];
    //String tamanhoTalhao[];

    ArrayList<String> nomePlantas = new ArrayList<String>();
    ArrayList<String> tamanhoTalhao = new ArrayList<String>();
    ArrayList<Integer> Cod_Planta = new ArrayList<Integer>();
    Float tamanhoSelecionado;
    Integer codigoSelecionado;

    Integer numeroDeTalhoes;
    
    EditText ETtamanhoCulturaUsuario;

    TextView TVnumeroTalhoesSugerido;

    Integer Cod_Propriedade;

    Button salvarCultura;

    Boolean click = true; // verificação de click (nao adicionar dados duplos)

    ArrayList<String> plantasadd = new ArrayList<String>(); // para remover cultura ja adicionada

    String nomeSelecionado;

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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_cultura);




        Cod_Propriedade = getIntent().getIntExtra("Cod_Propriedade", 0);
        plantasadd = getIntent().getStringArrayListExtra("plantasadd");

        ETtamanhoCulturaUsuario = findViewById(R.id.etPpT);
        TVnumeroTalhoesSugerido = findViewById(R.id.tvNumeroTalhoes);

        salvarCultura = findViewById(R.id.btnSalvrCultura);

        Spinner dropdown = findViewById(R.id.dropdownCultura);
        ResgatarPlantas(dropdown);

        salvarCultura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click) {
                    SalvarCultura();
                }else{
                    Toast.makeText(AdicionarCultura.this, "Aguarde um momento..." ,Toast.LENGTH_LONG).show();
                }
            }
        });

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tamanhoSelecionado = Float.parseFloat(tamanhoTalhao.get(position));
                codigoSelecionado = Cod_Planta.get(position);
                nomeSelecionado = nomePlantas.get(position);
                //teste Toast.makeText(parent.getContext(), "Selected: " + tamanhoSelecionado + " Código Planta:" + codigoSelecionado,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ETtamanhoCulturaUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()!=0){
                    Float tamanhoDigitado = Float.parseFloat(s.toString());
                    if(tamanhoDigitado <= tamanhoSelecionado) {
                        TVnumeroTalhoesSugerido.setText("1");
                        numeroDeTalhoes = 1;
                    }else{
                        Float numeroSugerido = tamanhoDigitado / tamanhoSelecionado;
                        Double a = Math.ceil(numeroSugerido) ;
                        TVnumeroTalhoesSugerido.setText("" + a.intValue());

                        numeroDeTalhoes = a.intValue();
                    }
                }else{
                    TVnumeroTalhoesSugerido.setText("0");
                    numeroDeTalhoes = 0;
                }


            }
        });


        /*
        Spinner dropdown = findViewById(R.id.dropdownCultura);
        String[] items = new String[nomePlantas.size()];
        items = nomePlantas.toArray(items);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nomePlantas);
        dropdown.setAdapter(adapter);
        */
    }

    public void ResgatarPlantas(final Spinner dropdown){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet
            String url = "http://mip2.000webhostapp.com/selecionarCulturaTamanho.php";


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

                            tamanhoTalhao.add(obj.getString("TamanhoTalhao"));
                            nomePlantas.add(obj.getString("Nome"));
                            Cod_Planta.add(obj.getInt("Cod_Planta"));


                        }
                       // nomePlantas.removeAll(plantasadd);

                        //String[] items = new String[nomePlantas.size()];
                        //items = nomePlantas.toArray(items);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, nomePlantas);
                        dropdown.setAdapter(adapter);


                    } catch (JSONException e) {
                        Toast.makeText(AdicionarCultura.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AdicionarCultura.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }
    }

    public void SalvarCultura(){
        final String tamanhoCultura = ETtamanhoCulturaUsuario.getText().toString();


        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();

        }else if(tamanhoCultura.isEmpty()) {
            Toast.makeText(this,"Informe o tamanho de sua cultura (em hectare).", Toast.LENGTH_LONG).show();

        }else if(plantasadd.contains(nomeSelecionado)){
            Toast.makeText(this,"Esta cultura já existe em sua propriedade.", Toast.LENGTH_LONG).show();

        }else{ // se tem acesso à internet
            click = false;
            String url = "http://mip2.000webhostapp.com/adicionarCultura.php?TamanhoDaCultura="+tamanhoCultura+
                    "&&fk_Propriedade_Cod_Propriedade="+Cod_Propriedade+
                    "&&fk_Planta_Cod_Planta="+codigoSelecionado+
                    "&&qtdTalhao="+numeroDeTalhoes;
            //Toast.makeText(this,url, Toast.LENGTH_LONG).show();

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
                            Intent k = new Intent(AdicionarCultura.this, Cultura.class);
                            k.putExtra("Cod_Propriedade", Cod_Propriedade);
                            startActivity(k);
                        }else{
                            Toast.makeText(AdicionarCultura.this, "Cultura não cadastrada! Tente novamente",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(AdicionarCultura.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AdicionarCultura.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }
    }

}
