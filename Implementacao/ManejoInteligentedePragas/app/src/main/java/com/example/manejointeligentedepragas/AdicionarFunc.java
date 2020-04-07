package com.example.manejointeligentedepragas;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class AdicionarFunc extends AppCompatActivity {

    public Button btnAddFunc;
    Integer Cod_Propriedade;
    String nomePropriedade;
    ArrayList<String> emailsResgatados = new ArrayList<String>();
    EditText etEmail;
    EditText etSenha;

    private Dialog mDialog;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lateral, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.perfil:
                Intent i= new Intent(this, Perfil.class);
                startActivity(i);
                return true;
            case R.id.pragas:
                Intent k = new Intent(this, VisualizaPragas.class);
                startActivity(k);
                return true;
            case R.id.plantas:
                Intent j = new Intent(this, VisualizaPlantas.class);
                startActivity(j);
                return true;

            case R.id.metodo_de_controle:
                Intent l = new Intent(this, VisualizaMetodos.class);
                startActivity(l);
                return true;

            case R.id.sobre_o_mip:
                Intent p = new Intent(this, SobreMIP.class);
                startActivity(p);
                return  true;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_func);

        Cod_Propriedade = getIntent().getIntExtra("Cod_Propriedade", 0);
        emailsResgatados = getIntent().getStringArrayListExtra("emailsFuncionarios");
        nomePropriedade = getIntent().getStringExtra("nomePropriedade");
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
