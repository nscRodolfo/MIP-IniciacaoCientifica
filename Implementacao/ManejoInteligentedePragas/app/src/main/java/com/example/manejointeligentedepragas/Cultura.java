package com.example.manejointeligentedepragas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import com.example.manejointeligentedepragas.Crontroller.Controller_Cultura;
import com.example.manejointeligentedepragas.Crontroller.Controller_Propriedade;
import com.example.manejointeligentedepragas.Crontroller.Controller_Usuario;
import com.example.manejointeligentedepragas.RecyclerViewAdapter.CulturaCardAdapter;
import com.example.manejointeligentedepragas.RecyclerViewAdapter.PropriedadeCardAdapter;
import com.example.manejointeligentedepragas.model.CulturaModel;
import com.example.manejointeligentedepragas.model.PropriedadeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Cultura extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public FloatingActionButton fabAddCultura;
    public TextView tvAddCultura;
    public TextView tvNumFunc;
    public CardView cardCultura;
    public CardView cardFuncionario;
    public String tipoUsu;

    private static final String TAG = "Cultura";
    private ArrayList<CulturaModel> cards = new ArrayList<>();
    private ArrayList<CulturaModel> web = new ArrayList<>();
    private ArrayList<CulturaModel> local = new ArrayList<>();
    Integer Cod_Propriedade;
    ArrayList<String> plantasadd = new ArrayList<String>();
    Integer numFunc = 0;
    String nomePropriedade;

    private Dialog mDialog;

    private DrawerLayout drawerLayout;


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

        Utils u = new Utils();
        if(!u.isConected(getBaseContext())) {
            Controller_Cultura cc = new Controller_Cultura(Cultura.this);
            cards = cc.getCultura(Cod_Propriedade);
            if(cards.size() == 0){
                AlertDialog.Builder dlgBox = new AlertDialog.Builder(Cultura.this);
                dlgBox.setTitle("Aviso");
                dlgBox.setMessage("Para possuir os dados dessa cultura enquanto está sem internet, você precisa acessá-la online pelo menos uma vez.");
                dlgBox.setPositiveButton("Entendi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dlgBox.show();
            }
            tvAddCultura.setVisibility(View.GONE);
            fabAddCultura.hide();
            cardFuncionario.setVisibility(View.GONE);
            mDialog.dismiss();
            iniciarRecyclerView();
        }else{
            if(tipoUsu.equals("Funcionario")){
                tvAddCultura.setVisibility(View.GONE);
                fabAddCultura.hide();
                cardFuncionario.setVisibility(View.GONE);
                resgatarDados();
            }else if(tipoUsu.equals("Produtor")){
                resgatarFunc();
                resgatarDados();
            }

        }

        //menu novo
        Toolbar toolbar = findViewById(R.id.toolbar_cultura);
        setSupportActionBar(toolbar);
        drawerLayout= findViewById(R.id.drawer_layout_cultura);
        NavigationView navigationView = findViewById(R.id.nav_view_cultura);
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
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            Intent i = new Intent(Cultura.this,Propriedades.class);
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
                            u.setFk_Cod_Planta(obj.getInt("fk_Planta_Cod_Planta")); // adicionado
                            u.setnomePlanta(obj.getString("NomePlanta"));
                            u.setNumeroTalhoes(obj.getInt("count_talhao"));
                            u.setTamanhoCultura(obj.getDouble("TamanhoDaCultura"));
                            web.add(u);
                            plantasadd.add(obj.getString("NomePlanta"));
                        }
                        Controller_Cultura cc= new Controller_Cultura(Cultura.this);
                        local = cc.getCultura(Cod_Propriedade);
                        if(web.size() == local.size()) {
                            if(!web.equals(local)) {
                                local = web;
                                for (int i = 0; i < local.size(); i++) {
                                    cc.removerCulturaEspecifica(local.get(i));
                                    cc.addCultura(local.get(i));
                                }
                                cards = local;
                            }else{
                                cards = local;
                            }
                        }else{
                            local = web;
                            for (int i = 0; i < local.size(); i++) {
                                cc.removerCulturaEspecifica(local.get(i));
                                cc.addCultura(local.get(i));
                            }
                            cards = local;
                        }
                        iniciarRecyclerView();
                        mDialog.dismiss();
                    } catch (JSONException e) {
                        Toast.makeText(Cultura.this, e.toString(), Toast.LENGTH_LONG).show();
                        mDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   Toast.makeText(Cultura.this,error.toString(), Toast.LENGTH_LONG).show();
                    mDialog.dismiss();
                }
            }));

        }
    }

    private void iniciarRecyclerView(){
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
                        mDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Cultura.this,error.toString(), Toast.LENGTH_LONG).show();
                    mDialog.dismiss();
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
