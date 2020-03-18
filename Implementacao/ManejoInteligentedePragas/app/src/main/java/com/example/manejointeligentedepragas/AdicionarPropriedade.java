package com.example.manejointeligentedepragas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.manejointeligentedepragas.Crontroller.Controller_Usuario;
import com.example.manejointeligentedepragas.model.CulturaModel;
import com.example.manejointeligentedepragas.model.PropriedadeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdicionarPropriedade extends AppCompatActivity {


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


    Button salvarPropriedade;

    EditText nomePropriedade;

    EditText cidadePropriedade;

    Boolean click = true; // verificação de click (nao adicionar dados duplos)

    Boolean editar = false; //

    String NomeP;

    String CidadeP;

    String EstadoP;

    Integer CodP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_propriedade);

        NomeP = getIntent().getStringExtra("NomeP");

        CidadeP = getIntent().getStringExtra("CidadeP");

        EstadoP = getIntent().getStringExtra("EstadoP");

        CodP = getIntent().getIntExtra("CodP", 0);


        final Spinner dropdown = findViewById(R.id.dropdownEstado);
        String[] items = new String[] {"AC","AL","AP","AM","BA","CE","DF","ES","GO","MA","MT","MS","MG","PA","PB","PR","PE","PI","RJ","RN","RS","RO","RR","SC","SP","SE","TO"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);


        salvarPropriedade = findViewById(R.id.btnSalvarPropriedade);
        nomePropriedade = findViewById(R.id.etEscreveNomeProp);
        cidadePropriedade = findViewById(R.id.etEscreveCidadeProp);


        if(CodP != 0){
            editar = true;
            nomePropriedade.setText(NomeP);
            cidadePropriedade.setText(CidadeP);
            dropdown.setSelection(adapter.getPosition(EstadoP)); //Muda para o ID com o nome passado
        }

        salvarPropriedade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click) {
                    if(editar){
                        EditarPropriedade(dropdown);
                    }else{
                        SalvarPropriedade(dropdown);
                    }

                    click = false;
                }else{
                    Toast.makeText(AdicionarPropriedade.this, "Aguarde um momento..." ,Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void SalvarPropriedade(Spinner dropdown){
        final String estado = dropdown.getSelectedItem().toString();
        final String nome = nomePropriedade.getText().toString();
        final String cidade = cidadePropriedade.getText().toString();

        if(nome.isEmpty()){
            Toast.makeText(AdicionarPropriedade.this, "Nome da propriedade é obrigatório!" ,Toast.LENGTH_LONG).show();
        }else if(cidade.isEmpty()){
            Toast.makeText(AdicionarPropriedade.this, "Cidade é obrigatório!" ,Toast.LENGTH_LONG).show();
        }else{
            Controller_Usuario cu = new Controller_Usuario(getBaseContext());
            String url = "http://mip2.000webhostapp.com/resgatarCodigoProdutor.php?Cod_Usuario=" + cu.getUser().getCod_Usuario();


            RequestQueue queue = Volley.newRequestQueue(AdicionarPropriedade.this);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Parsing json
                    //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                    try {
                        //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                        JSONObject obj =  new JSONObject(response);
                        String cod = (String) obj.getString("Cod_Produtor");



                        String url = "http://mip2.000webhostapp.com/adicionarPropriedade.php?Nome="+nome+"&&Cidade="+cidade+
                                "&&Estado="+estado+"&&fk_Produtor_Cod_Produtor="+cod;


                        RequestQueue queue = Volley.newRequestQueue(AdicionarPropriedade.this);
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
                                        Intent k = new Intent(AdicionarPropriedade.this, Propriedades.class);
                                        startActivity(k);
                                    }else{
                                        Toast.makeText(AdicionarPropriedade.this, "Propriedade não cadastrada! Tente novamente",Toast.LENGTH_LONG).show();
                                    }




                                } catch (JSONException e) {
                                    Toast.makeText(AdicionarPropriedade.this, e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(AdicionarPropriedade.this,error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }));



                    } catch (JSONException e) {
                        Toast.makeText(AdicionarPropriedade.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AdicionarPropriedade.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));
        }
    }

    public void EditarPropriedade(Spinner dropdown){
        final String estado = dropdown.getSelectedItem().toString();
        final String nome = nomePropriedade.getText().toString();
        final String cidade = cidadePropriedade.getText().toString();

        if(nome.isEmpty()){
            Toast.makeText(AdicionarPropriedade.this, "Nome da propriedade é obrigatório!" ,Toast.LENGTH_LONG).show();
        }else if(cidade.isEmpty()){
            Toast.makeText(AdicionarPropriedade.this, "Cidade é obrigatório!" ,Toast.LENGTH_LONG).show();
        }else{


            String url = "http://mip2.000webhostapp.com/editarPropriedade.php?Cod_Propriedade=" + CodP +
                            "&&Cidade="+cidade+"&&Estado="+estado+"&&Nome="+nome ;

            RequestQueue queue = Volley.newRequestQueue(AdicionarPropriedade.this);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Parsing json
                    //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                    try {
                        JSONObject obj = new JSONObject(response);
                        boolean confirmacao = obj.getBoolean("confirmacao");
                        if(confirmacao){
                            Intent k = new Intent(AdicionarPropriedade.this, Propriedades.class);
                            Toast.makeText(AdicionarPropriedade.this, "Propriedade alterada com sucesso!",Toast.LENGTH_LONG).show();
                            startActivity(k);
                        }else{
                            Toast.makeText(AdicionarPropriedade.this, "Propriedade não editada! Tente novamente",Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        Toast.makeText(AdicionarPropriedade.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AdicionarPropriedade.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));
        }
    }





}
