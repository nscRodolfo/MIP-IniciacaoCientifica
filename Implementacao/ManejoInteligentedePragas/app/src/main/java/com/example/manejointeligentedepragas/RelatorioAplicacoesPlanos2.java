package com.example.manejointeligentedepragas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.example.manejointeligentedepragas.Crontroller.Controller_Usuario;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RelatorioAplicacoesPlanos2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    int Cod_Propriedade;
    int codCultura;
    int codPraga;
    String nome;
    boolean aplicado;
    String nomePropriedade;
    String nomePraga;
    int Cod_Talhao;
    String NomeTalhao;

    ArrayList<Integer> popPragas = new ArrayList<Integer>();
    ArrayList<Integer> numPlantas = new ArrayList<Integer>();
    ArrayList<String> metodos = new ArrayList<String>();
    ArrayList<Date> dataContagem = new ArrayList<Date>();
    ArrayList<Date> dataContagemPlano = new ArrayList<Date>();
    ArrayList<Long> dataContagemLong = new ArrayList<Long>();

    private Dialog mDialog;

    private DrawerLayout drawerLayout;

    /*private XYPlot grafico;*/

    //data
    SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formataDataBR = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_aplicacoes_planos2);
        openDialog();

        Cod_Propriedade = getIntent().getIntExtra("Cod_Propriedade", 0);
        codCultura = getIntent().getIntExtra("Cod_Cultura", 0);
        nome = getIntent().getStringExtra("NomeCultura");
        aplicado = getIntent().getBooleanExtra("Aplicado", false);
        codPraga = getIntent().getIntExtra("Cod_Praga", 0);
        nomePropriedade = getIntent().getStringExtra("nomePropriedade");
        nomePraga = getIntent().getStringExtra("nomePraga");
        Cod_Talhao = getIntent().getIntExtra("Cod_Talhao", 0);
        NomeTalhao = getIntent().getStringExtra("NomeTalhao");

        //menu novo
        Toolbar toolbar = findViewById(R.id.toolbar_RAP);
        setSupportActionBar(toolbar);
        drawerLayout= findViewById(R.id.drawer_layout_RAP);
        NavigationView navigationView = findViewById(R.id.nav_view_RAP);
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

        setTitle("MIP² | "+nome+" x "+nomePraga);

        LineChart chart = findViewById(R.id.graphMP);


        resgataDados(Cod_Talhao,codPraga,chart);
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
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void popularDadosGráfico(LineChart chart){
        final List<Entry> entries = new ArrayList<Entry>();
        for(int i=0; i <dataContagem.size(); i++){
            entries.add(new Entry(dataContagem.get(i).getTime(), popPragas.get(i)));
        }

        LineDataSet dataSet = new LineDataSet(entries, "População de pragas");
        dataSet.setDrawFilled(true);
        dataSet.setDrawValues(false);
        dataSet.setColor(Color.parseColor("#659251"));
        dataSet.setFillColor(Color.parseColor("#659251"));
        dataSet.setCircleColor(Color.parseColor("#659251"));
        dataSet.setCircleHoleColor(Color.parseColor("#659251"));
        dataSet.setCircleRadius(6f);
        dataSet.setLineWidth(2f);
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.getDescription().setEnabled(false);

        chart.getAxisRight().setEnabled(false);

        chart.getLineData().setHighlightEnabled(true);

        chart.setExtraBottomOffset(40);

        chart.setExtraTopOffset(58);


        // Desativa o ZOOM do Touch
        chart.setDoubleTapToZoomEnabled(false);


        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                String aux = formataDataBR.format(value);
                return aux;
            }
        };

        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //Toast.makeText(RelatorioAplicacoesPlanos2.this, "R$ " + e.getY(),
                  //      Toast.LENGTH_SHORT).show();


                int indexInfos = entries.indexOf(e);


                String dataFormatadaAplicacao = formataDataBR.format(dataContagem.get(indexInfos));
                String dataFormatadaPlano = formataDataBR.format(dataContagemPlano.get(indexInfos));

                AlertDialog.Builder dlgBox = new AlertDialog.Builder(RelatorioAplicacoesPlanos2.this);
                dlgBox.setTitle("Informações:");
                dlgBox.setMessage("\nMétodo aplicado: "+metodos.get(indexInfos)+"\n\nCultura: "+nome+"\n\nTalhão: "+NomeTalhao+"\n\nData da aplicação: "+dataFormatadaAplicacao+"\n\nPlantas amostradas: "+numPlantas.get(indexInfos)+"\n\nPlantas infestadas: "+popPragas.get(indexInfos) + "\n\nData da última contagem: "+dataFormatadaPlano);
                dlgBox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dlgBox.show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        // Efeito de animação
        //chart.animateXY(3000, 3000);

        chart.invalidate();


        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(formatter);
        xAxis.setTextSize(11f);
        //xAxis.setGranularity(1f);
        //xAxis.setGranularityEnabled(true);
        //xAxis.setLabelCount(dataContagem.size());
        //xAxis.setYOffset(50);
        xAxis.setLabelRotationAngle(-45);
        mDialog.dismiss();
    }


    public void resgataDados(final int Cod_Talhao, final int codPraga, final LineChart graphA){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            mDialog.dismiss();
            Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet
            String url = "http://mip2.000webhostapp.com/resgataDadosGraphAplicacao.php?Cod_Talhao="+Cod_Talhao+"&&Cod_Praga="+codPraga;
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
                            try {
                                dataContagemPlano.add(formataData.parse(obj.getString("DataPlano")));
                                dataContagem.add(formataData.parse(obj.getString("DataAplicacao")));
                                dataContagemLong.add(formataData.parse(obj.getString("DataAplicacao")).getTime());
                            } catch (ParseException e) {
                                Toast.makeText(RelatorioAplicacoesPlanos2.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                            popPragas.add(obj.getInt("popPragas"));
                            numPlantas.add(obj.getInt("numPlantas"));
                            metodos.add(obj.getString("Metodo"));
                        }
                        if(dataContagem.size() == 0){
                            AlertDialog.Builder dlgBox = new AlertDialog.Builder(RelatorioAplicacoesPlanos2.this);
                            dlgBox.setCancelable(false);
                            dlgBox.setTitle("Aviso!");
                            dlgBox.setMessage("Você não realizou nenhum plano de amostragem para esta praga, deseja realizar um agora?");
                            dlgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(RelatorioAplicacoesPlanos2.this, PlanoDeAmostragem.class);
                                    i.putExtra("Cod_Propriedade", Cod_Propriedade);
                                    i.putExtra("Cod_Cultura", codCultura);
                                    i.putExtra("NomeCultura", nome);
                                    i.putExtra("nomePraga", nomePraga);
                                    i.putExtra("Cod_Praga", codPraga);
                                    i.putExtra("Aplicado", aplicado);
                                    i.putExtra("nomePropriedade", nomePropriedade);
                                    i.putExtra("Cod_Talhao", Cod_Talhao);
                                    i.putExtra("NomeTalhao", NomeTalhao);
                                    startActivity(i);
                                }
                            });
                            dlgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(RelatorioAplicacoesPlanos2.this, AcoesCultura.class);
                                    i.putExtra("Cod_Cultura", codCultura);
                                    i.putExtra("NomeCultura", nome);
                                    i.putExtra("Cod_Propriedade", Cod_Propriedade);
                                    i.putExtra("Aplicado", aplicado);
                                    i.putExtra("nomePropriedade", nomePropriedade);
                                    i.putExtra("Cod_Talhao", Cod_Talhao);
                                    i.putExtra("NomeTalhao", NomeTalhao);
                                    startActivity(i);
                                }
                            });
                            dlgBox.show();
                        }else{
                            popularDadosGráfico(graphA);
                        }

                    } catch (JSONException e) {
                        mDialog.dismiss();
                        Toast.makeText(RelatorioAplicacoesPlanos2.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mDialog.dismiss();
                    Toast.makeText(RelatorioAplicacoesPlanos2.this,error.toString(), Toast.LENGTH_LONG).show();
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
