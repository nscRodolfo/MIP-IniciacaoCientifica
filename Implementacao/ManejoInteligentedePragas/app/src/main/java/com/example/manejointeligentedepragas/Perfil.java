package com.example.manejointeligentedepragas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.manejointeligentedepragas.Crontroller.Controller_Usuario;
import com.example.manejointeligentedepragas.model.UsuarioModel;

public class Perfil extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public Button btnSair;
    public TextView tvNomePerfil;
    public TextView tvTelefonePerfil;
    public TextView tvEmailPerfil;
    private DrawerLayout drawerLayout;

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

        //menu novo
        Toolbar toolbar = findViewById(R.id.toolbar_perfil);
        setSupportActionBar(toolbar);
        drawerLayout= findViewById(R.id.drawer_layout_perfil);
        NavigationView navigationView = findViewById(R.id.nav_view_perfil);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        View headerView = navigationView.getHeaderView(0);

        Controller_Usuario controller_usuario = new Controller_Usuario(getBaseContext());
        String nomeUsu = controller_usuario.getUser().getNome();
        String emailUsu = controller_usuario.getUser().getEmail();

        TextView nomeMenu = headerView.findViewById(R.id.nomeMenu);
        nomeMenu.setText(nomeUsu);

        TextView emailMenu = headerView.findViewById(R.id.emailMenu);
        emailMenu.setText(emailUsu);

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

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.drawerPerfil:
                Intent i= new Intent(this, Perfil.class);
                startActivity(i);
                break;
            case R.id.drawerProp:
                Intent prop= new Intent(this, Propriedades.class);
                startActivity(prop);
                break;

            case R.id.drawerPlantas:
                Intent j = new Intent(this, VisualizaPlantas.class);
                startActivity(j);
                break;

            case R.id.drawerPrag:
                Intent k = new Intent(this, VisualizaPragas.class);
                startActivity(k);
                break;

            case R.id.drawerMet:
                Intent l = new Intent(this, VisualizaMetodos.class);
                startActivity(l);
                break;

            case R.id.drawerSobreMip:
                Intent p = new Intent(this, SobreMIP.class);
                startActivity(p);
                break;

            case R.id.drawerTutorial:

                break;

            case R.id.drawerSobre:
                Intent pp = new Intent(this, SobreMIP.class);
                startActivity(pp);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
