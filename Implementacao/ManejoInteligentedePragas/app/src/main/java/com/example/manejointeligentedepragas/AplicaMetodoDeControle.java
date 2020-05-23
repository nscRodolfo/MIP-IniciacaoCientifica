package com.example.manejointeligentedepragas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AplicaMetodoDeControle extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    int codPropriedade;
    int codPraga;
    int codCultura;
    String nome;
    boolean aplicado;
    String nomePropriedade;

    TextView tvMetodo;
    Spinner dropdown;
    Button btnSelecionaDepois;
    Button btnInfoMetodo;
    Button btnAplicaMetodo;

    ArrayList<String> nomeMetodo = new ArrayList<String>();
    ArrayList<Integer> codMetodo = new ArrayList<Integer>();
    ArrayList<Integer> intervaloAplicacao = new ArrayList<Integer>();

    String nomeSelecionado;
    Integer codSelecionado;
    Integer intervaloSelecionado;

    //data
    SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
    Date data = new Date();
    String dataFormatada = formataData.format(data);

    int IntervaloAplicacao;

    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aplica_metodo_de_controle);

        codPropriedade = getIntent().getIntExtra("Cod_Propriedade", 0);
        codCultura = getIntent().getIntExtra("Cod_Cultura", 0);
        nome = getIntent().getStringExtra("NomeCultura");
        codPraga = getIntent().getIntExtra("Cod_Praga", 0);
        aplicado = getIntent().getBooleanExtra("Aplicado", false);
        nomePropriedade = getIntent().getStringExtra("nomePropriedade");

        tvMetodo = findViewById(R.id.tvConfMetodo);
        dropdown = findViewById(R.id.dropdownConfMetodo);
        btnSelecionaDepois = findViewById(R.id.btnConfSelecionaDepois);
        btnInfoMetodo = findViewById(R.id.btnInfoMetodo);
        btnAplicaMetodo = findViewById(R.id.btnAplicaMetodo);

        //menu novo
        Toolbar toolbar = findViewById(R.id.toolbar_aplicaMetodo);
        setSupportActionBar(toolbar);
        drawerLayout= findViewById(R.id.drawer_layout_aplicaMetodo);
        NavigationView navigationView = findViewById(R.id.nav_view_aplicaMetodo);
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

        setTitle("MIP² | "+nome);
        resgataMetodos(dropdown, codPraga);

        btnSelecionaDepois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AplicaMetodoDeControle.this, AcoesCultura.class);
                i.putExtra("Cod_Propriedade", codPropriedade);
                i.putExtra("Cod_Cultura", codCultura);
                i.putExtra("NomeCultura", nome);
                i.putExtra("Aplicado", aplicado);
                i.putExtra("nomePropriedade", nomePropriedade);
                startActivity(i);
            }
        });

        btnAplicaMetodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //salva no aplicacao
                //muda status para amarelo = 1
                //muda aplicado
                ResgataMetodosSemIntervalo(codSelecionado);
            }
        });

        btnInfoMetodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AplicaMetodoDeControle.this, infoMetodo.class);
                i.putExtra("Cod_Metodo", codSelecionado);
                startActivity(i);
            }
        });



        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                codSelecionado = codMetodo.get(position);
                nomeSelecionado = nomeMetodo.get(position);
                intervaloSelecionado = intervaloAplicacao.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
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




    public void resgataMetodos(final Spinner dropdown, int codPraga){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet
            String url = "http://mip2.000webhostapp.com/selecionarMetodoConf.php?cod_Praga=" + codPraga;


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
                            nomeMetodo.add(obj.getString("Nome"));
                            codMetodo.add(obj.getInt("Cod_MetodoControle"));
                            intervaloAplicacao.add(obj.getInt("IntervaloAplicacao"));
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, nomeMetodo);
                        dropdown.setAdapter(adapter);


                    } catch (JSONException e) {
                        Toast.makeText(AplicaMetodoDeControle.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AplicaMetodoDeControle.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }
    }

    public void FuncaoAplicacao(int codCultura, int codPraga, int codMetodo, String data){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet
            Controller_Usuario cu = new Controller_Usuario(getBaseContext());
            String Autor = cu.getUser().getNome();

            String url = "http://mip2.000webhostapp.com/aplicacao.php?Cod_Praga=" + codPraga + "&&Cod_Cultura="+ codCultura + "&&Data=" + data + "&&Cod_Metodo="+codMetodo+"&&Autor="+Autor;
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AplicaMetodoDeControle.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }
    }

    public void ResgataMetodosSemIntervalo(int codM){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet
            String url = "http://mip2.000webhostapp.com/infoMetodo.php?Cod_Metodo="+codM;

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
                            IntervaloAplicacao  = obj.getInt("IntervaloAplicacao");
                        }
                        if (IntervaloAplicacao == 0){
                            AlertDialog.Builder dlgBox = new AlertDialog.Builder(AplicaMetodoDeControle.this);
                            dlgBox.setTitle("Aviso:");
                            dlgBox.setMessage("Esse método é de origem comercial, por favor, verifique no produto e aguarde o intervalo indicado entre as aplicações para evitar fitotoxidez na cultura.");
                            dlgBox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FuncaoAplicacao(codCultura,codPraga,codSelecionado,dataFormatada);
                                    aplicado = true;
                                    Intent i = new Intent(AplicaMetodoDeControle.this, Pragas.class);
                                    i.putExtra("Cod_Propriedade", codPropriedade);
                                    i.putExtra("Cod_Cultura", codCultura);
                                    i.putExtra("NomeCultura", nome);
                                    i.putExtra("Aplicado", aplicado);
                                    i.putExtra("nomePropriedade", nomePropriedade);
                                    startActivity(i);
                                }
                            });
                            dlgBox.show();
                        }else{
                            FuncaoAplicacao(codCultura,codPraga,codSelecionado,dataFormatada);
                            aplicado = true;
                            Intent i = new Intent(AplicaMetodoDeControle.this, Pragas.class);
                            i.putExtra("Cod_Propriedade", codPropriedade);
                            i.putExtra("Cod_Cultura", codCultura);
                            i.putExtra("NomeCultura", nome);
                            i.putExtra("Aplicado", aplicado);
                            i.putExtra("nomePropriedade", nomePropriedade);
                            startActivity(i);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(AplicaMetodoDeControle.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AplicaMetodoDeControle.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }
    }
}
