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
import android.widget.Button;
import android.widget.EditText;
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

import my.aplication.manejointeligentedepragas.model.UsuarioModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AtualizarInfoPerfil extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    EditText etNome;
    EditText etTel;
    EditText etEmail;
    Button btnSalvar;

    int codigo;
    String tipo;
    String senha;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_info_perfil);

        etNome = findViewById(R.id.etNomeInfoPerfil);
        etTel = findViewById(R.id.etTelInfoPerfil);
        etEmail = findViewById(R.id.etEmailInfoPerfil);
        btnSalvar = findViewById(R.id.btnSalvaInfoPeril);

        Controller_Usuario cu1 = new Controller_Usuario(getBaseContext());

        etNome.setText(cu1.getUser().getNome());
        etTel.setText(cu1.getUser().getTelefone());
        etEmail.setText(cu1.getUser().getEmail());

        codigo = cu1.getUser().getCod_Usuario();
        tipo = cu1.getUser().getTipo();
        senha = cu1.getUser().getSenha();

        /*Toast.makeText(AtualizarInfoPerfil.this,""+senha, Toast.LENGTH_LONG).show();
        Toast.makeText(AtualizarInfoPerfil.this,""+tipo, Toast.LENGTH_LONG).show();
        Toast.makeText(AtualizarInfoPerfil.this,""+codigo, Toast.LENGTH_LONG).show();
        */

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils u = new Utils();
                if(!u.isConected(getBaseContext()))
                {
                    Toast.makeText(AtualizarInfoPerfil.this,"Habilite a conexão com a internet", Toast.LENGTH_LONG).show();
                }else { // se tem acesso à internet chama logar
                    AtualizaInformacoes();
                }
            }
        });

        //menu novo
        Toolbar toolbar = findViewById(R.id.toolbar_att);
        setSupportActionBar(toolbar);
        drawerLayout= findViewById(R.id.drawer_layout_att);
        NavigationView navigationView = findViewById(R.id.nav_view_att);
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
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    public void AtualizaInformacoes(){
        UsuarioModel u = new UsuarioModel();
        u.setCod_Usuario(codigo);
        u.setTipo(tipo);
        u.setEmail(etEmail.getText().toString());
        u.setTelefone(etTel.getText().toString());
        u.setNome(etNome.getText().toString());
        u.setSenha(senha);


        if (etNome.getText().toString().isEmpty()){
            etNome.setError("Nome é obrigatório!");
        }else if (etTel.getText().toString().isEmpty()){
            etTel.setError("Telefone é obrigatório!");
        }else if (etTel.getText().toString().length() < 10){
            etTel.setError("Telefone informado não é válido");
        }else if (etEmail.getText().toString().isEmpty()){
            etEmail.setError("E-mail é obrigatório!");
        }else if (!isEmailValid(etEmail.getText().toString())){
            etEmail.setError("E-mail não é valido!");
        }else {
            Controller_Usuario cu1 = new Controller_Usuario(getBaseContext());
            cu1.updateUser(u);
            String url = "http://mip2.000webhostapp.com/attInfoPerfil.php?Nome=" + etNome.getText().toString() +
                    "&&Telefone=" + etNome.getText().toString() + "&&Email=" + etEmail.getText().toString() + "&&Cod_Usu=" + codigo;
            RequestQueue queue = Volley.newRequestQueue(this);

            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    // Parsing json
                    try {
                        JSONObject Obj = new JSONObject(response);
                        boolean confirmacao = Obj.getBoolean("confirmacao");

                        if (confirmacao) {
                            Toast.makeText(AtualizarInfoPerfil.this, "Atualização realizada com sucesso!", Toast.LENGTH_LONG).show();
                            final Intent k = new Intent(AtualizarInfoPerfil.this, Perfil.class);
                            startActivity(k);
                        } else {
                            Toast.makeText(AtualizarInfoPerfil.this, "E-mail já existe!", Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AtualizarInfoPerfil.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }));
        }
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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
}
