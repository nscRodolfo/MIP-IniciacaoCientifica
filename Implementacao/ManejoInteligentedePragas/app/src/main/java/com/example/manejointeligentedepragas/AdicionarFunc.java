package com.example.manejointeligentedepragas;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.manejointeligentedepragas.Crontroller.Controller_Usuario;
import com.example.manejointeligentedepragas.model.UsuarioModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdicionarFunc extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public Button btnAddFunc;
    Integer Cod_Propriedade;
    String nomePropriedade;
    ArrayList<String> emailsResgatados = new ArrayList<String>();
    EditText etEmail;
    EditText etSenha;

    private Dialog mDialog;

    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_func);

        Cod_Propriedade = getIntent().getIntExtra("Cod_Propriedade", 0);
        emailsResgatados = getIntent().getStringArrayListExtra("emailsFuncionarios");
        nomePropriedade = getIntent().getStringExtra("nomePropriedade");


        //menu novo
        Toolbar toolbar = findViewById(R.id.toolbar_add_func);
        setSupportActionBar(toolbar);
        drawerLayout= findViewById(R.id.drawer_layout_add_func);
        NavigationView navigationView = findViewById(R.id.nav_view_add_func);
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

        etEmail = findViewById(R.id.etEmailAddFunc);
        etSenha = findViewById(R.id.etSenhaAddFunc);

        btnAddFunc = findViewById(R.id.btnAdicionarFunc);

        btnAddFunc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
                adicionarFuncionario();
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

                break;

            case R.id.drawerSobre:
                Intent pp = new Intent(this, SobreMIP.class);
                startActivity(pp);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }




    public void adicionarFuncionario(){

        final Intent k = new Intent(AdicionarFunc.this, VisualizaFuncionario.class);

        String email = etEmail.getText().toString();
        String senha = etSenha.getText().toString();

        if(email.isEmpty()){
            etEmail.setError("E-mail é obrigatório!");
            mDialog.dismiss();
        }else if (!isEmailValid(email)){
            etEmail.setError("E-mail não é valido!");
            mDialog.dismiss();
        }else if(senha.isEmpty()){
            etSenha.setError("Senha é obrigatório!");
            mDialog.dismiss();
        }else if (senha.length() < 6){
            etSenha.setError("A senha precisa ter mais que 6 digitos");
            mDialog.dismiss();
        }else if(emailsResgatados.contains(email)){
            etEmail.setError("Este funcionário já está vinculado à propriedade");
            mDialog.dismiss();
        }else {
            senha = convertPassMd5(senha);

            String url = "http://mip2.000webhostapp.com/adicionarFuncionario.php?Cod_Propriedade="+Cod_Propriedade+
                         "&&Email="+email+"&&Senha="+senha;

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Parsing json
                    //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                    try {
                        //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();

                        JSONObject Obj = new JSONObject(response);
                        boolean confirmacao = Obj.getBoolean("confirmacao");

                        if(confirmacao){
                            mDialog.dismiss();
                            Toast.makeText(AdicionarFunc.this, "Funcionário vinculado!",Toast.LENGTH_LONG).show();
                            k.putExtra("Cod_Propriedade", Cod_Propriedade);
                            k.putExtra("nomePropriedade", nomePropriedade);
                            startActivity(k);
                        }else{
                            mDialog.dismiss();
                            Toast.makeText(AdicionarFunc.this, "Funcionário não encontrado.",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(AdicionarFunc.this, e.toString(), Toast.LENGTH_LONG).show();
                        mDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AdicionarFunc.this, error.toString(), Toast.LENGTH_LONG).show();
                    mDialog.dismiss();
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
    // converte String para hash MD5
    public static String convertPassMd5(String pass) {
        String password = null;
        MessageDigest mdEnc;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(pass.getBytes(), 0, pass.length());
            pass = new BigInteger(1, mdEnc.digest()).toString(16);
            while (pass.length() < 32) {
                pass = "0" + pass;
            }
            password = pass;
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return password;
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
