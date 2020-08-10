package my.aplication.manejointeligentedepragas;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import my.aplication.manejointeligentedepragas.Crontroller.Controller_Usuario;

import com.example.manejointeligentedepragas.R;

import org.json.JSONException;
import org.json.JSONObject;

public class AdicionarPropriedade extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    Button salvarPropriedade;

    EditText nomePropriedade;

    EditText cidadePropriedade;

    Boolean click = true; // verificação de click (nao adicionar dados duplos)

    Boolean editar = false; //

    String NomeP;

    String CidadeP;

    String EstadoP;

    Integer CodP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_propriedade);

        NomeP = getIntent().getStringExtra("NomeP");

        CidadeP = getIntent().getStringExtra("CidadeP");

        EstadoP = getIntent().getStringExtra("EstadoP");

        CodP = getIntent().getIntExtra("CodP", 0);


        final Spinner dropdown = findViewById(R.id.dropdownEstado);
        String[] items = new String[] {"AC","AL","AP","AM","BA","CE","DF","ES","GO","MA","MT","MS","MG","PA","PB","PR","PE","PI","RJ","RN","RS","RO","RR","SC","SP","SE","TO"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);


        salvarPropriedade = findViewById(R.id.btnSalvarPropriedade);
        nomePropriedade = findViewById(R.id.etEscreveNomeProp);
        cidadePropriedade = findViewById(R.id.etEscreveCidadeProp);

        //menu novo
        Toolbar toolbar = findViewById(R.id.toolbar_add_propriedade);
        setSupportActionBar(toolbar);
        drawerLayout= findViewById(R.id.drawer_layout_add_propriedade);
        NavigationView navigationView = findViewById(R.id.nav_view_add_propriedade);
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

        setTitle("MIP² | Adicionar propriedade");

        if(CodP != 0){
            editar = true;
            nomePropriedade.setText(NomeP);
            cidadePropriedade.setText(CidadeP);
            dropdown.setSelection(adapter.getPosition(EstadoP)); //Muda para o ID com o nome passado
        }

        salvarPropriedade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click) {
                    if(editar){
                        EditarPropriedade(dropdown);
                    }else{
                        SalvarPropriedade(dropdown);
                    }

                    click = false;
                }else{
                    Toast.makeText(AdicionarPropriedade.this, "Aguarde um momento..." ,Toast.LENGTH_LONG).show();
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

    public void SalvarPropriedade(Spinner dropdown){
        final String estado = dropdown.getSelectedItem().toString();
        final String nome = nomePropriedade.getText().toString();
        final String cidade = cidadePropriedade.getText().toString();

        if(nome.isEmpty()){
            Toast.makeText(AdicionarPropriedade.this, "Nome da propriedade é obrigatório!" ,Toast.LENGTH_LONG).show();
        }else if(cidade.isEmpty()){
            Toast.makeText(AdicionarPropriedade.this, "Cidade é obrigatório!" ,Toast.LENGTH_LONG).show();
        }else{
            Controller_Usuario cu = new Controller_Usuario(getBaseContext());
            String url = "http://mip2.000webhostapp.com/resgatarCodigoProdutor.php?Cod_Usuario=" + cu.getUser().getCod_Usuario();


            RequestQueue queue = Volley.newRequestQueue(AdicionarPropriedade.this);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Parsing json
                    //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                    try {
                        //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                        JSONObject obj =  new JSONObject(response);
                        String cod = (String) obj.getString("Cod_Produtor");



                        String url = "http://mip2.000webhostapp.com/adicionarPropriedade.php?Nome="+nome+"&&Cidade="+cidade+
                                "&&Estado="+estado+"&&fk_Produtor_Cod_Produtor="+cod;


                        RequestQueue queue = Volley.newRequestQueue(AdicionarPropriedade.this);
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
                                        Intent k = new Intent(AdicionarPropriedade.this, Propriedades.class);
                                        startActivity(k);
                                    }else{
                                        Toast.makeText(AdicionarPropriedade.this, "Propriedade não cadastrada! Tente novamente",Toast.LENGTH_LONG).show();
                                    }




                                } catch (JSONException e) {
                                    Toast.makeText(AdicionarPropriedade.this, e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(AdicionarPropriedade.this,error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }));



                    } catch (JSONException e) {
                        Toast.makeText(AdicionarPropriedade.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AdicionarPropriedade.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));
        }
    }

    public void EditarPropriedade(Spinner dropdown){
        final String estado = dropdown.getSelectedItem().toString();
        final String nome = nomePropriedade.getText().toString();
        final String cidade = cidadePropriedade.getText().toString();

        if(nome.isEmpty()){
            Toast.makeText(AdicionarPropriedade.this, "Nome da propriedade é obrigatório!" ,Toast.LENGTH_LONG).show();
        }else if(cidade.isEmpty()){
            Toast.makeText(AdicionarPropriedade.this, "Cidade é obrigatório!" ,Toast.LENGTH_LONG).show();
        }else{


            String url = "http://mip2.000webhostapp.com/editarPropriedade.php?Cod_Propriedade=" + CodP +
                            "&&Cidade="+cidade+"&&Estado="+estado+"&&Nome="+nome ;

            RequestQueue queue = Volley.newRequestQueue(AdicionarPropriedade.this);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Parsing json
                    //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                    try {
                        JSONObject obj = new JSONObject(response);
                        boolean confirmacao = obj.getBoolean("confirmacao");
                        if(confirmacao){
                            Intent k = new Intent(AdicionarPropriedade.this, Propriedades.class);
                            Toast.makeText(AdicionarPropriedade.this, "Propriedade alterada com sucesso!",Toast.LENGTH_LONG).show();
                            startActivity(k);
                        }else{
                            Toast.makeText(AdicionarPropriedade.this, "Propriedade não editada! Tente novamente",Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        Toast.makeText(AdicionarPropriedade.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AdicionarPropriedade.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));
        }
    }





}
