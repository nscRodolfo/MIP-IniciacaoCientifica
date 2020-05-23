package com.example.manejointeligentedepragas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.manejointeligentedepragas.Crontroller.Controller_Usuario;

public class Relatorios extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
        {

    int Cod_Propriedade;
    int codCultura;
    String nome;
    boolean aplicado;
    String nomePropriedade;

    RelativeLayout rlPragasContagem;
    RelativeLayout rlPlanosAmostragem;
    RelativeLayout rlCaldasAplicadas;
    RelativeLayout rlRelatoriosAplicacoesContagens;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorios);

        Cod_Propriedade = getIntent().getIntExtra("Cod_Propriedade", 0);
        codCultura = getIntent().getIntExtra("Cod_Cultura", 0);
        nome = getIntent().getStringExtra("NomeCultura");
        aplicado = getIntent().getBooleanExtra("Aplicado", false);
        nomePropriedade = getIntent().getStringExtra("nomePropriedade");

        //menu novo
        Toolbar toolbar = findViewById(R.id.toolbar_relatorio);
        setSupportActionBar(toolbar);
        drawerLayout= findViewById(R.id.drawer_layout_relatorio);
        NavigationView navigationView = findViewById(R.id.nav_view_relatorios);
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
                Intent i = new Intent(Relatorios.this, SelecionaPragaPlanosRealizados.class);
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
                Intent i = new Intent(Relatorios.this, SelecionaPragaRelatorioAplicacoesRealizadas.class);
                i.putExtra("Cod_Cultura", codCultura);
                i.putExtra("NomeCultura", nome);
                i.putExtra("Cod_Propriedade", Cod_Propriedade);
                i.putExtra("Aplicado", aplicado);
                i.putExtra("nomePropriedade", nomePropriedade);
                startActivity(i);
            }
        });

        rlRelatoriosAplicacoesContagens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // intent grafico aplicações
                Intent i = new Intent(Relatorios.this, SelecionaPragaRelatorioAplicacoesPlanos.class);
                i.putExtra("Cod_Cultura", codCultura);
                i.putExtra("NomeCultura", nome);
                i.putExtra("Cod_Propriedade", Cod_Propriedade);
                i.putExtra("Aplicado", aplicado);
                i.putExtra("nomePropriedade", nomePropriedade);
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
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean("isIntroOpened",false);
                        editor.commit();

                        Intent intro = new Intent(this, IntroActivity.class);
                        startActivity(intro);
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
