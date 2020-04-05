package com.example.manejointeligentedepragas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class SobreMIP extends AppCompatActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
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

    TextView explicacao;
    TextView praga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre_mip);

       // setTitle("MIP² | Sobre o aplicativo");

        explicacao = findViewById(R.id.tvExplicacao);
        explicacao.setText("O Manejo Integrado de Pragas (MIP) é uma filosofia de controle de pragas que procura preservar e incrementar os fatores de mortalidade natural," +
                            " através do uso integrado de todas as técnicas de combate possíveis, selecionadas com base nos parâmetros econômicos, ecológicos e sociológicos.");

        praga = findViewById(R.id.tvSobrePraga);
        praga.setText("Praga: é qualquer organismo que causa um dano econômico.");
    }
}
