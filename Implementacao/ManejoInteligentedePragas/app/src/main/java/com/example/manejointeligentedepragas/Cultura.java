package com.example.manejointeligentedepragas;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.widget.ProgressBar;
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
import com.example.manejointeligentedepragas.RecyclerViewAdapter.CulturaCardAdapter;
import com.example.manejointeligentedepragas.RecyclerViewAdapter.PropriedadeCardAdapter;
import com.example.manejointeligentedepragas.model.CulturaModel;
import com.example.manejointeligentedepragas.model.PropriedadeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Cultura extends AppCompatActivity {

    public FloatingActionButton fabAddCultura;
    public TextView tvAddCultura;
    public TextView tvNumFunc;
    public CardView cardCultura;
    public CardView cardFuncionario;
    public String tipoUsu;

    private static final String TAG = "Cultura";
    private ArrayList<CulturaModel> cards = new ArrayList<>();
    Integer Cod_Propriedade;
    ArrayList<String> plantasadd = new ArrayList<String>();
    Integer numFunc = 0;
    String nomePropriedade;


    private Dialog mDialog;

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

            case R.id.sobre_o_mip:
                Intent p = new Intent(this, SobreMIP.class);
                startActivity(p);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultura);

        openDialog();

        Cod_Propriedade = getIntent().getIntExtra("Cod_Propriedade", 0);
        nomePropriedade = getIntent().getStringExtra("nomePropriedade");


        cardFuncionario = findViewById(R.id.CardFuncionario);
        tvAddCultura = findViewById(R.id.AddCultura);
        fabAddCultura = findViewById(R.id.fabAddCultura);
        tvNumFunc = findViewById(R.id.tvNumFunc);

        Controller_Usuario cu = new Controller_Usuario(getBaseContext());
        tipoUsu = cu.getUser().getTipo();

        if(tipoUsu.equals("Funcionario")){
            tvAddCultura.setVisibility(View.GONE);
            fabAddCultura.hide();
            cardFuncionario.setVisibility(View.GONE);
            resgatarDados();
        }else if(tipoUsu.equals("Produtor")){
            resgatarFunc();
            resgatarDados();
        }

        setTitle("MIP² | "+nomePropriedade);

        fabAddCultura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils u = new Utils();
                if(!u.isConected(getBaseContext()))
                {
                    Toast.makeText(Cultura.this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
                    mDialog.dismiss();
                }else {
                    Intent i = new Intent(Cultura.this, AdicionarCultura.class);
                    i.putExtra("Cod_Propriedade", Cod_Propriedade);
                    i.putExtra("plantasadd", plantasadd);
                    i.putExtra("nomePropriedade", nomePropriedade);
                    startActivity(i);
                }
            }
        });

        tvAddCultura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils u = new Utils();
                if(!u.isConected(getBaseContext()))
                {
                    Toast.makeText(Cultura.this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
                    mDialog.dismiss();
                }else {
                    Intent i = new Intent(Cultura.this, AdicionarCultura.class);
                    i.putExtra("Cod_Propriedade", Cod_Propriedade);
                    i.putExtra("plantasadd", plantasadd);
                    i.putExtra("nomePropriedade", nomePropriedade);
                    startActivity(i);
                }
            }
        });


        cardFuncionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Cultura.this, VisualizaFuncionario.class);
                i.putExtra("Cod_Propriedade", Cod_Propriedade);
                i.putExtra("nomePropriedade", nomePropriedade);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Cultura.this,Propriedades.class);
        startActivity(i);
    }





    private void resgatarDados(){
        //Log.d(TAG, "resgatarDados: resgatou");

        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet", Toast.LENGTH_LONG).show();
            mDialog.dismiss();
        }else { // se tem acesso à internet

            String url = "http://mip2.000webhostapp.com/resgatarCulturas.php?Cod_Propriedade=" + Cod_Propriedade;

            RequestQueue queue = Volley.newRequestQueue(Cultura.this);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Parsing json
                    //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i< array.length(); i++){
                            JSONObject obj = array.getJSONObject(i);
                            CulturaModel u = new CulturaModel();
                            u.setCod_Cultura(obj.getInt("Cod_Cultura"));
                            u.setFk_Cod_Propriedade(obj.getInt("fk_Propriedade_Cod_Propriedade"));
                            u.setnomePlanta(obj.getString("NomePlanta"));
                            u.setNumeroTalhoes(obj.getInt("count_talhao"));
                            boolean auxAplicado;
                            if(obj.getInt("Aplicado") != 0){
                                auxAplicado = true;
                            }else{
                                auxAplicado = false;
                            }
                            u.setAplicado(auxAplicado);
                            cards.add(u);
                            plantasadd.add(obj.getString("NomePlanta"));
                        }
                        iniciarRecyclerView();
                        mDialog.dismiss();
                    } catch (JSONException e) {
                        Toast.makeText(Cultura.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   Toast.makeText(Cultura.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }

/*
        CulturaModel p1 = new CulturaModel(0,0,"Rio Pomba",0);
        CulturaModel p2 = new CulturaModel(0,0,"Juiz de Fora",0);
        CulturaModel p3 = new CulturaModel(0,0,"Ubá",0);
        CulturaModel p4 = new CulturaModel(0,0,"Mercês",0);
        cards.add(p1);
        cards.add(p2);
        cards.add(p3);
        cards.add(p4);
        iniciarRecyclerView();
*/
    }

    private void iniciarRecyclerView(){

        Log.d(TAG, "iniciarRecyclerView:  init iniciar");
        RecyclerView rv = findViewById(R.id.RVCultura);
        CulturaCardAdapter adapter = new CulturaCardAdapter(this, cards, Cod_Propriedade,nomePropriedade);

        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

    }

    public void resgatarFunc(){
            String url = "http://mip2.000webhostapp.com/resgatarNumFuncionarios.php?Cod_Propriedade=" + Cod_Propriedade;

            RequestQueue queue = Volley.newRequestQueue(Cultura.this);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Parsing json
                    //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                    try {
                        JSONObject obj = new JSONObject(response);
                        numFunc = obj.getInt("count_func");
                        if(numFunc == 0){
                            tvNumFunc.setText("Nenhum funcionário cadastrado.");
                        }else {
                            tvNumFunc.setText(numFunc + " funcionários cadastrados.");
                        }
                    } catch (JSONException e) {
                        Toast.makeText(Cultura.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Cultura.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

    }

    public void openDialog(){
        mDialog = new Dialog(this);
        //vamos remover o titulo da Dialog
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //vamos carregar o xml personalizado
        mDialog.setContentView(R.layout.dialog);
        //DEixamos transparente
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        // não permitimos fechar esta dialog
        mDialog.setCancelable(false);
        //temos a instancia do ProgressBar!
        final ProgressBar progressBar = ProgressBar.class.cast(mDialog.findViewById(R.id.progressBar));

        mDialog.show();

        // mDialog.dismiss(); -> para fechar a dialog

    }

}
