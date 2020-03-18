package com.example.manejointeligentedepragas;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.example.manejointeligentedepragas.Crontroller.Controller_Usuario;
import com.example.manejointeligentedepragas.RecyclerViewAdapter.PropriedadeCardAdapter;
import com.example.manejointeligentedepragas.model.PropriedadeModel;
import com.example.manejointeligentedepragas.model.UsuarioModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Propriedades extends AppCompatActivity {

    public FloatingActionButton fab;
    public TextView textView;

    private static final String TAG = "Propriedades";

    //vars relative layout
    private ArrayList<PropriedadeModel> cards = new ArrayList<>();

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
        Log.d(TAG, "onCreate:  started");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propriedades);

        resgatarDados();

        fab = findViewById(R.id.fabAddProp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils u = new Utils();
                if(!u.isConected(getBaseContext()))
                {
                    Toast.makeText(Propriedades.this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
                }else {
                    Intent i = new Intent(Propriedades.this, AdicionarPropriedade.class);
                    startActivity(i);
                }
            }
        });

        textView = findViewById(R.id.tvAddPropriedade);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils u = new Utils();
                if(!u.isConected(getBaseContext()))
                {
                    Toast.makeText(Propriedades.this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
                }else {
                    Intent i = new Intent(Propriedades.this, AdicionarPropriedade.class);
                    startActivity(i);
                }
            }
        });
    }


    // mensagem para sair do aplicativo, não volta mais no login
    private boolean backPressedOnce = false;
    private Handler backPressedHandler = new Handler();

    private static final int BACK_PRESSED_DELAY = 2000;

    private final Runnable backPressedTimeoutAction = new Runnable() {
        @Override
        public void run() {
            backPressedOnce = false;
        }
    };

    @Override
    public void onBackPressed() {
            if (this.backPressedOnce) {
                // Finaliza a aplicacao
                finishAffinity();
                //System.exit(0);
                return;
            }
            this.backPressedOnce = true;
            Toast.makeText(this, "Pressione novamente para fechar",Toast.LENGTH_SHORT).show();
            backPressedHandler.postDelayed(backPressedTimeoutAction, BACK_PRESSED_DELAY);
    }


    private void resgatarDados(){
        Log.d(TAG, "resgatarDados: resgatou");

        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet

            Controller_Usuario cu = new Controller_Usuario(getBaseContext());
            String url = "http://mip2.000webhostapp.com/resgatarPropriedades.php?Cod_Usuario=" + cu.getUser().getCod_Usuario();


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
                                PropriedadeModel u = new PropriedadeModel();
                                u.setCod_Propriedade(obj.getInt("Cod_Propriedade"));
                                u.setNome(obj.getString("Nome"));
                                u.setCidade(obj.getString("Cidade"));
                                u.setEstado(obj.getString("Estado"));
                                u.setFk_Cod_Produtor(obj.getInt("fk_Produtor_Cod_Produtor"));
                                cards.add(u);
                            }
                            iniciarRecyclerView();
                        } catch (JSONException e) {
                            Toast.makeText(Propriedades.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Propriedades.this,error.toString(), Toast.LENGTH_LONG).show();
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
        Log.d(TAG, "iniciarRecyclerView:  init iniciar");
        RecyclerView rv = findViewById(R.id.RVPropriedade);
        PropriedadeCardAdapter adapter = new PropriedadeCardAdapter(this, cards);

        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

    }
}
