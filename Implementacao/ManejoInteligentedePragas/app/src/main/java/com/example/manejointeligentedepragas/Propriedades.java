package com.example.manejointeligentedepragas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
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

public class Propriedades extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public FloatingActionButton fab;
    public TextView textView;
    public String nomePropriedade;
    public String tipoUsu;
    private Dialog mDialog;

    private DrawerLayout drawerLayout;

    //vars relative layout
    private ArrayList<PropriedadeModel> cards = new ArrayList<>();

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

        //menu novo
        Toolbar toolbar = findViewById(R.id.toolbar_propriedades);
        setSupportActionBar(toolbar);
        drawerLayout= findViewById(R.id.drawer_layout_propriedades);
        NavigationView navigationView = findViewById(R.id.nav_view_propriedades);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        View headerView = navigationView.getHeaderView(0);

        Controller_Usuario controller_usuario = new Controller_Usuario(getBaseContext());
        String nomeUsu = controller_usuario.getUser().getNome();
        String emailUsu = controller_usuario.getUser().getEmail();

        TextView nomeMenu = headerView.findViewById(R.id.nomeMenu);
        nomeMenu.setText(nomeUsu);

        TextView emailMenu = headerView.findViewById(R.id.emailMenu);
        emailMenu.setText(emailUsu);




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
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
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
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.drawerPerfil:
                Intent i= new Intent(this, Perfil.class);
                startActivity(i);
                break;
            case R.id.drawerProp:
                Toast.makeText(Propriedades.this,"Você já está na tela de propriedades.", Toast.LENGTH_LONG).show();
                break;

            case R.id.drawerPlantas:
                Intent j = new Intent(this, VisualizaPlantas.class);
                startActivity(j);
                break;

            case R.id.drawerPrag:
                Intent k = new Intent(this, VisualizaPragas.class);
                startActivity(k);
                break;

            case R.id.drawerMet:
                Intent l = new Intent(this, VisualizaMetodos.class);
                startActivity(l);
                break;

            case R.id.drawerSobreMip:
                Intent p = new Intent(this, SobreMIP.class);
                startActivity(p);
                break;

            case R.id.drawerTutorial:

                break;

            case R.id.drawerSobre:
                Intent pp = new Intent(this, SobreMIP.class);
                startActivity(pp);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
