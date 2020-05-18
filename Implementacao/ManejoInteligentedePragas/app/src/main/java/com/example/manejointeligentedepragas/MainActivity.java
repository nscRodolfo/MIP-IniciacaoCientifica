package com.example.manejointeligentedepragas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manejointeligentedepragas.Crontroller.Controller_Usuario;
import com.example.manejointeligentedepragas.model.UsuarioModel;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.RectanglePromptBackground;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;


public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    public Button btnEntrar;
    public Button btnCadastro;

    UsuarioModel usu = new UsuarioModel();
    Intent k;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //menu novo
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout= findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        View headerView = navigationView.getHeaderView(0);

        ImageView imageView = headerView.findViewById(R.id.imageMenu);
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        imageView.setImageDrawable(drawable);

        TextView nomeMenu = headerView.findViewById(R.id.nomeMenu);
        //nomeMenu.setVisibility(View.GONE);
        nomeMenu.setText("Monitoramento Inteligente de Pragas");

        TextView emailMenu = headerView.findViewById(R.id.emailMenu);
        emailMenu.setVisibility(View.GONE);



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

                //showButtonPromp();
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
                Toast.makeText(MainActivity.this,"Para acessar seu perfil, faça login!", Toast.LENGTH_LONG).show();
                break;
            case R.id.drawerProp:
                Toast.makeText(MainActivity.this,"Para acessar as propriedades, faça login!", Toast.LENGTH_LONG).show();
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

    public void showButtonPromp(){
        new MaterialTapTargetPrompt.Builder(this)
                .setTarget(btnEntrar)
                .setPrimaryText("Different shapes")
                .setSecondaryText("Extend PromptFocal or PromptBackground to change the shapes")
                .setPromptBackground(new RectanglePromptBackground())
                .setPromptFocal(new RectanglePromptFocal())
                .show();
    }


}

