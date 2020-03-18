package com.example.manejointeligentedepragas;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;

public class AcoesCultura extends AppCompatActivity {

    public RelativeLayout rlVPA;

    public RelativeLayout rlRPA;

    int Cod_Propriedade;

    boolean aplicado;

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

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acoes_cultura);

        Cod_Propriedade = getIntent().getIntExtra("Cod_Propriedade", 0);

        /*
        View actionBar = getLayoutInflater().inflate(R.layout.actionbar, null);

        TextView tvTitulo = (TextView) actionBar.findViewById(R.id.tvTituloActionBar);

        String nome = getIntent().getStringExtra("NomeCultura");

        tvTitulo.setText(nome);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        */

        final int codCultura = getIntent().getIntExtra("Cod_Cultura", 0);
        final String nome = getIntent().getStringExtra("NomeCultura");
        aplicado = getIntent().getBooleanExtra("Aplicado", false);

        setTitle("MIP² | "+nome);

        rlVPA = findViewById(R.id.rlVPA);
        rlVPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AcoesCultura.this, Pragas.class);
                i.putExtra("Cod_Cultura", codCultura);
                i.putExtra("NomeCultura", nome);
                i.putExtra("Cod_Propriedade", Cod_Propriedade);
                i.putExtra("Aplicado", aplicado);
                startActivity(i);
            }
        });

        rlRPA = findViewById(R.id.rlRPA);
        rlRPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AcoesCultura.this, RealizarPlano.class);
                i.putExtra("Cod_Cultura", codCultura);
                i.putExtra("NomeCultura", nome);
                i.putExtra("Cod_Propriedade", Cod_Propriedade);
                i.putExtra("Aplicado", aplicado);
                startActivity(i);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(AcoesCultura.this,Cultura.class);
        i.putExtra("Cod_Propriedade", Cod_Propriedade);
        startActivity(i);
    }
}
