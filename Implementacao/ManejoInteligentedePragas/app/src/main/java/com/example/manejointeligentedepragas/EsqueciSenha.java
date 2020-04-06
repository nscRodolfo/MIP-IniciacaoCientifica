package com.example.manejointeligentedepragas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
                RecuperaSenha(email);
            }
        });
    }

    public void RecuperaSenha(String email){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
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
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i< array.length(); i++){

                        }
                    } catch (JSONException e) {
                        Toast.makeText(EsqueciSenha.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(EsqueciSenha.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }


    }

}
