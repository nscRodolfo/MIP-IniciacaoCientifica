package com.example.manejointeligentedepragas;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.manejointeligentedepragas.Auxiliar.Utils;
import com.example.manejointeligentedepragas.RecyclerViewAdapter.CulturaCardAdapter;
import com.example.manejointeligentedepragas.RecyclerViewAdapter.PlanosRealizadosAdapter;
import com.example.manejointeligentedepragas.model.CulturaModel;
import com.example.manejointeligentedepragas.model.PlanoAmostragemModel;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jjoe64.graphview.GraphView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RelatorioPlanosRealizados extends AppCompatActivity {

    int Cod_Propriedade;
    int codCultura;
    int codPraga;
    String nome;
    boolean aplicado;
    String nomePropriedade;
    String nomePraga;

    private ArrayList<PlanoAmostragemModel> planos = new ArrayList<>();

    private Dialog mDialog;

    //usados para itext

    final private int REQUEST_CODE_ASK_PERMISSIONS =111;
    private File pdfFile;

    SimpleDateFormat formataDataBR = new SimpleDateFormat("dd-MM-yyyy");
    Date data = new Date();


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_gerar_relatorio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icGeraRelatorio:
                GerarRelatorio();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_planos_realizados);

        openDialog();

        Cod_Propriedade = getIntent().getIntExtra("Cod_Propriedade", 0);
        codCultura = getIntent().getIntExtra("Cod_Cultura", 0);
        nome = getIntent().getStringExtra("NomeCultura");
        aplicado = getIntent().getBooleanExtra("Aplicado", false);
        codPraga = getIntent().getIntExtra("Cod_Praga", 0);
        nomePropriedade = getIntent().getStringExtra("nomePropriedade");
        nomePraga = getIntent().getStringExtra("nomePraga");

        setTitle("MIP² | " + nomePraga);

        resgataDados(codCultura, codPraga);
    }

    public void resgataDados(final int codCultura, final int codPraga){
        Utils u = new Utils();
        if(!u.isConected(getBaseContext()))
        {
            Toast.makeText(this,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
            mDialog.dismiss();
        }else { // se tem acesso à internet
            String url = "http://mip2.000webhostapp.com/resgataDadosGraphPlantasPlanos.php?Cod_Cultura="+codCultura+"&&Cod_Praga="+codPraga;
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Parsing json
                    //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                    try {
                        String Autor;
                        //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i< array.length(); i++){
                            JSONObject obj = array.getJSONObject(i);
                            PlanoAmostragemModel pa = new PlanoAmostragemModel();
                            pa.setDate(obj.getString("Data"));
                            pa.setPlantasAmostradas(obj.getInt("numPlantas"));
                            pa.setPlantasInfestadas(obj.getInt("popPragas"));
                            pa.setAutor(obj.getString("Autor"));
                            planos.add(pa);
                        }if(planos.isEmpty()){
                            AlertDialog.Builder dlgBox = new AlertDialog.Builder(RelatorioPlanosRealizados.this);
                            dlgBox.setCancelable(false);
                            dlgBox.setTitle("Aviso!");
                            dlgBox.setMessage("Você não realizou nenhum plano de amostragem para esta praga, deseja realizar um agora?");
                            dlgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(RelatorioPlanosRealizados.this, PlanoDeAmostragem.class);
                                    i.putExtra("Cod_Propriedade", Cod_Propriedade);
                                    i.putExtra("Cod_Cultura", codCultura);
                                    i.putExtra("NomeCultura", nome);
                                    i.putExtra("nomePraga", nomePraga);
                                    i.putExtra("Cod_Praga", codPraga);
                                    i.putExtra("Aplicado", aplicado);
                                    i.putExtra("nomePropriedade", nomePropriedade);
                                    startActivity(i);
                                }
                            });
                            dlgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(RelatorioPlanosRealizados.this, AcoesCultura.class);
                                    i.putExtra("Cod_Cultura", codCultura);
                                    i.putExtra("NomeCultura", nome);
                                    i.putExtra("Cod_Propriedade", Cod_Propriedade);
                                    i.putExtra("Aplicado", aplicado);
                                    i.putExtra("nomePropriedade", nomePropriedade);
                                    startActivity(i);
                                }
                            });
                            dlgBox.show();
                        }else{
                            iniciarRecyclerView();
                        }
                        mDialog.dismiss();

                    } catch (JSONException e) {
                        mDialog.dismiss();
                        Toast.makeText(RelatorioPlanosRealizados.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mDialog.dismiss();
                    Toast.makeText(RelatorioPlanosRealizados.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));

        }
    }

    private void iniciarRecyclerView(){
        RecyclerView rv = findViewById(R.id.rvPlanosRealizados);
        PlanosRealizadosAdapter adapter = new PlanosRealizadosAdapter(this, planos, 1,codCultura);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
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

    public void GerarRelatorio(){
        //Toast.makeText(RelatorioAplicacoesRealizadas.this, "click", Toast.LENGTH_LONG).show();
        try{
            createPdfWrapper();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (DocumentException e){
            e.printStackTrace();
        }
    }

    private void createPdfWrapper() throws  FileNotFoundException, DocumentException{

        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(RelatorioPlanosRealizados.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED){

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("Você precisa conceder permissão para armazenamento. Deseja fazer isso?",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }else{
            createPdf();
        }


    }

    private void showMessageOKCancel(String Message, DialogInterface.OnClickListener okListener){
        new AlertDialog.Builder(RelatorioPlanosRealizados.this).setMessage(Message)
                .setPositiveButton("Sim", okListener)
                .setNegativeButton("Cancelar", null)
                .create().show();
    }

    private void createPdf() throws FileNotFoundException, DocumentException {
        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/mip/Relatórios de planos de amostragem");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
        }

        String pdfname = nomePropriedade+", "+nome+", "+nomePraga+", data: "+formataDataBR.format(data)+".pdf";
        pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document(PageSize.A4);
        PdfPTable table = new PdfPTable(new float[]{3, 3, 3, 3});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setFixedHeight(50);
        table.setTotalWidth(PageSize.A4.getWidth());
        table.setWidthPercentage(100);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell("Autor");
        table.addCell("Data da do plano de amostragem");
        table.addCell("Plantas infestadas");
        table.addCell("Plantas amostradas");
        table.setHeaderRows(1);

        PdfPCell[] cells = table.getRow(0).getCells();

        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(BaseColor.LIGHT_GRAY);
        }

        for (int i = 0; i < planos.size(); i++) {
            String autor = planos.get(i).getAutor();
            String data = planos.get(i).getDate();
            Integer infestacao = planos.get(i).getPlantasInfestadas();
            Integer amostra = planos.get(i).getPlantasAmostradas();



            table.addCell(String.valueOf(autor));
            table.addCell(String.valueOf(data));
            table.addCell(String.valueOf(infestacao));
            table.addCell(String.valueOf(amostra));

        }

        PdfWriter.getInstance(document, output);
        document.open();
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 30.0f, Font.NORMAL, BaseColor.BLACK);
        Font g = new Font(Font.FontFamily.TIMES_ROMAN, 18.0f, Font.NORMAL, BaseColor.BLACK);
        Paragraph inicial = new Paragraph("Relatório de planos de amostragem\n\n", f);
        inicial.setAlignment(Element.ALIGN_CENTER);
        document.add(inicial);
        document.add(new Paragraph("Propriedade: "+ nomePropriedade +"\n", g));
        document.add(new Paragraph("Cultura: "+ nome +"\n", g));
        document.add(new Paragraph("Praga: "+ nomePraga +"\n\n", g));
        document.add(table);

        document.close();
        previewPdf();
    }

    private void previewPdf(){
        PackageManager packageManager = RelatorioPlanosRealizados.this.getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            //Uri uri = Uri.fromFile(pdfFile);
            Uri uri = FileProvider.getUriForFile(RelatorioPlanosRealizados.this,  BuildConfig.APPLICATION_ID + ".provider", pdfFile);
            intent.setDataAndType(uri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            RelatorioPlanosRealizados.this.startActivity(intent);
        } else {
            Toast.makeText(RelatorioPlanosRealizados.this, "Download a PDF Viewer to see the generated PDF", Toast.LENGTH_SHORT).show();
        }

    }

}
