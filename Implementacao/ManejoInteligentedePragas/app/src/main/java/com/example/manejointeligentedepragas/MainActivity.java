package com.example.manejointeligentedepragas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.manejointeligentedepragas.Crontroller.Controller_Usuario;
import com.example.manejointeligentedepragas.model.UsuarioModel;


public class MainActivity extends AppCompatActivity {


    public Button btnEntrar;
    public Button btnCadastro;
    public Button btnMIP;

    UsuarioModel usu = new UsuarioModel();
    Intent k;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usu = new Controller_Usuario(getBaseContext()).getUser();
        k = new Intent(MainActivity.this,Propriedades.class);
        if(usu.getEmail() != null)
        {
            //Toast.makeText(MainActivity.this,usu.getEmail(), Toast.LENGTH_LONG).show();
            startActivity(k);
            finish();
        }

        btnEntrar = findViewById(R.id.btnEntrar);
        btnCadastro = findViewById(R.id.btnCadastro);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this, Entrar.class);
                startActivity(i);
            }
        });

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this, Cadastro.class);
                startActivity(i);
            }
        });

        btnMIP = findViewById(R.id.btnInf);
        btnMIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this, SobreMIP.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pragas_main:
                Intent k = new Intent(this, VisualizaPragas.class);
                startActivity(k);
                return true;

            case R.id.plantas_main:
                Intent j = new Intent(this, VisualizaPlantas.class);
                startActivity(j);
                return true;

            case R.id.metodo_de_controle_main:
                Intent l = new Intent(this, VisualizaMetodos.class);
                startActivity(l);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    public boolean OnCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu_main, menu);
        return true;
    }
    public boolean OnOptionsItemSelected(MenuItem menuI)
    {
        int id = menuI.getItemId();

        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.OnOptionsItemSelected(menuI);
    }*/


}

