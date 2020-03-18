package com.example.appmip15;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ContagemPragas extends AppCompatActivity {

    Button btnCalcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contagem_pragas);


        btnCalcular = findViewById(R.id.calcular);
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ContagemPragas.this, SelecionarMC.class); // Muda de tela quando
                startActivity(i);
            }
        });


    }
}
