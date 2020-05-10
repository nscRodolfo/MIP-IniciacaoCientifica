package com.example.manejointeligentedepragas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.manejointeligentedepragas.Auxiliar.Utils;
import com.example.manejointeligentedepragas.model.PropriedadeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EsqueciSenha extends AppCompatActivity {

    EditText edtRecupera;
    Button Recupera;

    String email;
    public Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueci_senha);

        edtRecupera = findViewById(R.id.edtEmailRecupera);
        Recupera = findViewById(R.id.btnRequisicao);

        Recupera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edtRecupera.getText().toString();
                openDialog();
                RecuperaSenha(email);
            }
        });
    }

    public void RecuperaSenha(String email){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            mDialog.dismiss();
            Toast.makeText(this,"Habilite a conexão com a internet", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet

            String url = "http://mip2.000webhostapp.com/EsqueciSenha.php?Email=" + email;
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
                            AlertDialog.Builder dlgBox = new AlertDialog.Builder(EsqueciSenha.this);
                            dlgBox.setTitle("Alteração de senha");
                            dlgBox.setMessage("Sua senha foi alterada, verifique seu e-mail (confira na caixa de spams)! Caso não tenha recebido seu e-mail, tente novamente.");
                            dlgBox.setCancelable(false);
                            dlgBox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent k = new Intent(EsqueciSenha.this, Entrar.class);
                                    startActivity(k);
                                }
                            });
                            dlgBox.show();
                        }else{
                            mDialog.dismiss();
                            AlertDialog.Builder dlgBox = new AlertDialog.Builder(EsqueciSenha.this);
                            dlgBox.setTitle("Alteração de senha");
                            dlgBox.setMessage("Sua senha não foi alterada, tente novamente mais tarde!");
                            dlgBox.setCancelable(false);
                            dlgBox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent k = new Intent(EsqueciSenha.this, Entrar.class);
                                    startActivity(k);
                                }
                            });
                            dlgBox.show();
                        }
                        mDialog.dismiss();
                    } catch (JSONException e) {
                        mDialog.dismiss();
                        Toast.makeText(EsqueciSenha.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mDialog.dismiss();
                    Toast.makeText(EsqueciSenha.this,error.toString(), Toast.LENGTH_LONG).show();
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
