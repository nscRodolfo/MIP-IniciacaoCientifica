package com.example.manejointeligentedepragas;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.example.manejointeligentedepragas.Auxiliar.Utils;
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
import java.util.Calendar;
import java.util.Date;

public class RelatorioPragaPlano extends AppCompatActivity {

    int Cod_Propriedade;
    int codCultura;
    int codPraga;
    String nome;
    boolean aplicado;
    String nomePropriedade;

    ArrayList<Integer> popPragas = new ArrayList<Integer>();
    ArrayList<Integer> numPlantas = new ArrayList<Integer>();
    ArrayList<Date> dataContagem = new ArrayList<Date>();

    /*private XYPlot grafico;*/

    //data
    SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_praga_plano);

        Cod_Propriedade = getIntent().getIntExtra("Cod_Propriedade", 0);
        codCultura = getIntent().getIntExtra("Cod_Cultura", 0);
        nome = getIntent().getStringExtra("NomeCultura");
        aplicado = getIntent().getBooleanExtra("Aplicado", false);
        codPraga = getIntent().getIntExtra("Cod_Praga", 0);
        nomePropriedade = getIntent().getStringExtra("nomePropriedade");

        setTitle("MIP² | "+nome);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        //graph.getViewport().setScalable(true);
        //graph.getViewport().setScrollable(true);
        //graph.getViewport().setScrollableY(true);
        graph.setTitle("Pragas x Contagens");
        graph.setTitleColor(Color.parseColor("#659251"));



        popularDadosGráfico(graph);

        /*grafico = (XYPlot) findViewById(R.id.graficoPragaPlano);
        popularDadosGráfico();
        */
    }


    public void popularDadosGráfico(GraphView graph){
        /*XYSeries s1 = new SimpleXYSeries(SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Contagens", 1,5,2,8,3,9);

        grafico.addSeries(s1, new LineAndPointFormatter(Color.BLACK, Color.GREEN, null, null));
        */

        resgataDados(codCultura,codPraga);

        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d4 = calendar.getTime();

        int tamanho = dataContagem.size();
        DataPoint[] dataPoints = new DataPoint[tamanho];

        for (int i =0;i<tamanho;i++){
            dataPoints[i] = new DataPoint(dataContagem.get(i),popPragas.get(i));
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);

        //for (int i =0;i<tamanho;i++){
          //  series.appendData(new DataPoint(dataContagem.get(i),popPragas.get(i)), true, tamanho);
        //}

        series.setColor(Color.parseColor("#659251"));
        graph.addSeries(series);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(RelatorioPragaPlano.this));
        //graph.getGridLabelRenderer().setNumHorizontalLabels(4);
        //graph.getViewport().setMinX(dataContagem.get(0).getTime());
        //graph.getViewport().setMaxX(dataContagem.get(tamanho-1).getTime());
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getGridLabelRenderer().setHumanRounding(false);
        /*
        PointsGraphSeries<DataPoint> series1 = new PointsGraphSeries<>(new DataPoint[]{
                new DataPoint(d1, 1),
                new DataPoint(d2, 10),
                new DataPoint(d3, 7),
                new DataPoint(d4, 9)
        });
        series1.setColor(Color.parseColor("#659251"));
        graph.addSeries(series1);


        series1.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(RelatorioPragaPlano.this, "Series1: On Data Point clicked: "+dataPoint, Toast.LENGTH_SHORT).show();
            }
        });
        */
    }


    public void resgataDados(int codCultura,int codPraga){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet
            String url = "http://mip2.000webhostapp.com/resgataDadosGraphPlantasPlanos.php?Cod_Cultura="+codCultura+"&&Cod_Praga="+codPraga;
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
                                dataContagem.add(formataData.parse(obj.getString("Data")));
                            } catch (ParseException e) {
                                Toast.makeText(RelatorioPragaPlano.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                            popPragas.add(obj.getInt("popPragas"));
                            numPlantas.add(obj.getInt("numPlantas"));
                        }
                    } catch (JSONException e) {
                        Toast.makeText(RelatorioPragaPlano.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RelatorioPragaPlano.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }
    }
}
