package com.example.manejointeligentedepragas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.manejointeligentedepragas.Auxiliar.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConfirmacaoPlanoAmostragem extends AppCompatActivity {
    int codPropriedade;
    int codPraga;
    int codCultura;
    String nome;
    String nomePraga;
    boolean controla;
    boolean aplicado;

    TextView tvMostraCultura;
    TextView tvMostraPraga;
    TextView tvMostraControla;

    Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacao_plano_amostragem);

        codPropriedade = getIntent().getIntExtra("Cod_Propriedade", 0);
        codCultura = getIntent().getIntExtra("Cod_Cultura", 0);
        nome = getIntent().getStringExtra("NomeCultura");
        nomePraga = getIntent().getStringExtra("NomePraga");
        codPraga = getIntent().getIntExtra("Cod_Praga", 0);
        controla = getIntent().getBooleanExtra("Controla",false);
        aplicado = getIntent().getBooleanExtra("Aplicado", false);

        tvMostraCultura = findViewById(R.id.tvConfMostraCultura);
        tvMostraPraga = findViewById(R.id.tvConfMostraPraga);
        tvMostraControla = findViewById(R.id.tvConfMostraControle);
        btnSalvar = findViewById(R.id.btnConfSalvaMetodo);

        tvMostraCultura.setText(nome);
        tvMostraPraga.setText(nomePraga);

        if (controla){
            if(aplicado){
                tvMostraControla.setText("Método de controle aplicado recentemente. É necessário esperar o tempo de fitossanidade para realizar uma nova aplicação");
                btnSalvar.setText("OK");
            }else{
                tvMostraControla.setText("É necessário o controle.");
                btnSalvar.setText("Selecionar método");
                alteraStatus(codCultura, codPraga, 2); //2 = vermelho precisa de controle
            }
        }else{
            tvMostraControla.setText("Não é necessário realizar nenhum tipo de controle");
            btnSalvar.setText("OK");
            //altera status praga
            alteraStatus(codCultura, codPraga, 0); //0 = não precisa de controle
        }


        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(controla){
                    if(aplicado){
                        Intent i = new Intent(ConfirmacaoPlanoAmostragem.this, Pragas.class);
                        i.putExtra("Cod_Propriedade", codPropriedade);
                        i.putExtra("Cod_Cultura", codCultura);
                        i.putExtra("NomeCultura", nome);
                        i.putExtra("Aplicado", aplicado);
                        startActivity(i);
                    }else{
                        Intent i = new Intent(ConfirmacaoPlanoAmostragem.this, AplicaMetodoDeControle.class);
                        i.putExtra("Cod_Propriedade", codPropriedade);
                        i.putExtra("Cod_Cultura", codCultura);
                        i.putExtra("NomeCultura", nome);
                        i.putExtra("Cod_Praga",codPraga);
                        i.putExtra("Aplicado", aplicado);
                        startActivity(i);
                    }
                }else{
                    Intent i = new Intent(ConfirmacaoPlanoAmostragem.this, Pragas.class);
                    i.putExtra("Cod_Propriedade", codPropriedade);
                    i.putExtra("Cod_Cultura", codCultura);
                    i.putExtra("NomeCultura", nome);
                    i.putExtra("Aplicado", aplicado);
                    startActivity(i);
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ConfirmacaoPlanoAmostragem.this, AcoesCultura.class);
        i.putExtra("Cod_Propriedade", codPropriedade);
        i.putExtra("Cod_Cultura", codCultura);
        i.putExtra("NomeCultura", nome);
        i.putExtra("Aplicado", aplicado);
        startActivity(i);
    }



    public void alteraStatus(int codCultura, int codPraga, int status){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet
            String url = "http://mip2.000webhostapp.com/alteraStatus.php?Cod_Praga=" + codPraga + "&&Cod_Cultura="+ codCultura + "&&Status=" + status;
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ConfirmacaoPlanoAmostragem.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));
        }
    }
}
