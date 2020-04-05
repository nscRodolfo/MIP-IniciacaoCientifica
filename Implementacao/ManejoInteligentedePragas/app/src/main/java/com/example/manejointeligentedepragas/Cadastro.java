package com.example.manejointeligentedepragas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.manejointeligentedepragas.Auxiliar.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cadastro extends AppCompatActivity {

    public Button btnCadastrar;

    public EditText tbNome;
    public EditText tbTelefone;
    public EditText tbEmail;
    public EditText tbConEmail;
    public EditText tbSenha;
    public EditText tbConSenha;
    public RadioGroup rgtipoUsu;

    public ProgressBar pbc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        setTitle("MIP² | Cadastro");

        pbc = findViewById(R.id.progressBarC);

        tbNome = findViewById(R.id.tbNome);
        tbTelefone = findViewById(R.id.tbTelefone);
        tbEmail = findViewById(R.id.tbEmailC);
        tbConEmail = findViewById(R.id.tbConEmail);
        tbSenha = findViewById(R.id.tbSenhaC);
        tbConSenha = findViewById(R.id.tbConSenha);

        rgtipoUsu = findViewById(R.id.rgTipousuario);

        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils u = new Utils();
                if(!u.isConected(getBaseContext()))
                {
                    Toast.makeText(Cadastro.this,"Habilite a conexão com a internet", Toast.LENGTH_LONG).show();
                }else { // se tem acesso à internet chama cadastrar

                    cadastrar();
                }
            }
        });
    }

    public void cadastrar()
    {
        final Intent k = new Intent(Cadastro.this, Entrar.class);

        String nome = tbNome.getText().toString();
        String telefone = tbTelefone.getText().toString();
        String email = tbEmail.getText().toString();
        String conEmail = tbConEmail.getText().toString();
        String senha = tbSenha.getText().toString();
        String conSenha = tbConSenha.getText().toString();
        String tipoUsuario = null;

        switch (rgtipoUsu.getCheckedRadioButtonId())
        {
            case R.id.rbFuncioario:
                tipoUsuario = "Funcionario";
                break;
            case R.id.rbProprietario:
                tipoUsuario = "Produtor";
                break;
        }
        if (tipoUsuario == null) {
            Toast.makeText(Cadastro.this,"É necessário escolher um tipo de usuário!", Toast.LENGTH_LONG).show();
        }else if (nome.isEmpty()){
            tbNome.setError("Nome é obrigatório!");
        }else if (telefone.isEmpty()){
            tbTelefone.setError("Telefone é obrigatório!");
        }else if (telefone.length() < 10){
            tbTelefone.setError("Telefone informado não é válido");
        }else if (email.isEmpty()){
            tbEmail.setError("E-mail é obrigatório!");
        }else if (!isEmailValid(email)){
            tbEmail.setError("E-mail não é valido!");
        }else if (conEmail.isEmpty()){
            tbConEmail.setError("Confirmar o e-mail é obrigatório!");
        }else if (!(email.equals(conEmail))){
            tbConEmail.setError("Os e-mails informados não são iguais!");
        }else if (senha.isEmpty()) {
            tbSenha.setError("Senha é obrigatório");
        }else if (senha.length() < 6){
            tbSenha.setError("A senha precisa ter mais que 6 digitos");
        }else if (conSenha.isEmpty()){
            tbConSenha.setError("Confirmar a senha é obrigatório!");
        }else if (!(senha.equals(conSenha))){
            tbConSenha.setError("As senhas informadas não são iguais!");
        }else{
            pbc.setVisibility(View.VISIBLE);
            senha = convertPassMd5(senha);
            String url = "http://mip2.000webhostapp.com/cadastrar.php?Nome="+nome+
                    "&&Telefone="+telefone+"&&Email="+email+"&&Senha="+senha+"&&tipoUsu="+tipoUsuario;
            RequestQueue queue = Volley.newRequestQueue(this);

            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    // Parsing json
                    try {
                        JSONObject Obj = new JSONObject(response);
                        boolean confirmacao = Obj.getBoolean("confirmacao");

                        if(confirmacao){
                            pbc.setVisibility(View.GONE);
                            Toast.makeText(Cadastro.this, "Cadastro Realizado com Sucesso!",Toast.LENGTH_LONG).show();
                            startActivity(k);
                        }else{
                            pbc.setVisibility(View.GONE);
                            Toast.makeText(Cadastro.this, "E-mail já cadastrado!",Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Cadastro.this,error.toString(), Toast.LENGTH_LONG).show();
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
