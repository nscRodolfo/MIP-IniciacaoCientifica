package com.example.manejointeligentedepragas;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.manejointeligentedepragas.Auxiliar.Utils;
import com.example.manejointeligentedepragas.Crontroller.Controller_Praga;
import com.example.manejointeligentedepragas.Crontroller.Controller_PresencaPraga;
import com.example.manejointeligentedepragas.Crontroller.Controller_UltimosPlanos;
import com.example.manejointeligentedepragas.Crontroller.Controller_Usuario;
import com.example.manejointeligentedepragas.model.PragaModel;
import com.example.manejointeligentedepragas.model.PresencaPragaModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AcoesCultura extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    public RelativeLayout rlVPA;

    public RelativeLayout rlRPA;

    public RelativeLayout rlGR;

    int Cod_Propriedade;
    String nomePropriedade;
    int Cod_Talhao;
    String NomeTalhao;
    int codCultura;
    String nome;
    int Cod_Planta;

    ArrayList<PresencaPragaModel> localPresenca = new ArrayList<>();
    ArrayList<PresencaPragaModel> webPresenca = new ArrayList<>();
    boolean aplicado;

    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acoes_cultura);

        openDialog();

        /*
        View actionBar = getLayoutInflater().inflate(R.layout.actionbar, null);

        TextView tvTitulo = (TextView) actionBar.findViewById(R.id.tvTituloActionBar);

        String nome = getIntent().getStringExtra("NomeCultura");

        tvTitulo.setText(nome);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        */

        Cod_Talhao = getIntent().getIntExtra("Cod_Talhao", 0);
        NomeTalhao = getIntent().getStringExtra("NomeTalhao");
        Cod_Propriedade = getIntent().getIntExtra("Cod_Propriedade", 0);
        nomePropriedade = getIntent().getStringExtra("nomePropriedade");
        codCultura = getIntent().getIntExtra("Cod_Cultura", 0);
        nome = getIntent().getStringExtra("NomeCultura");
        aplicado = getIntent().getBooleanExtra("Aplicado", false);
        Cod_Planta = getIntent().getIntExtra("Cod_Planta",0);

        rlGR= findViewById(R.id.rlGR);


        //menu novo
        Toolbar toolbar = findViewById(R.id.toolbar_acoes_cultura);
        setSupportActionBar(toolbar);
        drawerLayout= findViewById(R.id.drawer_layout_acoes_cultura);
        NavigationView navigationView = findViewById(R.id.nav_view_acoes_cultura);
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

        resgatarPresencaPraga();
        resgatarDatasUltimossPlanos();


        setTitle("MIP² | "+nome+": "+NomeTalhao);

        rlVPA = findViewById(R.id.rlVPA);
        rlVPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AcoesCultura.this, Pragas.class);
                i.putExtra("Cod_Talhao", Cod_Talhao);
                i.putExtra("NomeTalhao", NomeTalhao);
                i.putExtra("Cod_Cultura", codCultura);
                i.putExtra("NomeCultura", nome);
                i.putExtra("Cod_Propriedade", Cod_Propriedade);
                i.putExtra("Aplicado", aplicado);
                i.putExtra("nomePropriedade", nomePropriedade);
                i.putExtra("Cod_Planta", Cod_Planta);
                startActivity(i);
            }
        });

        rlRPA = findViewById(R.id.rlRPA);
        rlRPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AcoesCultura.this, RealizarPlano.class);
                i.putExtra("Cod_Talhao", Cod_Talhao);
                i.putExtra("NomeTalhao", NomeTalhao);
                i.putExtra("Cod_Cultura", codCultura);
                i.putExtra("NomeCultura", nome);
                i.putExtra("Cod_Propriedade", Cod_Propriedade);
                i.putExtra("Aplicado", aplicado);
                i.putExtra("nomePropriedade", nomePropriedade);
                i.putExtra("Cod_Planta", Cod_Planta);
                startActivity(i);
            }
        });
        rlGR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AcoesCultura.this, Relatorios.class);
                i.putExtra("Cod_Talhao", Cod_Talhao);
                i.putExtra("NomeTalhao", NomeTalhao);
                i.putExtra("Cod_Cultura", codCultura);
                i.putExtra("NomeCultura", nome);
                i.putExtra("Cod_Propriedade", Cod_Propriedade);
                i.putExtra("Aplicado", aplicado);
                i.putExtra("nomePropriedade", nomePropriedade);
                i.putExtra("Cod_Planta", Cod_Planta);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            Intent i = new Intent(AcoesCultura.this,Talhoes.class);
            i.putExtra("Cod_Propriedade", Cod_Propriedade);
            i.putExtra("nomePropriedade", nomePropriedade);
            i.putExtra("Cod_Cultura", codCultura);
            i.putExtra("NomeCultura", nome);
            i.putExtra("Aplicado", aplicado);
            i.putExtra("Cod_Planta", Cod_Planta);
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

    //ter o presença praga para realizar o plano sem precisar entrar em pragas
    private void resgatarPresencaPraga() {
        //Log.d(TAG, "resgatarDados: resgatou");
        final Controller_PresencaPraga cpp = new Controller_PresencaPraga(AcoesCultura.this);
        Controller_Praga cp = new Controller_Praga(AcoesCultura.this);

        Utils u = new Utils();
        if (!u.isConected(getBaseContext())) {
            localPresenca = cpp.getPresencaPraga(Cod_Talhao);
            for(int i=0; i<localPresenca.size();i++){
                PragaModel pm = new PragaModel();
                pm.setCod_Praga(localPresenca.get(i).getFk_Cod_Praga());
                pm.setNome(cp.getNome(localPresenca.get(i).getFk_Cod_Praga()));
                pm.setStatus(localPresenca.get(i).getStatus());
            }
            mDialog.dismiss();
        } else { // se tem acesso à internet

            String url = "http://mip2.000webhostapp.com/resgatarPragas.php?Cod_Talhao=" + Cod_Talhao;

            RequestQueue queue = Volley.newRequestQueue(AcoesCultura.this);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Parsing json
                    //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            PragaModel u = new PragaModel();
                            u.setCod_Praga(obj.getInt("Cod_Praga"));
                            u.setNome(obj.getString("Nome"));
                            u.setStatus(obj.getInt("Status"));
                            int codPresenca = obj.getInt("Cod_PresencaPraga");
                            cpp.addPresenca(u.getStatus(), Cod_Talhao, u.getCod_Praga(), 0, codPresenca);
                            webPresenca.add(new PresencaPragaModel(u.getCod_Praga(),Cod_Talhao,codPresenca,u.getStatus(),0));
                        }

                        localPresenca = cpp.getPresencaPraga(Cod_Talhao);


                        //Toast.makeText(Talhoes.this, "AQui", Toast.LENGTH_LONG).show();
                        if(webPresenca.contains(localPresenca)) {

                        }else {
                            //fazer verificação do sync_Status
                        }
                        mDialog.dismiss();
                    } catch (JSONException e) {
                        Toast.makeText(AcoesCultura.this, e.toString(), Toast.LENGTH_LONG).show();
                        mDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AcoesCultura.this, error.toString(), Toast.LENGTH_LONG).show();
                    mDialog.dismiss();
                }
            }));

        }
    }

    private void resgatarDatasUltimossPlanos() {
        //Log.d(TAG, "resgatarDados: resgatou");
        final Controller_UltimosPlanos cup = new Controller_UltimosPlanos(AcoesCultura.this);
        Utils u = new Utils();
        if (!u.isConected(getBaseContext())) {

            mDialog.dismiss();
        } else { // se tem acesso à internet

            cup.removerUltimosPlanos();
            String url = "http://mip2.000webhostapp.com/getUltimosPlanos.php?Cod_Talhao=" + Cod_Talhao;

            RequestQueue queue = Volley.newRequestQueue(AcoesCultura.this);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Parsing json
                    //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            String data = obj.getString("Data");
                            int fk_codPraga = obj.getInt("fk_Praga_Cod_Praga");
                            cup.addUltimosPlanos(data,Cod_Talhao,fk_codPraga);
                        }
                        mDialog.dismiss();
                    } catch (JSONException e) {
                        Toast.makeText(AcoesCultura.this, e.toString(), Toast.LENGTH_LONG).show();
                        mDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AcoesCultura.this, error.toString(), Toast.LENGTH_LONG).show();
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
