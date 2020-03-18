package com.example.appmip15;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FuncoesMIP extends AppCompatActivity {

    Button btnAddPraga;
    Button btnRealizarPA;
    Button btnSelectMC;
    Button btnConfirmMC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcoes_mip);

        btnAddPraga = findViewById(R.id.addPraga);
        btnAddPraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FuncoesMIP.this, AdicionarPragas.class); // Muda de tela quando
                startActivity(i);                                                            // clicar no botão
            }
        });

        btnRealizarPA = findViewById(R.id.RealizarPA);
        btnRealizarPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FuncoesMIP.this, RealizarPlano.class); // Muda de tela quando
                startActivity(i);
            }
        });

        btnSelectMC = findViewById(R.id.selectMC);
        btnSelectMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FuncoesMIP.this, SelecionarMC.class); // Muda de tela quando
                startActivity(i);
            }
        });

        btnConfirmMC = findViewById(R.id.confirmMC);
        btnConfirmMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FuncoesMIP.this, ConfirmaMC.class); // Muda de tela quando
                startActivity(i);
            }
        });

    }
}
