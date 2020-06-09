package com.example.manejointeligentedepragas;

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
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.example.manejointeligentedepragas.RecyclerViewAdapter.TalhaoCardAdapter;
import com.example.manejointeligentedepragas.model.CulturaModel;
import com.example.manejointeligentedepragas.model.TalhaoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Talhoes extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public FloatingActionButton fabAddTalhao;
    public TextView tvAddTalhao;
    public String tipoUsu;

    private static final String TAG = "Talhao";
    private ArrayList<TalhaoModel> cards = new ArrayList<>();
    Integer Cod_Propriedade;
    String nomePropriedade;

    private Dialog mDialog;

    private DrawerLayout drawerLayout;

    boolean aplicado;
    int codCultura;
    String nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talhoes);

        Cod_Propriedade = getIntent().getIntExtra("Cod_Propriedade", 0);
        nomePropriedade = getIntent().getStringExtra("nomePropriedade");
        codCultura = getIntent().getIntExtra("Cod_Cultura", 0);
        nome = getIntent().getStringExtra("NomeCultura");


        openDialog();

        tvAddTalhao = findViewById(R.id.tvAddTalhao);
        fabAddTalhao = findViewById(R.id.fabAddTalhao);

        Controller_Usuario cu = new Controller_Usuario(getBaseContext());
        tipoUsu = cu.getUser().getTipo();

        if(tipoUsu.equals("Funcionario")){
            tvAddTalhao.setVisibility(View.GONE);
            fabAddTalhao.hide();
            resgatarDados();
        }else if(tipoUsu.equals("Produtor")){
            resgatarDados();
        }

        //menu novo
        Toolbar toolbar = findViewById(R.id.toolbar_talhoes);
        setSupportActionBar(toolbar);
        drawerLayout= findViewById(R.id.drawer_layout_talhoes);
        NavigationView navigationView = findViewById(R.id.nav_view_talhoes);
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

        setTitle("MIP² | "+nomePropriedade);

        fabAddTalhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils u = new Utils();
                if(!u.isConected(getBaseContext()))
                {
                    Toast.makeText(Talhoes.this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
                    mDialog.dismiss();
                }else {
                    Intent k = new Intent(Talhoes.this, AdicionarTalhao.class);
                    k.putExtra("Cod_Propriedade", Cod_Propriedade);
                    k.putExtra("nomePropriedade", nomePropriedade);
                    k.putExtra("Cod_Cultura", codCultura);
                    k.putExtra("NomeCultura", nome);
                    k.putExtra("Aplicado", aplicado);
                    startActivity(k);
                }
            }
        });

        tvAddTalhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils u = new Utils();
                if(!u.isConected(getBaseContext()))
                {
                    Toast.makeText(Talhoes.this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
                    mDialog.dismiss();
                }else {
                    Intent k = new Intent(Talhoes.this, AdicionarTalhao.class);
                    k.putExtra("Cod_Propriedade", Cod_Propriedade);
                    k.putExtra("nomePropriedade", nomePropriedade);
                    k.putExtra("Cod_Cultura", codCultura);
                    k.putExtra("NomeCultura", nome);
                    k.putExtra("Aplicado", aplicado);
                    startActivity(k);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            Intent i = new Intent(Talhoes.this,Cultura.class);
            i.putExtra("Cod_Propriedade", Cod_Propriedade);
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
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void resgatarDados(){
        //Log.d(TAG, "resgatarDados: resgatou");

        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet", Toast.LENGTH_LONG).show();
            mDialog.dismiss();
        }else { // se tem acesso à internet

            String url = "http://mip2.000webhostapp.com/resgatarTalhoesTela.php?Cod_Cultura=" + codCultura;

            RequestQueue queue = Volley.newRequestQueue(Talhoes.this);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Parsing json
                    //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i< array.length(); i++){
                            JSONObject obj = array.getJSONObject(i);
                            TalhaoModel u = new TalhaoModel();
                            u.setCod_Talhao(obj.getInt("Cod_Talhao"));
                            u.setFk_Cod_Cultura(obj.getInt("fk_Cultura_Cod_Cultura"));
                            u.setFk_Cod_Planta(obj.getInt("fk_Planta_Cod_Planta"));
                            u.setNome(obj.getString("Nome"));
                            boolean auxAplicado;
                            if(obj.getInt("Aplicado") != 0){
                                auxAplicado = true;
                            }else{
                                auxAplicado = false;
                            }
                            u.setAplicado(auxAplicado);
                            cards.add(u);
                        }
                        iniciarRecyclerView();
                        mDialog.dismiss();
                    } catch (JSONException e) {
                        Toast.makeText(Talhoes.this, e.toString(), Toast.LENGTH_LONG).show();
                        mDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Talhoes.this,error.toString(), Toast.LENGTH_LONG).show();
                    mDialog.dismiss();
                }
            }));

        }
    }

    private void iniciarRecyclerView(){

        Log.d(TAG, "iniciarRecyclerView:  init iniciar");
        RecyclerView rv = findViewById(R.id.RVTalhoes);
        TalhaoCardAdapter adapter = new TalhaoCardAdapter(this, cards, codCultura,Cod_Propriedade, nomePropriedade, nome);

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
