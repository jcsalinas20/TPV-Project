package com.example.proyecto_bar_reinols_movil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OpcionesMesa extends AppCompatActivity {

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        final MediaPlayer gato_josu = MediaPlayer.create(this, R.raw.gato_josu);
        final MediaPlayer gemido_josu = MediaPlayer.create(this, R.raw.gemido_josu);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gato_josu.start();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            gemido_josu.start();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final MediaPlayer nintendoSwitch = MediaPlayer.create(this,R.raw.nintendo_switcht_click);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones_mesa);
        setTitle("Mesa " + MostrarMesas.mesaSeleccionada);

        TextView mesa = findViewById(R.id.numMesa);
        Button pedido = findViewById(R.id.pedido);
        Button revisarPedido = findViewById(R.id.revisar_pedido);
        Button cuenta = findViewById(R.id.cuenta);

        mesa.setText("Mesa " + MostrarMesas.mesaSeleccionada);

        pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nintendoSwitch.start();
                Intent intent = new Intent(OpcionesMesa.this, MostrarCategorias.class);
                startActivity(intent);
            }
        });

        revisarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nintendoSwitch.start();
                if (MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos.isEmpty()) {
                    Toast.makeText(OpcionesMesa.this, "Ningun producto seleccionado.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OpcionesMesa.this, MostrarCategorias.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(OpcionesMesa.this, MostrarComandaMesa.class);
                    startActivity(intent);
                }
            }
        });

        cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nintendoSwitch.start();
                Toast.makeText(OpcionesMesa.this, "No disponible", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
