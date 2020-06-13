package com.example.manejointeligentedepragas;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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

import java.util.ArrayList;

public class VisualizaInimigos extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    int codPraga;
    ArrayList<String> nomeInimigos = new ArrayList<>();
    ArrayList<Integer> codInimigos = new ArrayList<>();

    //pesquisa
    EditText edtPesquisaInimigos;
    Boolean pesquisado = false;
    private ArrayList<String> pesquisa = new ArrayList<String>();
    private ArrayList<Integer> codInimigoPesquisa = new ArrayList<Integer>();

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiza_inimigos);

        edtPesquisaInimigos = findViewById(R.id.PesquisaInimigos);
        codPraga = getIntent().getIntExtra("Cod_Praga",0);

        final ListView listView = findViewById(R.id.ListViewInimigos);

        //menu novo
        Toolbar toolbar = findViewById(R.id.toolbar_visuInimigos);
        setSupportActionBar(toolbar);
        drawerLayout= findViewById(R.id.drawer_layout_visuInimigos);
        NavigationView navigationView = findViewById(R.id.nav_view_visuInimigos);
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



        setTitle("MIP² | Inimigos Naturais");

        //esconder teclado
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ResgataInimigos(listView,codPraga);

        //pesquisa
        edtPesquisaInimigos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Pesquisar();
                listView.setAdapter(new ArrayAdapter<String>(VisualizaInimigos.this, android.R.layout.simple_list_item_1, pesquisa));
                if(s==null){
                    pesquisado = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Pesquisar();
                listView.setAdapter(new ArrayAdapter<String>(VisualizaInimigos.this, android.R.layout.simple_list_item_1, pesquisa));
                if(s==null){
                    pesquisado = false;
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(pesquisado ==true) {
                    Intent i= new Intent(VisualizaInimigos.this,infoInimigoNatural.class);
                    i.putExtra("Cod_Inimigo",codInimigoPesquisa.get(position));
                    startActivity(i);
                }else{
                    Intent i= new Intent(VisualizaInimigos.this,infoInimigoNatural.class);
                    i.putExtra("Cod_Inimigo",codInimigos.get(position));
                    startActivity(i);
                }

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

    public void ResgataInimigos(final ListView listView, int codPraga){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet
            String url = "http://mip2.000webhostapp.com/visualizaInimigoNatural.php?Cod_Praga="+codPraga;

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
                            nomeInimigos.add(obj.getString("NomeInimigo"));
                            codInimigos.add(obj.getInt("Cod_Inimigo"));
                        }
                        if(nomeInimigos.isEmpty() && codInimigos.isEmpty()){
                            nomeInimigos.add("Esta praga não possui inimigos naturais cadastrados");
                            codInimigos.add(0);
                        }
                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<String>(VisualizaInimigos.this, android.R.layout.simple_list_item_1, nomeInimigos);
                        listView.setAdapter(adapter);

                    } catch (JSONException e) {
                        Toast.makeText(VisualizaInimigos.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(VisualizaInimigos.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }
    }

    public void Pesquisar(){
        int textlength = edtPesquisaInimigos.getText().length();
        pesquisa.clear();
        codInimigoPesquisa.clear();
        pesquisado = true;

        for (int i = 0; i < nomeInimigos.size(); i++ ) {
            if (textlength <= nomeInimigos.get(i).length()) {
                //if (edtPesquisaPragas.getText().toString().equalsIgnoreCase((String)nomePragas.get(i).subSequence(0, textlength))) {
                if(nomeInimigos.get(i).contains(edtPesquisaInimigos.getText().toString())){
                    pesquisa.add(nomeInimigos.get(i));
                    codInimigoPesquisa.add(codInimigos.get(i));
                }else if(edtPesquisaInimigos.getText().toString().equalsIgnoreCase((String)nomeInimigos.get(i).subSequence(0, textlength))){
                    pesquisa.add(nomeInimigos.get(i));
                    codInimigoPesquisa.add(codInimigos.get(i));
                }
            }
        }
    }
}
