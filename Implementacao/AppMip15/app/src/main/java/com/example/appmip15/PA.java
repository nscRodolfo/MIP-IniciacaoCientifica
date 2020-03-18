package com.example.appmip15;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PA extends AppCompatActivity {

    Button btnRealizarLevantamento2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p);

        btnRealizarLevantamento2 = findViewById(R.id.realizarLevantamento2);
        btnRealizarLevantamento2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PA.this, ContagemPragas.class); // Muda de tela quando
                startActivity(i);
            }
        });
    }
}
