package com.example.appmip15;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RealizarPlano extends AppCompatActivity {

    Button btnRealizarLevantamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_plano);

        btnRealizarLevantamento =  findViewById(R.id.realizarLevantamento);
        btnRealizarLevantamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RealizarPlano.this, PA.class); // Muda de tela quando
                startActivity(i);
            }
        });
    }
}
