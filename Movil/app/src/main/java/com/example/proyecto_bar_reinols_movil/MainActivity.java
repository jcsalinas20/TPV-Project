package com.example.proyecto_bar_reinols_movil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static int numMaxMesas;
    public static ArrayList<String> nombreCamareros = new ArrayList<>();
    public static ArrayList<byte[]> imagenCamareros = new ArrayList<>();

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        final MediaPlayer lets_do_this = MediaPlayer.create(this,R.raw.lets_do_this);
        final MediaPlayer gato_josu = MediaPlayer.create(this, R.raw.gato_josu);
        final MediaPlayer gemido_josu = MediaPlayer.create(this, R.raw.gemido_josu);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gato_josu.start();
            super.onConfigurationChanged(newConfig);
            setContentView(R.layout.activity_main_landscape);
            Button btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
            btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lets_do_this.start();
                    Intent intent =  new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                }
            });

            Button btnRegistrar = findViewById(R.id.btnRegistrar);
            btnRegistrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lets_do_this.start();
                    Intent intent =  new Intent(MainActivity.this, Registrarse.class);
                    startActivity(intent);
                }
            });
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            gemido_josu.start();
            super.onConfigurationChanged(newConfig);
            setContentView(R.layout.activity_main);
            Button btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
            btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lets_do_this.start();
                    Intent intent =  new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                }
            });

            Button btnRegistrar = findViewById(R.id.btnRegistrar);
            btnRegistrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lets_do_this.start();
                    Intent intent =  new Intent(MainActivity.this, Registrarse.class);
                    startActivity(intent);
                }
            });
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final MediaPlayer lets_do_this = MediaPlayer.create(this,R.raw.lets_do_this);
        int oreientacion = getResources().getConfiguration().orientation;
        if (oreientacion == Configuration.ORIENTATION_LANDSCAPE){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_landscape);
        } else  {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }
        setTitle("Inicio");

        Button btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lets_do_this.start();
                Intent intent =  new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        Button btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lets_do_this.start();
                Intent intent =  new Intent(MainActivity.this, Registrarse.class);
                startActivity(intent);
            }
        });
    }
}