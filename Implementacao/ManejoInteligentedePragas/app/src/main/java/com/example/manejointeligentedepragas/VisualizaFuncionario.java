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

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VisualizaFuncionario extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public FloatingActionButton fabAddFunc;
    public TextView tvAddFunc;
    private ArrayList<UsuarioModel> cards = new ArrayList<>();
    Integer Cod_Propriedade;
    ArrayList<String> emailsFuncionarios = new ArrayList<String>();
    String nomePropriedade;
    private Dialog mDialog;

    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiza_funcionario);

        openDialog();

        Cod_Propriedade = getIntent().getIntExtra("Cod_Propriedade", 0);
        nomePropriedade = getIntent().getStringExtra("nomePropriedade");


        //menu novo
        Toolbar toolbar = findViewById(R.id.toolbar_funcionario);
        setSupportActionBar(toolbar);
        drawerLayout= findViewById(R.id.drawer_layout_funcionario);
        NavigationView navigationView = findViewById(R.id.nav_view_funcionario);
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

        setTitle("MIP² | Funcionários");


        resgatarDados();

        fabAddFunc = findViewById(R.id.fabAddFunc);
        fabAddFunc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VisualizaFuncionario.this, AdicionarFunc.class);
                i.putExtra("Cod_Propriedade", Cod_Propriedade);
                i.putExtra("emailsFuncionarios", emailsFuncionarios);
                i.putExtra("nomePropriedade", nomePropriedade);
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
                i.putExtra("nomePropriedade", nomePropriedade);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            Intent i = new Intent(VisualizaFuncionario.this,Cultura.class);
            i.putExtra("Cod_Propriedade",Cod_Propriedade);
            i.putExtra("nomePropriedade", nomePropriedade);
            startActivity(i);
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
                Intent prop= new Intent(this, Propriedades.class);
                startActivity(prop);
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
                SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("isIntroOpened",false);
                editor.commit();

                Intent intro = new Intent(this, IntroActivity.class);
                startActivity(intro);
                break;

            case R.id.drawerSobre:
                Intent pp = new Intent(this, SobreMIP.class);
                startActivity(pp);
                break;

            case R.id.drawerReferencias:
                Intent pi = new Intent(this, Referencias.class);
                startActivity(pi);
                break;

            case R.id.drawerRecomendações:
                Intent pa = new Intent(this, RecomendacoesMAPA.class);
                startActivity(pa);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    private void resgatarDados(){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet", Toast.LENGTH_LONG).show();
            mDialog.dismiss();
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
                        mDialog.dismiss();
                    } catch (JSONException e) {
                        Toast.makeText(VisualizaFuncionario.this, e.toString(), Toast.LENGTH_LONG).show();
                        mDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(VisualizaFuncionario.this,error.toString(), Toast.LENGTH_LONG).show();
                    mDialog.dismiss();
                }
            }));

        }
    }

    private void iniciarRecyclerView(){
        RecyclerView rv = findViewById(R.id.RVFuncionario);
        FuncionarioCardAdapter adapter = new FuncionarioCardAdapter( this, cards, Cod_Propriedade, nomePropriedade);

        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

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
