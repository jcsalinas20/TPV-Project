package com.example.proyecto_bar_reinols_movil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class splash_activity extends AppCompatActivity {
    private final int DURACION_SPLASH = 1000;
    ConexionServer conServerCamareros = new ConexionServer();
    ConexionServer conServerNumMesas = new ConexionServer();
    ConexionServer conServerCategorias = new ConexionServer();
    ConexionServer conServerCatImg = new ConexionServer();
    ConexionServer conServerProdImg = new ConexionServer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final MediaPlayer nintendoSwitch = MediaPlayer.create(this, R.raw.nintendo_switcht_click);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setTheme(R.style.Splash_landscape);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash_activity_landscape);
        } else {
            setTheme(R.style.Splash);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash_activity);
        }
        nintendoSwitch.start();

        conServerCamareros.queConsultaHacer = "camareros";
        conServerCamareros.task(splash_activity.this);// pasar comanda al Server

        conServerNumMesas.queConsultaHacer = "num_mesas";
        conServerNumMesas.task(splash_activity.this);// pasar comanda al Server

        conServerCatImg.queConsultaHacer = "byte_cate";
        conServerCatImg.task(splash_activity.this);// pasar comanda al Server

        conServerProdImg.queConsultaHacer = "byte_prod";
        conServerProdImg.task(splash_activity.this);// pasar comanda al Server

        conServerCategorias.queConsultaHacer = "categorias";
        conServerCategorias.task(splash_activity.this);// pasar comanda al Server

        new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(splash_activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            };
        }, DURACION_SPLASH);
    }
}