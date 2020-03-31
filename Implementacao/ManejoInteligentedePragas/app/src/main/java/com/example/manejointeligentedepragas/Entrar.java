package com.example.manejointeligentedepragas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.security.MessageDigest;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.manejointeligentedepragas.Auxiliar.Utils;
import com.example.manejointeligentedepragas.Crontroller.Controller_Usuario;
import com.example.manejointeligentedepragas.model.UsuarioModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Entrar extends AppCompatActivity {

    public Button btnLogin;

    public EditText edt_login;
    public EditText edt_senha;
    public ProgressBar pb;

    String tipoUsu;

    Integer Cod_Propriedade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);

        edt_login = findViewById(R.id.tbEmail2);
        edt_senha = findViewById(R.id.tbSenha);

        btnLogin = findViewById(R.id.btnEntrarL);
        pb = findViewById(R.id.progressBar3);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils u = new Utils();
                if(!u.isConected(getBaseContext()))
                {
                    Toast.makeText(Entrar.this,"Habilite a conexão com a internet", Toast.LENGTH_LONG).show();
                }else { // se tem acesso à internet chama logar
                    pb.setVisibility(View.VISIBLE);
                    logar();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Entrar.this,MainActivity.class);
        startActivity(i);
    }

    private void logar()
    {
        final String login = edt_login.getText().toString().trim(); // trim- não passar espaço em branco
        String aux = edt_senha.getText().toString().trim();
        final String senha = convertPassMd5(aux);
        //Toast.makeText(Entrar.this,"MD5 da senha: "+senha, Toast.LENGTH_LONG).show();

        if (login.isEmpty()){
            edt_login.setError("E-mail é obrigatório!");
            pb.setVisibility(View.GONE);
        }else if (!isEmailValid(login)){
            edt_login.setError("E-mail não é valido!");
            pb.setVisibility(View.GONE);
        }else if (senha.isEmpty()){
            edt_senha.setError("Senha é obrigatório");
            pb.setVisibility(View.GONE);
        }else {
            //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();

            //endereçodeipv4  dinamico - pode alterar quando mudar de rede
            //String url = "https://192.168.0.20/AppMIP/Entrar.php?email="+login;

            String url = "http://mip2.000webhostapp.com/Entrar.php?email="+login;


            RequestQueue queue = Volley.newRequestQueue(this);
            final String finalSenha = senha;
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Parsing json
                    //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                    try {
                        //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                        JSONArray array = new JSONArray(response);
                        JSONObject obj = array.getJSONObject(0);
                        UsuarioModel u = new UsuarioModel();
                        u.setCod_Usuario(obj.getInt("Cod_Usuario"));
                        u.setEmail(obj.getString("Email"));
                        u.setSenha(obj.getString("Senha"));
                        u.setNome(obj.getString("Nome"));
                        u.setTelefone(obj.getString("Telefone"));
                        tipoUsu = obj.getString("Tipo");
                        if(u.getSenha().trim().equals(finalSenha)){

                            boolean c = new Controller_Usuario(getBaseContext()).addUsuario(u);
                            // adiciona no banco local o Usuario
                            Toast.makeText(Entrar.this, "Logado com Sucesso!",Toast.LENGTH_LONG).show();
                            pb.setVisibility(View.GONE);
                            if(tipoUsu.equals("Funcionario")){
                                Cod_Propriedade = obj.getInt("Cod_Propriedade");
                                final Intent k = new Intent(Entrar.this, Cultura.class);
                                k.putExtra("Cod_Propriedade", Cod_Propriedade);
                                k.putExtra("nomePropriedade", "Genesis");
                                startActivity(k);
                            }else if(tipoUsu.equals("Produtor")){
                                final Intent k = new Intent(Entrar.this, Propriedades.class);
                                startActivity(k);
                            }else if(tipoUsu.equals("Adm")){
                                Toast.makeText(Entrar.this, "ADÊMI",Toast.LENGTH_LONG).show();
                            }


                        }else{
                            Toast.makeText(Entrar.this, "Dados Invalidos!",Toast.LENGTH_LONG).show();
                            pb.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(Entrar.this, "Email Não Cadastrado",Toast.LENGTH_LONG).show();
                        pb.setVisibility(View.GONE);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Entrar.this,error.toString(), Toast.LENGTH_LONG).show();
                    pb.setVisibility(View.GONE);
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


}
