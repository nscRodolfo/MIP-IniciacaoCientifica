package com.example.manejointeligentedepragas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

public class Relatorios extends AppCompatActivity {

    int Cod_Propriedade;
    int codCultura;
    String nome;
    boolean aplicado;
    String nomePropriedade;

    RelativeLayout rlPragasContagem;
    RelativeLayout rlPlanosAmostragem;
    RelativeLayout rlCaldasAplicadas;
    RelativeLayout rlRelatoriosAplicacoesContagens;

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
        setContentView(R.layout.activity_relatorios);

        Cod_Propriedade = getIntent().getIntExtra("Cod_Propriedade", 0);
        codCultura = getIntent().getIntExtra("Cod_Cultura", 0);
        nome = getIntent().getStringExtra("NomeCultura");
        aplicado = getIntent().getBooleanExtra("Aplicado", false);
        nomePropriedade = getIntent().getStringExtra("nomePropriedade");

        setTitle("MIP² | Relatórios "+nome);
        rlPragasContagem = findViewById(R.id.rlRelatoriosPragasContagens);
        rlPlanosAmostragem = findViewById(R.id.rlRelatoriosPlanosDeAmostragem);
        rlCaldasAplicadas = findViewById(R.id.rlCaldasAplicadas);
        rlRelatoriosAplicacoesContagens = findViewById(R.id.rlRelatoriosAplicacoesContagens);

        rlPragasContagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Relatorios.this, SelecionaPragaRelatorioPragaPlano.class);
                i.putExtra("Cod_Cultura", codCultura);
                i.putExtra("NomeCultura", nome);
                i.putExtra("Cod_Propriedade", Cod_Propriedade);
                i.putExtra("Aplicado", aplicado);
                i.putExtra("nomePropriedade", nomePropriedade);
                startActivity(i);
            }
        });

        rlPlanosAmostragem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Relatorios.this, RelatorioPlanosRealizados.class);
                i.putExtra("Cod_Cultura", codCultura);
                i.putExtra("NomeCultura", nome);
                i.putExtra("Cod_Propriedade", Cod_Propriedade);
                i.putExtra("Aplicado", aplicado);
                i.putExtra("nomePropriedade", nomePropriedade);
                startActivity(i);
            }
        });

        rlCaldasAplicadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent aplicações realizadas na cultura
            }
        });

        rlRelatoriosAplicacoesContagens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // intent grafico aplicações
            }
        });

    }
}
