package com.example.appmip15;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ConfirmaMC extends AppCompatActivity {

    Button btnAplica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirma_mc);


        btnAplica = findViewById(R.id.AplicaMC);
        btnAplica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Métodos de controle aplicados", Toast.LENGTH_LONG).show();

                Intent i = new Intent(ConfirmaMC.this, MainActivity.class); // Muda de tela quando
                startActivity(i);
            }
        });

    }
}
