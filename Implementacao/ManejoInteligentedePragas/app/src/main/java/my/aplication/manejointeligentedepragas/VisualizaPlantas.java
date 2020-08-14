package my.aplication.manejointeligentedepragas;

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
import my.aplication.manejointeligentedepragas.Auxiliar.Utils;
import my.aplication.manejointeligentedepragas.Crontroller.Controller_Usuario;

import com.example.manejointeligentedepragas.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VisualizaPlantas extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ArrayList<String> nomePlantas = new ArrayList<>();
    ArrayList<Integer> codPlantas = new ArrayList<>();

    //pesquisa
    EditText edtPesquisaPlantas;
    Boolean pesquisado = false;
    private ArrayList<String> pesquisa = new ArrayList<String>();
    private ArrayList<Integer> codPlantaPesquisa = new ArrayList<Integer>();

    private DrawerLayout drawerLayout;

    private String tipoUsu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiza_plantas);

        Utils u = new Utils();
        if(!u.isConected(getBaseContext())) {
            ExibeCaixaDialogo();
        }else{

        }
        edtPesquisaPlantas = findViewById(R.id.PesquisaPlantas);
        final ListView listView = findViewById(R.id.ListViewPlantas);

        //menu novo
        Toolbar toolbar = findViewById(R.id.toolbar_visuPlanta);
        setSupportActionBar(toolbar);
        drawerLayout= findViewById(R.id.drawer_layout_visuPlanta);
        NavigationView navigationView = findViewById(R.id.nav_view_visuPlanta);
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

        setTitle("MIP² | Plantas");

        //esconder teclado
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ResgataPlantas(listView);

        //pesquisa
        edtPesquisaPlantas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Pesquisar();
                listView.setAdapter(new ArrayAdapter<String>(VisualizaPlantas.this, android.R.layout.simple_list_item_1, pesquisa));
                if(s==null){
                    pesquisado = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Pesquisar();
                listView.setAdapter(new ArrayAdapter<String>(VisualizaPlantas.this, android.R.layout.simple_list_item_1, pesquisa));
                if(s==null){
                    pesquisado = false;
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(pesquisado ==true) {
                    Intent i= new Intent(VisualizaPlantas.this,infoPlanta.class);
                    i.putExtra("Cod_Planta",codPlantaPesquisa.get(position));
                    startActivity(i);
                }else{
                    Intent i= new Intent(VisualizaPlantas.this,infoPlanta.class);
                    i.putExtra("Cod_Planta",codPlantas.get(position));
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
                Controller_Usuario cu = new Controller_Usuario(getBaseContext());
                tipoUsu = cu.getUser().getTipo();
                if(tipoUsu == null){
                    Toast.makeText(VisualizaPlantas.this,"Para acessar seu perfil, faça login!", Toast.LENGTH_LONG).show();
                }else{
                    Intent i= new Intent(this, Perfil.class);
                    startActivity(i);
                }
                break;
            case R.id.drawerProp:
                Controller_Usuario cu1 = new Controller_Usuario(getBaseContext());
                tipoUsu = cu1.getUser().getTipo();
                if(tipoUsu==null){
                    Toast.makeText(VisualizaPlantas.this,"Para acessar as propriedades, faça login!", Toast.LENGTH_LONG).show();
                }else{
                    Intent prop= new Intent(this, Propriedades.class);
                    startActivity(prop);
                }
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


    public void ResgataPlantas(final ListView listView){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            //Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet
            String url = "https://mip.software/phpapp/visualizaPlantas.php";

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
                            nomePlantas.add(obj.getString("NomePlanta"));
                            codPlantas.add(obj.getInt("Cod_Planta"));
                        }
                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<String>(VisualizaPlantas.this, android.R.layout.simple_list_item_1, nomePlantas);
                        listView.setAdapter(adapter);

                    } catch (JSONException e) {
                        Toast.makeText(VisualizaPlantas.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(VisualizaPlantas.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }
    }

    public void Pesquisar(){
        int textlength = edtPesquisaPlantas.getText().length();
        pesquisa.clear();
        codPlantaPesquisa.clear();
        pesquisado = true;

        for (int i = 0; i < nomePlantas.size(); i++ ) {
            if (textlength <= nomePlantas.get(i).length()) {
                //if (edtPesquisaPragas.getText().toString().equalsIgnoreCase((String)nomePragas.get(i).subSequence(0, textlength))) {
                if(nomePlantas.get(i).contains(edtPesquisaPlantas.getText().toString())){
                    pesquisa.add(nomePlantas.get(i));
                    codPlantaPesquisa.add(codPlantas.get(i));
                }else if(edtPesquisaPlantas.getText().toString().equalsIgnoreCase((String)nomePlantas.get(i).subSequence(0, textlength))){
                    pesquisa.add(nomePlantas.get(i));
                    codPlantaPesquisa.add(codPlantas.get(i));
                }
            }
        }
    }

    public void ExibeCaixaDialogo(){
        AlertDialog.Builder dlgBox = new AlertDialog.Builder(this);
        dlgBox.setTitle("Aviso!");
        dlgBox.setMessage("Por enquanto, você só pode acessar as informações online! Esta função será disponibilizada no futuro!");
        dlgBox.setCancelable(false);
        dlgBox.setPositiveButton("Entendi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
            }
        });
        dlgBox.show();
    }
}
