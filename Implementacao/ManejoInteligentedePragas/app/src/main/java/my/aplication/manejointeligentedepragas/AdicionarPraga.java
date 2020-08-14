package my.aplication.manejointeligentedepragas;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import my.aplication.manejointeligentedepragas.Auxiliar.Utils;
import my.aplication.manejointeligentedepragas.Crontroller.Controller_PlanoAmostragem;
import my.aplication.manejointeligentedepragas.Crontroller.Controller_Praga;
import my.aplication.manejointeligentedepragas.Crontroller.Controller_PresencaPraga;
import my.aplication.manejointeligentedepragas.Crontroller.Controller_Usuario;
import my.aplication.manejointeligentedepragas.model.PlanoAmostragemModel;
import my.aplication.manejointeligentedepragas.model.PresencaPragaModel;

import com.example.manejointeligentedepragas.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.ConnectionBuddyConfiguration;
import com.zplesac.connectionbuddy.interfaces.ConnectivityChangeListener;
import com.zplesac.connectionbuddy.models.ConnectivityEvent;

public class AdicionarPraga extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ArrayList<String> nomePraga = new ArrayList<String>();
    ArrayList<Integer> codPraga = new ArrayList<Integer>();

    ArrayList<String> pragasAdd = new ArrayList<String>();

    Button salvarPraga;

    Integer codigoSelecionado;

    String nomeSelecionado;

    boolean aplicado;

    Boolean click = true; // verificação de click (nao adicionar dados duplos)

    int codCultura;

    int Cod_Propriedade;

    String nomePropriedade;

    String nome;

    int Cod_Talhao;

    String NomeTalhao;

    Button InfoPraga;

    int Cod_Planta;

    private Dialog mDialog;

    private DrawerLayout drawerLayout;

    ArrayList<PresencaPragaModel> presencaPragaModels = new ArrayList();

    ArrayList<PlanoAmostragemModel> planoAmostragemModels = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_praga);

        ConnectionBuddyConfiguration networkInspectorConfiguration = new ConnectionBuddyConfiguration.Builder(this).build();
        ConnectionBuddy.getInstance().init(networkInspectorConfiguration);

        openDialog();

        Cod_Talhao = getIntent().getIntExtra("Cod_Talhao", 0);
        NomeTalhao = getIntent().getStringExtra("NomeTalhao");
        aplicado = getIntent().getBooleanExtra("Aplicado", false);
        codCultura = getIntent().getIntExtra("Cod_Cultura", 0);
        nome = getIntent().getStringExtra("NomeCultura");
        Cod_Propriedade = getIntent().getIntExtra("Cod_Propriedade", 0);
        nomePropriedade = getIntent().getStringExtra("nomePropriedade");
        Cod_Planta = getIntent().getIntExtra("Cod_Planta",0);

        salvarPraga = findViewById(R.id.btnSalvrCultura);
        InfoPraga = findViewById(R.id.btnInfoPraga);

        pragasAdd = getIntent().getStringArrayListExtra("pragasAdd");

        Spinner dropdown = findViewById(R.id.dropdownPraga);
        mDialog.dismiss();


        //menu novo
        Toolbar toolbar = findViewById(R.id.toolbar_add_praga);
        setSupportActionBar(toolbar);
        drawerLayout= findViewById(R.id.drawer_layout_add_praga);
        NavigationView navigationView = findViewById(R.id.nav_view_add_praga);
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

        setTitle("MIP² | "+nome+": "+NomeTalhao);

        Controller_Praga cp = new Controller_Praga(AdicionarPraga.this);
        nomePraga=cp.getPragaAtingeNome(Cod_Planta);
        codPraga=cp.getPragaAtingeCodigo(Cod_Planta);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, nomePraga);
        dropdown.setAdapter(adapter);

        // ResgatarPragas(dropdown, codCultura);

        salvarPraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click) {
                    SalvarPraga();
                }else{
                    Toast.makeText(AdicionarPraga.this, "Aguarde um momento..." ,Toast.LENGTH_LONG).show();
                }
            }
        });

        InfoPraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(AdicionarPraga.this, InfoPraga.class);
                k.putExtra("Cod_Praga", codigoSelecionado);
                startActivity(k);
            }
        });

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                codigoSelecionado = codPraga.get(position);
                nomeSelecionado = nomePraga.get(position);
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
            Intent i = new Intent(AdicionarPraga.this,Pragas.class);
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
    }

    protected void onStart() {
        super.onStart();
        ConnectionBuddy.getInstance().registerForConnectivityEvents(this, new ConnectivityChangeListener() {
            @Override
            public void onConnectionChange(ConnectivityEvent event) {
                Utils u = new Utils();
                if(!u.isConected(getBaseContext()))
                {
                    //Toast.makeText(AcoesCultura.this,"Você está offline!", Toast.LENGTH_LONG).show();
                }else{
                    final Controller_PlanoAmostragem cpa = new Controller_PlanoAmostragem(AdicionarPraga.this);
                    final Controller_PresencaPraga cpp = new Controller_PresencaPraga(AdicionarPraga.this);

                    //Toast.makeText(AcoesCultura.this,"Você está online!", Toast.LENGTH_LONG).show();

                    planoAmostragemModels = cpa.getPlanoOffline();
                    presencaPragaModels = cpp.getPresencaPragaOffline();

                    for(int i=0; i<planoAmostragemModels.size(); i++){
                        SalvarPlanos(planoAmostragemModels.get(i));
                    }
                    cpa.removerPlano();

                    for(int i=0; i<presencaPragaModels.size(); i++){
                        SalvarPresencas(presencaPragaModels.get(i));
                    }
                    cpp.updatePresencaSyncStatus();


                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        ConnectionBuddy.getInstance().unregisterFromConnectivityEvents(this);
    }


    public void SalvarPlanos(PlanoAmostragemModel pam){
        Controller_Usuario cu = new Controller_Usuario(AdicionarPraga.this);
        String Autor = cu.getUser().getNome();

        String url = "https://mip.software/phpapp/salvaPlanoAmostragem.php?Cod_Talhao=" + pam.getFk_Cod_Talhao()
                +"&&Data="+pam.getDate()
                +"&&PlantasInfestadas="+pam.getPlantasInfestadas()
                +"&&PlantasAmostradas="+pam.getPlantasAmostradas()
                +"&&Cod_Praga="+pam.getFk_Cod_Praga()
                +"&&Autor="+Autor;

        RequestQueue queue = Volley.newRequestQueue(AdicionarPraga.this);
        queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AdicionarPraga.this,error.toString(), Toast.LENGTH_LONG).show();
            }
        }));
    }

    public void SalvarPresencas(PresencaPragaModel ppm){
        String url = "https://mip.software/phpapp/updatePraga.php?Cod_Praga="+ppm.getFk_Cod_Praga()+
                "&&Cod_Talhao="+ppm.getFk_Cod_Talhao()+"&&Status="+ppm.getStatus();
        RequestQueue queue = Volley.newRequestQueue(AdicionarPraga.this);
        queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AdicionarPraga.this,error.toString(), Toast.LENGTH_LONG).show();
            }
        }));
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
                Intent pp = new Intent(this, Sobre.class);
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


    public void ResgatarPragas(final Spinner dropdown, int codCultura){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            mDialog.dismiss();
            Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet
            String url = "https://mip.software/phpapp/selecionarPragasAtinge.php?cod_Cultura=" + codCultura;
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
                            nomePraga.add(obj.getString("Nome"));
                            codPraga.add(obj.getInt("Cod_Praga"));
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, nomePraga);
                        dropdown.setAdapter(adapter);

                        mDialog.dismiss();

                    } catch (JSONException e) {
                        mDialog.dismiss();
                        Toast.makeText(AdicionarPraga.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mDialog.dismiss();
                    Toast.makeText(AdicionarPraga.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }
    }


    public void SalvarPraga(){
        final Controller_PresencaPraga cpp = new Controller_PresencaPraga(AdicionarPraga.this);
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            mDialog.dismiss();
            if(pragasAdd.contains(nomeSelecionado)){
                mDialog.dismiss();
                Toast.makeText(this,"Esta praga já existe em sua cultura.", Toast.LENGTH_LONG).show();
            }else{
                cpp.addPresencaSemCod(1,Cod_Talhao,codigoSelecionado,1);
                Intent k = new Intent(AdicionarPraga.this, Pragas.class);
                k.putExtra("Cod_Talhao", Cod_Talhao);
                k.putExtra("NomeTalhao", NomeTalhao);
                k.putExtra("Cod_Cultura", codCultura);
                k.putExtra("NomeCultura", nome);
                k.putExtra("Cod_Propriedade", Cod_Propriedade);
                k.putExtra("Aplicado", aplicado);
                k.putExtra("nomePropriedade", nomePropriedade);
                k.putExtra("Cod_Planta", Cod_Planta);
                startActivity(k);
            }
        }else if(pragasAdd.contains(nomeSelecionado)){
            mDialog.dismiss();
            Toast.makeText(this,"Esta praga já existe em sua cultura.", Toast.LENGTH_LONG).show();
        }else{ // se tem acesso à internet
            click = false;
            String url = "https://mip.software/phpapp/adicionarPraga.php?Cod_Praga="+codigoSelecionado+
                    "&&Cod_Talhao="+Cod_Talhao;
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Parsing json
                    //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                    try {
                        //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();

                        JSONObject obj1 = new JSONObject(response);
                        boolean confirmacao = obj1.getBoolean("confirmacao");

                        if(confirmacao){
                            mDialog.dismiss();
                            cpp.addPresencaSemCod(1,Cod_Talhao,codigoSelecionado,1);
                            Intent k = new Intent(AdicionarPraga.this, Pragas.class);
                            k.putExtra("Cod_Talhao", Cod_Talhao);
                            k.putExtra("NomeTalhao", NomeTalhao);
                            k.putExtra("Cod_Cultura", codCultura);
                            k.putExtra("NomeCultura", nome);
                            k.putExtra("Cod_Propriedade", Cod_Propriedade);
                            k.putExtra("Aplicado", aplicado);
                            k.putExtra("nomePropriedade", nomePropriedade);
                            k.putExtra("Cod_Planta", Cod_Planta);
                            startActivity(k);
                        }else{
                            Toast.makeText(AdicionarPraga.this, "Praga não cadastrada! Tente novamente",Toast.LENGTH_LONG).show();
                            mDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(AdicionarPraga.this, e.toString(), Toast.LENGTH_LONG).show();
                        mDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AdicionarPraga.this,error.toString(), Toast.LENGTH_LONG).show();
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
