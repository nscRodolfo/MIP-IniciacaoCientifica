package com.example.manejointeligentedepragas;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.manejointeligentedepragas.Auxiliar.Utils;
import com.example.manejointeligentedepragas.Crontroller.Controller_Usuario;
import com.example.manejointeligentedepragas.RecyclerViewAdapter.FuncionarioCardAdapter;
import com.example.manejointeligentedepragas.RecyclerViewAdapter.PropriedadeCardAdapter;
import com.example.manejointeligentedepragas.model.PropriedadeModel;
import com.example.manejointeligentedepragas.model.UsuarioModel;
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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VisualizaFuncionario extends AppCompatActivity {

    public FloatingActionButton fabAddFunc;
    public TextView tvAddFunc;
    private ArrayList<UsuarioModel> cards = new ArrayList<>();
    Integer Cod_Propriedade;
    ArrayList<String> emailsFuncionarios = new ArrayList<String>();

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
        setContentView(R.layout.activity_visualiza_funcionario);

        Cod_Propriedade = getIntent().getIntExtra("Cod_Propriedade", 0);

        resgatarDados();

        fabAddFunc = findViewById(R.id.fabAddFunc);
        fabAddFunc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VisualizaFuncionario.this, AdicionarFunc.class);
                i.putExtra("Cod_Propriedade", Cod_Propriedade);
                i.putExtra("emailsFuncionarios", emailsFuncionarios);
                startActivity(i);
            }
        });

        tvAddFunc = findViewById(R.id.tvAddFuncionario);
        tvAddFunc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VisualizaFuncionario.this, AdicionarFunc.class);
                i.putExtra("Cod_Propriedade", Cod_Propriedade);
                i.putExtra("emailsFuncionarios", emailsFuncionarios);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(VisualizaFuncionario.this,Cultura.class);
        i.putExtra("Cod_Propriedade",Cod_Propriedade);
        startActivity(i);
    }



    private void resgatarDados(){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet

            Controller_Usuario cu = new Controller_Usuario(getBaseContext());
            String url = "http://mip2.000webhostapp.com/resgatarFuncionarios.php?Cod_Propriedade="+Cod_Propriedade;


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
                            UsuarioModel u = new UsuarioModel();
                            u.setCod_Usuario(obj.getInt("Cod_Usuario"));
                            u.setNome(obj.getString("Nome"));
                            u.setTelefone(obj.getString("Telefone"));
                            u.setEmail(obj.getString("Email"));
                            emailsFuncionarios.add(obj.getString("Email"));
                            cards.add(u);
                        }
                        iniciarRecyclerView();
                    } catch (JSONException e) {
                        Toast.makeText(VisualizaFuncionario.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(VisualizaFuncionario.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }
        /*
        PropriedadeModel p1 = new PropriedadeModel(0,"Espera Feliz","Rio Pomba","MG",0);
        PropriedadeModel p2 = new PropriedadeModel(0,"Espera Triste","Juiz de Fora","MG",0);
        PropriedadeModel p3 = new PropriedadeModel(0,"Espera Alegre","Ubá","MG",0);
        PropriedadeModel p4 = new PropriedadeModel(0,"Espera Dark","Mercês","MG",0);
        cards.add(p1);
        cards.add(p2);
        cards.add(p3);
        cards.add(p4);
        */
    }

    private void iniciarRecyclerView(){
        RecyclerView rv = findViewById(R.id.RVFuncionario);
        FuncionarioCardAdapter adapter = new FuncionarioCardAdapter( this, cards, Cod_Propriedade);

        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

    }



}
