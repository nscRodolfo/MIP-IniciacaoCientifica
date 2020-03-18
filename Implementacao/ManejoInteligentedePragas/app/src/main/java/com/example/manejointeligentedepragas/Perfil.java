package com.example.manejointeligentedepragas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.manejointeligentedepragas.Crontroller.Controller_Usuario;
import com.example.manejointeligentedepragas.model.UsuarioModel;

public class Perfil extends AppCompatActivity {

    public Button btnSair;
    public TextView tvNomePerfil;
    public TextView tvTelefonePerfil;
    public TextView tvEmailPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        tvNomePerfil = findViewById(R.id.tvNomePerfil);
        tvTelefonePerfil = findViewById(R.id.tvTelefonePerfil);
        tvEmailPerfil = findViewById(R.id.tvEmailPerfil);

        Controller_Usuario cu1 = new Controller_Usuario(getBaseContext());

        tvNomePerfil.setText(""+cu1.getUser().getNome());
        tvTelefonePerfil.setText("Telefone: "+cu1.getUser().getTelefone());
        tvEmailPerfil.setText("E-mail: "+cu1.getUser().getEmail());

        btnSair = findViewById(R.id.btnDeslogarPerfil);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controller_Usuario cu = new Controller_Usuario(getBaseContext());
                cu.removerUsuario();
                Intent i = new Intent(Perfil.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
