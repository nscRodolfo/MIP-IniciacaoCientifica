package com.example.manejointeligentedepragas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.manejointeligentedepragas.ImageAdapter.ViewPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class infoPlanta extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    int codPlanta;

    TextView tvNome;
    TextView tvNomeCientifico;
    TextView tvFamilia;
    TextView tvBotanica;
    TextView tvAmbientePropicio;
    TextView tvCultivo;
    TextView tvTratosCulturais;
    TextView tvCiclo;
    TextView tvTamanhoTalhao;
    Button btnInfoPlantaPragas;
    LinearLayout sliderDotspanel;

    //pontos indicadores
    private int dotscount;
    private ImageView[] dots;

    String nome;
    String nomeCientifico;
    String familia;
    String botanica;
    String ambientePropicio;
    String cultivo;
    String tratosCulturais;
    String ciclo;
    String tamanhoTalhao;

    ArrayList<String> urlsPlantas = new ArrayList<>();

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_planta);

        tvNome = findViewById(R.id.InfoPlantaNome);
        tvNomeCientifico = findViewById(R.id.InfoPlantaNomeCientifico);
        tvFamilia = findViewById(R.id.InfoPlantaFamilia);
        tvBotanica = findViewById(R.id.InfoPlantaBotanica);
        tvAmbientePropicio = findViewById(R.id.InfoPlantaAmbientePropicio);
        tvCultivo = findViewById(R.id.InfoPlantaCultivo);
        tvTratosCulturais = findViewById(R.id.InfoPlantaTratosCulturais);
        tvCiclo = findViewById(R.id.InfoPlantaCiclo);
        tvTamanhoTalhao = findViewById(R.id.InfoPlantaTamanhoTalhao);
        btnInfoPlantaPragas = findViewById(R.id.btnInfoPlantaPragas);
        ViewPager viewPager = findViewById(R.id.ViewPagerPlanta);
        sliderDotspanel = findViewById(R.id.SliderDotsPlanta);

        codPlanta = getIntent().getIntExtra("Cod_Planta",0);

        //menu novo
        Toolbar toolbar = findViewById(R.id.toolbar_infoPlantaa);
        setSupportActionBar(toolbar);
        drawerLayout= findViewById(R.id.drawer_layout_infoPlantaa);
        NavigationView navigationView = findViewById(R.id.nav_view_infoPlantaa);
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

        ResgatarUrlPlantas(viewPager, codPlanta);
        ResgataPlantas(codPlanta);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btnInfoPlantaPragas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(infoPlanta.this, VisualizaPragas.class);
                k.putExtra("Cod_Planta",codPlanta);
                startActivity(k);
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

    public void ResgataPlantas(int codP){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet
            String url = "http://mip2.000webhostapp.com/infoPlanta.php?Cod_Planta="+codP;

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

                            nome = obj.getString("Nome");
                            nomeCientifico = obj.getString("NomeCientifico");
                            familia = obj.getString("Familia");
                            botanica = obj.getString("Botanica");
                            ambientePropicio = obj.getString("AmbientePropicio");
                            cultivo = obj.getString("Cultivo");
                            tratosCulturais = obj.getString("TratosCulturais");
                            ciclo = obj.getString("Ciclo");
                            tamanhoTalhao = obj.getString("TamanhoTalhao");
                        }

                        tvNome.setText("Nome: "+nome);
                        tvNomeCientifico.setText("Nome científico: "+nomeCientifico);
                        tvFamilia.setText("Família: "+familia);
                        tvBotanica.setText("Botanica: "+botanica);
                        tvAmbientePropicio.setText("Ambiente Propício: "+ambientePropicio);
                        tvCultivo.setText("Cultivo: "+cultivo);
                        tvTratosCulturais.setText("Tratos Culturais: "+tratosCulturais);
                        tvCiclo.setText("Ciclo: "+ciclo);
                        tvTamanhoTalhao.setText("Tamanho máximo do talhão (ha): "+tamanhoTalhao+" hectares");


                        setTitle("MIP² | "+nome);

                    } catch (JSONException e) {
                        Toast.makeText(infoPlanta.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(infoPlanta.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }
    }

    public void ResgatarUrlPlantas(final ViewPager viewPager, int codP){
        Utils u = new Utils();
        if (!u.isConected(getBaseContext())) {
            Toast.makeText(this, "Habilite a conexão com a internet", Toast.LENGTH_LONG).show();
        } else { // se tem acesso à internet

            String url = "http://mip2.000webhostapp.com/resgatarFotoPlantas.php?Cod_Planta="+codP;

            RequestQueue queue = Volley.newRequestQueue(infoPlanta.this);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Parsing json
                    //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            urlsPlantas.add("http://mip2.000webhostapp.com/imagens/plantas/"+obj.getString("FotoPlanta"));
                        }
                        ViewPagerAdapter adapterPlantas = new ViewPagerAdapter(infoPlanta.this,urlsPlantas);
                        viewPager.setAdapter(adapterPlantas);

                        dotscount = adapterPlantas.getCount();
                        dots = new ImageView[dotscount];


                        for(int i = 0; i < dotscount; i++){

                            dots[i] = new ImageView(infoPlanta.this);
                            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                            params.setMargins(8, 0, 8, 0);

                            sliderDotspanel.addView(dots[i], params);

                        }

                        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                    } catch (JSONException e) {
                        Toast.makeText(infoPlanta.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(infoPlanta.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }

    }

}
