package com.example.manejointeligentedepragas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.example.manejointeligentedepragas.model.PlanoAmostragemModel;
import com.example.manejointeligentedepragas.model.PragaModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class PlanoDeAmostragem extends AppCompatActivity {

    private static final String TAG = "PlanoDeAmostragem";

    int codPropriedade;
    int codPraga;
    int codCultura;
    String nome;
    String nomePraga;
    boolean aplicado;

    int plantasTalhao;
    int pontosTalhao;
    int plantaPonto;

    double nivelControle;

    TextView tvplantasTalhao;
    TextView tvpontosTalhao;
    TextView tvplantaPonto;
    TextView tvContagemTalhoes;
    TextView tvContagemPlantas;

    Button presencaPraga;
    Button ausenciaPraga;

    Button btnTalhaoAnterior;
    Button btnTalhaoSuperior;

    Button btnCorrigir;
    Boolean ultimoClick = false;

    ArrayList<Integer> codTalhoes = new ArrayList<Integer>();
    ArrayList<PlanoAmostragemModel> planos = new ArrayList<PlanoAmostragemModel>();

    //contadores
    int countInfestacao=0;
    int countPlantas=1;
    int countTalhao=1;

    //data
    SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
    Date data = new Date();
    String dataFormatada = formataData.format(data);

    //controla ou não controla
    Boolean controla = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plano_de_amostragem);

        codPropriedade = getIntent().getIntExtra("Cod_Propriedade", 0);
        codCultura = getIntent().getIntExtra("Cod_Cultura", 0);
        nome = getIntent().getStringExtra("NomeCultura");
        nomePraga = getIntent().getStringExtra("nomePraga");
        codPraga = getIntent().getIntExtra("Cod_Praga", 0);
        aplicado = getIntent().getBooleanExtra("Aplicado", false);

        tvplantasTalhao = findViewById(R.id.tvNPlantasPorTalhao);
        tvplantaPonto = findViewById(R.id.tvNPlantasPorPonto);
        tvpontosTalhao = findViewById(R.id.tvNPontosPorTalhao);

        tvContagemPlantas = findViewById(R.id.tvContagemPlantas);
        tvContagemTalhoes = findViewById(R.id.tvContagemTalhoes);

        presencaPraga = findViewById(R.id.btnPresencaPraga);
        ausenciaPraga = findViewById(R.id.btnAusenciaPraga);

        ResgatarAtinge(codPraga,codCultura);

        tvContagemPlantas.setText(String.valueOf(countPlantas));
        tvContagemTalhoes.setText(String.valueOf(countTalhao));

        btnTalhaoAnterior = findViewById(R.id.btnTalhaoAnterior);
        btnTalhaoSuperior = findViewById(R.id.btnTalhaoSuperior);
        btnCorrigir = findViewById(R.id.btnCorrige);

        btnTalhaoAnterior.setVisibility(View.INVISIBLE);
        btnCorrigir.setVisibility(View.INVISIBLE);




        ausenciaPraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ultimoClick = false; // saber qual click foi para a correção
                countPlantas++;
                tvContagemPlantas.setText(String.valueOf(countPlantas));
                checarTalhao();
            }
        });
        presencaPraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ultimoClick = true; // saber qual click foi para a correção
                countInfestacao++;
                countPlantas++;
                tvContagemPlantas.setText(String.valueOf(countPlantas));
                checarTalhao();
            }
        });

        btnTalhaoAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exibirCaixaDialogoAnterior();
            }
        });

        btnTalhaoSuperior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exibirCaixaDialogoProximo();
            }
        });

        btnCorrigir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countPlantas--;
                if(ultimoClick){
                    countInfestacao--;
                }
                tvContagemPlantas.setText(String.valueOf(countPlantas));
                btnCorrigir.setVisibility(View.INVISIBLE);
            }
        });

        btnCorrigir.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                exibirCaixaDialogoOnLongClickCorrigir();
                return true;
            }
        });

        //click listener do talhao anterior: diminuir count talhão, zerar count planta e apagar ultima posição do arraylist de planos
        //click listener do proximo talhao: aumentar count talhão, zerar count planta , ver se é o ultimo talhao,
        // se for o ultimo talhao +1 salvar no arraylist e passa pra proxima intent

        //mensagem em ambos botoes

        //if na função pra checar se o count de talhao é maior que o numero de talhoes da cultura, se for salva o arraylist de planos
        // no banco online

        // booleano no PlanoDeAmostragemModel ou na classe e mandar pra proxima intent para verificar se controla ou nao

        // tem public void onClick(View v) {} para detectar quando clica no botão


    }

    @Override
    public void onBackPressed() {
        exibirCaixaDialogoOnbackPressed();
    }


    public void ResgatarAtinge(int codPraga, final int codCultura){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet
            String url = "http://mip2.000webhostapp.com/resgatarAtinge.php?Cod_Cultura="+codCultura+"&&Cod_Praga="+codPraga;


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
                            JSONObject obj = array.getJSONObject(i);
                            plantasTalhao = obj.getInt("NumeroPlantasAmostradas");
                            pontosTalhao = obj.getInt("PontosPorTalhao");
                            plantaPonto = obj.getInt("PlantasPorPonto");
                            nivelControle = obj.getDouble("NivelDeControle");
                        }

                        tvplantasTalhao.setText(String.valueOf(plantasTalhao));
                        tvplantaPonto.setText(String.valueOf(plantaPonto));
                        tvpontosTalhao.setText(String.valueOf(pontosTalhao));
                        ResgatarTalhao(codCultura);

                    } catch (JSONException e) {
                        Toast.makeText(PlanoDeAmostragem.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(PlanoDeAmostragem.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }
    }

    public void ResgatarTalhao(int codCultura){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet
            String url = "http://mip2.000webhostapp.com/resgatarTalhao.php?Cod_Cultura="+codCultura;
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
                            JSONObject obj = array.getJSONObject(i);
                            codTalhoes.add(obj.getInt("Cod_Talhao"));
                        }
                        if(codTalhoes.size()==1) {
                            btnTalhaoSuperior.setText("Finalizar");
                        }
                    } catch (JSONException e) {
                        Toast.makeText(PlanoDeAmostragem.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(PlanoDeAmostragem.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }
    }

    public void checarTalhao(){
        btnCorrigir.setVisibility(View.VISIBLE);
        if(countPlantas == plantasTalhao+1){
            PlanoAmostragemModel pa = new PlanoAmostragemModel(dataFormatada,countInfestacao,countPlantas-1,codTalhoes.get(countTalhao-1),codPraga);
            planos.add(pa);
            countTalhao++;
            btnTalhaoAnterior.setVisibility(View.VISIBLE);
            btnCorrigir.setVisibility(View.INVISIBLE);
            if(countTalhao == codTalhoes.size()+1){
                SalvarPlanoAmostragem(planos);
                //salva plano de amostrgem no banco
                //e passa para a próxima intent
            }else if(countTalhao == codTalhoes.size()){
                btnTalhaoSuperior.setText("Finalizar");
                countPlantas = 1;
                countInfestacao = 0;
                tvContagemPlantas.setText(String.valueOf(countPlantas));
                tvContagemTalhoes.setText(String.valueOf(countTalhao));
            }else {
                countPlantas = 1;
                countInfestacao = 0;
                tvContagemPlantas.setText(String.valueOf(countPlantas));
                tvContagemTalhoes.setText(String.valueOf(countTalhao));
            }
        }
    }

    public void exibirCaixaDialogoProximo()
    {

        if(countPlantas == 1){
            AlertDialog.Builder dlgBox = new AlertDialog.Builder(this);
            dlgBox.setTitle("Aviso!");
            dlgBox.setMessage("Por favor, faça a amostragem de pelo menos uma planta.");
            dlgBox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //não faz nada
                }
            });

            dlgBox.show();
        }else {
            AlertDialog.Builder dlgBox = new AlertDialog.Builder(this);
            dlgBox.setTitle("Aviso!");
            dlgBox.setMessage("Você não verificou todas as plantas necessárias e isso afetará a precisão do plano de amostragem, deseja avançar?");
            dlgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PlanoAmostragemModel pa = new PlanoAmostragemModel(dataFormatada, countInfestacao, countPlantas - 1, codTalhoes.get(countTalhao - 1), codPraga);
                    planos.add(pa);
                    countTalhao++;
                    btnTalhaoAnterior.setVisibility(View.VISIBLE);
                    btnCorrigir.setVisibility(View.INVISIBLE);

                    if (countTalhao == codTalhoes.size()) {
                        btnTalhaoSuperior.setText("Finalizar");
                        countPlantas = 1;
                        countInfestacao = 0;
                        tvContagemPlantas.setText(String.valueOf(countPlantas));
                        tvContagemTalhoes.setText(String.valueOf(countTalhao));
                    } else if (btnTalhaoSuperior.getText() == "Finalizar") {
                        //salva plano de amostrgem no banco
                        //e passa para a próxima intent
                        SalvarPlanoAmostragem(planos);
                    } else {
                        countPlantas = 1;
                        countInfestacao = 0;
                        tvContagemPlantas.setText(String.valueOf(countPlantas));
                        tvContagemTalhoes.setText(String.valueOf(countTalhao));
                    }

                    //checarTalhao();
                }
            });

            dlgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // não faz nada
                }
            });

            dlgBox.show();
        }
    }

    public void exibirCaixaDialogoAnterior()
    {
        AlertDialog.Builder dlgBox = new AlertDialog.Builder(this);
        dlgBox.setTitle("Aviso!");
        dlgBox.setMessage("Deseja refazer o plano de amostragem do talhão anterior?");
        dlgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                btnCorrigir.setVisibility(View.INVISIBLE);
                if(countTalhao != 1) {
                    planos.remove(countTalhao - 2);
                    countTalhao--;
                    btnTalhaoSuperior.setText("Próximo talhão");
                    if(countTalhao == 1){
                        btnTalhaoAnterior.setVisibility(View.INVISIBLE);
                    }
                    countPlantas = 1;
                    countInfestacao = 0;
                    tvContagemPlantas.setText(String.valueOf(countPlantas));
                    tvContagemTalhoes.setText(String.valueOf(countTalhao));
                }
            }
        });

        dlgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // não faz nada
            }
        });

        dlgBox.show();

    }

    public void exibirCaixaDialogoOnbackPressed()
    {
        AlertDialog.Builder dlgBox = new AlertDialog.Builder(this);
        dlgBox.setTitle("ATENÇÃO!");
        dlgBox.setMessage("Deseja cancelar o plano de amostragem?");
        dlgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(PlanoDeAmostragem.this, AcoesCultura.class);
                i.putExtra("Cod_Propriedade", codPropriedade);
                i.putExtra("Cod_Cultura", codCultura);
                i.putExtra("NomeCultura", nome);
                i.putExtra("Aplicado", aplicado);
                startActivity(i);
            }
        });

        dlgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // não faz nada
            }
        });

        dlgBox.show();

    }

    public void exibirCaixaDialogoOnLongClickCorrigir()
    {
        AlertDialog.Builder dlgBox = new AlertDialog.Builder(this);
        dlgBox.setTitle("Aviso!");
        dlgBox.setMessage("Deseja refazer a contagem deste talhão?");
        dlgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                countPlantas = 1;
                countInfestacao =0;
                tvContagemPlantas.setText(String.valueOf(countPlantas));
                btnCorrigir.setVisibility(View.INVISIBLE);
            }
        });

        dlgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // não faz nada
            }
        });

        dlgBox.show();

    }

    public void SalvarPlanoAmostragem(ArrayList<PlanoAmostragemModel> planos){
        //verifica todos os talhões, se qualquer um tiver o nível de controle atingido, é necessário o controle em toda cultura
        for(int j=0; j<planos.size();j++){
            // cálculo da regra de 3 para conferir o nível de controle
            double porcentagemInfestadas = ((planos.get(j).getPlantasInfestadas()*100)/ planos.get(j).getPlantasAmostradas())/100;
            if(porcentagemInfestadas >= nivelControle){
                controla = true;
                break;
            }
        }
        for(int i=0; i< planos.size(); i++){
            Utils u = new Utils();
            if(!u.isConected(getBaseContext()))
            {
                Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
            }else { // se tem acesso à internet
                String url = "http://mip2.000webhostapp.com/salvaPlanoAmostragem.php?Cod_Talhao=" + planos.get(i).getFk_Cod_Talhao()
                        +"&&Data="+dataFormatada
                        +"&&PlantasInfestadas="+planos.get(i).getPlantasInfestadas()
                        +"&&PlantasAmostradas="+planos.get(i).getPlantasAmostradas()
                        +"&&Cod_Praga="+codPraga;

                RequestQueue queue = Volley.newRequestQueue(this);
                queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        //Parsing json
                        //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                        try {

                            JSONObject obj1 = new JSONObject(response);
                            boolean confirmacao = obj1.getBoolean("confirmacao");
                            if(confirmacao){
                                Intent i = new Intent(PlanoDeAmostragem.this, ConfirmacaoPlanoAmostragem.class);
                                i.putExtra("Cod_Propriedade", codPropriedade);
                                i.putExtra("Cod_Cultura", codCultura);
                                i.putExtra("NomeCultura", nome);
                                i.putExtra("NomePraga",nomePraga);
                                i.putExtra("Cod_Praga",codPraga);
                                i.putExtra("Controla",controla);
                                i.putExtra("Aplicado", aplicado);
                                startActivity(i);
                            }else{
                                Toast.makeText(PlanoDeAmostragem.this, "Plano de amostragem não cadastrado! Tente novamente",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(PlanoDeAmostragem.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PlanoDeAmostragem.this,error.toString(), Toast.LENGTH_LONG).show();
                    }
                }));

            }
        }
    }

}
