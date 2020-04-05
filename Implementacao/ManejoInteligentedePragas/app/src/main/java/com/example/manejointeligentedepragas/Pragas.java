package com.example.manejointeligentedepragas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.manejointeligentedepragas.Auxiliar.Utils;
import com.example.manejointeligentedepragas.RecyclerViewAdapter.CulturaCardAdapter;
import com.example.manejointeligentedepragas.RecyclerViewAdapter.PragaCardAdapter;
import com.example.manejointeligentedepragas.model.CulturaModel;
import com.example.manejointeligentedepragas.model.PragaModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Pragas extends AppCompatActivity {

    public FloatingActionButton fabAddPraga;
    public TextView tvAddPraga;
    private ArrayList<PragaModel> cards = new ArrayList<>();
    int codCultura;
    int Cod_Propriedade;
    String nomePropriedade;
    boolean aplicado;
    String nome;
    ArrayList<String> pragasAdd = new ArrayList<String>();
    private Dialog mDialog;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_praga, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icInfo:
                ExibirCaixaInfo();
                return true;
            case R.id.perfil_praga:
                Intent i = new Intent(this, Perfil.class);
                startActivity(i);
                return true;
            case R.id.pragas_praga:
                Intent k = new Intent(this, VisualizaPragas.class);
                startActivity(k);
                return true;
            case R.id.plantas_praga:
                Intent j = new Intent(this, VisualizaPlantas.class);
                startActivity(j);
                return true;

            case R.id.metodo_de_controle_praga:
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
        setContentView(R.layout.activity_pragas);

        openDialog();

        aplicado = getIntent().getBooleanExtra("Aplicado", false);
        codCultura = getIntent().getIntExtra("Cod_Cultura", 0);
        nome = getIntent().getStringExtra("NomeCultura");
        Cod_Propriedade = getIntent().getIntExtra("Cod_Propriedade", 0);
        nomePropriedade = getIntent().getStringExtra("nomePropriedade");

        setTitle("MIP² | "+nome);

        resgatarDados();

        fabAddPraga = findViewById(R.id.fabAddPraga);
        fabAddPraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Pragas.this, AdicionarPraga.class);
                i.putExtra("Cod_Cultura", codCultura);
                i.putExtra("NomeCultura", nome);
                i.putExtra("Cod_Propriedade", Cod_Propriedade);
                i.putExtra("pragasAdd", pragasAdd);
                i.putExtra("Aplicado", aplicado);
                i.putExtra("nomePropriedade", nomePropriedade);
                startActivity(i);
            }
        });

        tvAddPraga = findViewById(R.id.tvAddPraga);
        tvAddPraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Pragas.this, AdicionarPraga.class);
                i.putExtra("Cod_Cultura", codCultura);
                i.putExtra("NomeCultura", nome);
                i.putExtra("Cod_Propriedade", Cod_Propriedade);
                i.putExtra("pragasAdd", pragasAdd);
                i.putExtra("Aplicado", aplicado);
                i.putExtra("nomePropriedade", nomePropriedade);
                startActivity(i);
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Pragas.this, AcoesCultura.class);
        i.putExtra("Cod_Cultura", codCultura);
        i.putExtra("NomeCultura", nome);
        i.putExtra("Cod_Propriedade", Cod_Propriedade);
        i.putExtra("Aplicado", aplicado);
        i.putExtra("nomePropriedade", nomePropriedade);
        startActivity(i);
    }


    private void iniciarRecyclerView() {
        RecyclerView rv = findViewById(R.id.RVPraga);
        PragaCardAdapter adapter = new PragaCardAdapter(this, cards, codCultura, nome, Cod_Propriedade, aplicado, nomePropriedade);

        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

    }

    private void resgatarDados() {
        //Log.d(TAG, "resgatarDados: resgatou");

        Utils u = new Utils();
        if (!u.isConected(getBaseContext())) {
            Toast.makeText(this, "Habilite a conexão com a internet", Toast.LENGTH_LONG).show();
            mDialog.dismiss();
        } else { // se tem acesso à internet

            String url = "http://mip2.000webhostapp.com/resgatarPragas.php?Cod_Cultura=" + codCultura;

            RequestQueue queue = Volley.newRequestQueue(Pragas.this);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Parsing json
                    //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            PragaModel u = new PragaModel();
                            u.setCod_Praga(obj.getInt("Cod_Praga"));
                            u.setNome(obj.getString("Nome"));
                            u.setStatus(obj.getInt("Status"));
                            cards.add(u);
                            pragasAdd.add(obj.getString("Nome"));
                        }
                        iniciarRecyclerView();
                        mDialog.dismiss();
                    } catch (JSONException e) {
                        Toast.makeText(Pragas.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Pragas.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }

    }

    public  void ExibirCaixaInfo(){
        AlertDialog.Builder dlgBox = new AlertDialog.Builder(Pragas.this);
        SpannableString ss =
                new SpannableString("As cores nessa tela indicam diferentes situações:\n\n" +
                "Verde: indica que a praga encontra-se controlada no momento (abaixo do nível de controle)." +
                "\n\nAmarelo: indica que é necessário realizar uma amostragem sobre a praga." +
                "\n\nVermelho: indica que, após uma contagem, foi constatada a necessidade de aplicação de algum método de controle.");
        ForegroundColorSpan foregroundVerde = new ForegroundColorSpan(Color.parseColor("#659251"));
        ForegroundColorSpan foregroundAmarelo = new ForegroundColorSpan(Color.parseColor("#ECC911"));
        ForegroundColorSpan foregroundVermelho = new ForegroundColorSpan(Color.parseColor("#FD991111"));
        ss.setSpan(foregroundVerde, 51,58, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(foregroundAmarelo, 143,151, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(foregroundVermelho, 215,225, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        StyleSpan negrito = new StyleSpan(Typeface.BOLD);
        StyleSpan negrito2 = new StyleSpan(Typeface.BOLD);
        StyleSpan negrito3 = new StyleSpan(Typeface.BOLD);
        ss.setSpan(negrito3, 51,58, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(negrito2, 143,151, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(negrito, 215,225, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        dlgBox.setTitle("Informações");
        dlgBox.setMessage(ss);

        /*dlgBox.setMessage("As cores nessa tela indicam diferentes situações:\n\n" +
                          "Verde: indica que a praga encontra-se controlada no momento(abaixo do nível de controle)." +
                          "\n\nAmarelo: indica que é necessário realizar uma amostragem sobre a praga." +
                          "\n\nVermelho: indica que, após uma contagem, foi constatada a necessidade de aplicação de algum método de controle.");
        */dlgBox.setPositiveButton("Entendi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dlgBox.show();
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
