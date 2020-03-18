package com.example.appmip15;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdicionarPragas extends AppCompatActivity {

    Button btnAddPragas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_pragas);

        btnAddPragas = findViewById(R.id.addPraga);
        btnAddPragas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Praga Adicionada", Toast.LENGTH_LONG).show();

                Intent i = new Intent(AdicionarPragas.this, MainActivity.class); // Muda de tela quando
                startActivity(i);
            }
        });
    }
}
