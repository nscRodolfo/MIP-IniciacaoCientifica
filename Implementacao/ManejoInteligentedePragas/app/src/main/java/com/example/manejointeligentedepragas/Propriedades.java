package com.example.manejointeligentedepragas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.widget.Button;
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
    public String nomePropriedade;
    public String tipoUsu;


    private Dialog mDialog;



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

            case R.id.sobre_o_mip:
                Intent p = new Intent(this, SobreMIP.class);
                startActivity(p);
                return  true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propriedades);

        openDialog();

        Controller_Usuario cu = new Controller_Usuario(getBaseContext());

        tipoUsu = cu.getUser().getTipo();

        fab = findViewById(R.id.fabAddProp);
        textView = findViewById(R.id.tvAddPropriedade);

        if(tipoUsu.equals("Funcionario")){
            setTitle("MIP² | Propriedades vinculadas");
            fab.hide();
            textView.setVisibility(View.GONE);
            resgatarDadosFunc(cu.getUser().getCod_Usuario());
        }else if(tipoUsu.equals("Produtor")){
            setTitle("MIP² | Propriedades");
            resgatarDados(cu.getUser().getCod_Usuario());
        }else if(tipoUsu.equals("Adm")){

        }

        //setTitle("MIP² | Propriedades");
        //resgatarDados();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils u = new Utils();
                if(!u.isConected(getBaseContext()))
                {
                    Toast.makeText(Propriedades.this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
                    mDialog.dismiss();
                }else {
                    Intent i = new Intent(Propriedades.this, AdicionarPropriedade.class);
                    startActivity(i);
                }
            }
        });


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils u = new Utils();
                if(!u.isConected(getBaseContext()))
                {
                    Toast.makeText(Propriedades.this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
                    mDialog.dismiss();
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


    private void resgatarDados(int codUsuario){

        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet", Toast.LENGTH_LONG).show();
            mDialog.dismiss();
        }else { // se tem acesso à internet

            String url = "http://mip2.000webhostapp.com/resgatarPropriedades.php?Cod_Usuario=" + codUsuario;

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
                                cards.add(u);
                            }
                            iniciarRecyclerView();
                            mDialog.dismiss();
                        } catch (JSONException e) {
                            Toast.makeText(Propriedades.this, e.toString(), Toast.LENGTH_LONG).show();
                            mDialog.dismiss();
                        }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Propriedades.this,error.toString(), Toast.LENGTH_LONG).show();
                    mDialog.dismiss();
                }
            }));

        }
        /*
        PropriedadeModel p1 = new PropriedadeModel(0,"Espera Feliz","Rio Pomba","MG",0);
        */
    }

    private void iniciarRecyclerView(){
        RecyclerView rv = findViewById(R.id.RVPropriedade);
        PropriedadeCardAdapter adapter = new PropriedadeCardAdapter(this, cards);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

    }


    private void resgatarDadosFunc(int codUsuario){

        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet", Toast.LENGTH_LONG).show();
            mDialog.dismiss();
        }else { // se tem acesso à internet
            String url = "http://mip2.000webhostapp.com/resgatarPropriedadesFunc.php?Cod_Usuario=" + codUsuario;
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
                            cards.add(u);
                        }
                        if(cards.isEmpty()){
                            AlertDialog.Builder dlgBox = new AlertDialog.Builder(Propriedades.this);
                            dlgBox.setTitle("Aviso!");
                            dlgBox.setMessage("Você não está vinculado à nenhuma propriedade.");
                            dlgBox.setPositiveButton("Entendi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // não faz nada
                                }
                            });
                            dlgBox.show();
                        }else{
                            iniciarRecyclerView();
                        }
                        mDialog.dismiss();
                    } catch (JSONException e) {
                        Toast.makeText(Propriedades.this, e.toString(), Toast.LENGTH_LONG).show();
                        mDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Propriedades.this,error.toString(), Toast.LENGTH_LONG).show();
                    mDialog.dismiss();
                }
            }));

        }
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
