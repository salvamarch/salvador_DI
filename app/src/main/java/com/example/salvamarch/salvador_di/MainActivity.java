package com.example.salvamarch.salvador_di;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
EditText nomser, ano, cadena, numtemp, ide,titulo, temporada, numcap;
Button ins, mos, bor, cam;
mibd bd;
ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        nomser=findViewById(R.id.nomser);
        ano=findViewById(R.id.ano);
        cadena=findViewById(R.id.cadena);
        numtemp=findViewById(R.id.numtemp);
        ide=findViewById(R.id.ide);
        titulo=findViewById(R.id.titulo);
        temporada=findViewById(R.id.temporada);
        numcap=findViewById(R.id.numcap);
        ins=findViewById(R.id.ins);
        mos=findViewById(R.id.mos);
        bor=findViewById(R.id.bor);
        cam=findViewById(R.id.cam);
        lista=findViewById(R.id.lista);
        bd=new mibd(this);

        ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean resultado = false;
                if(ide.length()==0){
                    resultado = bd.insertarserie(nomser.getText(),
                            ano.getText(),
                            cadena.getText(), numtemp.getText());
                }
                else if(nomser.length()==0){
                    resultado = bd.insertarcapitulo(ide.getText(),
                            temporada.getText().toString(),
                            titulo.getText().toString(), numcap.getText());
                }else{
                    Toast.makeText(MainActivity.this, "Debes rellenar solo una tabla", Toast.LENGTH_SHORT).show();
                }

                if (resultado){
                    Toast.makeText(MainActivity.this, "Insertado correctamente", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Error en la insercción.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cur1=bd.listarserie();
                Cursor cur2=bd.listarcapitulo();
                ArrayAdapter<String> adapter;
                List<String> list=new ArrayList<String>();
                if(ide.length()==0){
                    if((cur1!=null)&&(cur1.getCount()>0)){
                        while (cur1.moveToNext()){
                            String fila="";
                            fila+="nombre: " + cur1.getString(0);
                            fila+="año: " + cur1.getString(1);
                            fila+="cadena: " + cur1.getString(2);
                            fila+="temporadas: " + cur1.getString(3);
                            list.add(fila);
                        }
                        adapter=new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
                        lista.setAdapter(adapter);
                        cur1.close();
                    }else{
                        Toast.makeText(MainActivity.this, "Vacio", Toast.LENGTH_SHORT).show();
                    }
                }else if(nomser.length()==0){
                    if((cur2!=null)&&(cur2.getCount()>0)){
                        while (cur2.moveToNext()){
                            String fila="";
                            fila+="nombre: " + cur2.getString(4);
                            fila+="año: " + cur2.getString(5);
                            fila+="cadena: " + cur2.getString(6);
                            fila+="temporadas: " + cur2.getString(7);
                            list.add(fila);
                        }
                        adapter=new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
                        lista.setAdapter(adapter);
                        cur2.close();
                    }else{
                        Toast.makeText(MainActivity.this, "Vacio", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        bor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ide.length()==0){
                    boolean resultado=bd.borrarserie(nomser.getText().toString());
                    if(resultado){
                        Toast.makeText(MainActivity.this, "Borrado correctamente", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Nada que borrar", Toast.LENGTH_SHORT).show();
                    }
                }else if(nomser.length()==0){
                    boolean resultado=bd.borrarcapitulo(ide.getText().toString());
                    if(resultado){
                        Toast.makeText(MainActivity.this, "Borrado correctamente", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Nada que borrar", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ide.length()==0){
                    boolean resultado=bd.actualizarserie(nomser.getText().toString(), ano.getText(), cadena.getText(), numtemp.getText());
                    if(resultado){
                        Toast.makeText(MainActivity.this, "Borrado correctamente", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Nada que borrar", Toast.LENGTH_SHORT).show();
                    }
                }else if(nomser.length()==0){
                    boolean resultado=bd.actualizarcapitulo(ide.getText().toString(), temporada.getText(), titulo.getText(), numcap.getText());
                    if(resultado){
                        Toast.makeText(MainActivity.this, "Borrado correctamente", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Nada que borrar", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
