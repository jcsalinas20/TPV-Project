package com.example.proyecto_bar_reinols_movil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class MostrarMesas extends AppCompatActivity {
    public static int mesaSeleccionada;
    public static HashMap<Integer, MesaComanda> mesasComanda = new HashMap<>();
    public static boolean comienzoDeAplicacion = false;
    MostrarCategorias categorias = new MostrarCategorias();

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        final MediaPlayer gemido_josu = MediaPlayer.create(this, R.raw.gemido_josu);
        final MediaPlayer gato_josu = MediaPlayer.create(this, R.raw.gato_josu);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gato_josu.start();
            super.onConfigurationChanged(newConfig);
            setContentView(R.layout.activity_mostrar_mesas_landscape);
            categorias.mostrarCategorias(MostrarMesas.this);// metodo para mostrar las categorias

            ListView list = findViewById(R.id.listaMesas);
            AdaptadorDeMesas adapter = new AdaptadorDeMesas(this);
            list.setAdapter(adapter);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            gemido_josu.start();
            super.onConfigurationChanged(newConfig);
            setContentView(R.layout.activity_mostrar_mesas);
            categorias.mostrarCategorias(MostrarMesas.this);// metodo para mostrar las categorias

            ListView list = findViewById(R.id.listaMesas);
            AdaptadorDeMesas adapter = new AdaptadorDeMesas(this);
            list.setAdapter(adapter);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int oreientacion = getResources().getConfiguration().orientation;
        if (oreientacion == Configuration.ORIENTATION_LANDSCAPE){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mostrar_mesas_landscape);
        } else  {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mostrar_mesas);
        }
        setTitle("Mesas");

        if (!comienzoDeAplicacion) {
            comienzoDeAplicacion = true;
            MesaComanda.llenarKeyHasMapMesaComanda();
            agregarMesas();
        }

        categorias.mostrarCategorias(MostrarMesas.this);// metodo para mostrar las categorias

        ListView list = findViewById(R.id.listaMesas);
        AdaptadorDeMesas adapter = new AdaptadorDeMesas(this);
        list.setAdapter(adapter);
    }

    private void agregarMesas() {
        for (int i = 0; i < Mesa.ITEMS.length; i++) {
            Mesa.ITEMS[i] = new Mesa("Mesa "+ (i+1));
        }
    }
}

class Mesa {
    String numMesa;

    public Mesa(String numMesa) {
        this.numMesa = numMesa;
    }

    public String getNumMesa() {
        return numMesa;
    }

    public void setNumMesa(String numMesa) {
        this.numMesa = numMesa;
    }

    public int getId() {
        return numMesa.hashCode();
    }

    public static Mesa[] ITEMS = new Mesa[MainActivity.numMaxMesas];
}

class AdaptadorDeMesas extends BaseAdapter {
    private Context context;

    public AdaptadorDeMesas(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return Mesa.ITEMS.length;
    }

    @Override
    public Mesa getItem(int position) {
        return Mesa.ITEMS[position];
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.botones_mesas, viewGroup, false);
        }

        Button numMesa = (Button) view.findViewById(R.id.numMesa);

        final Mesa item = getItem(position);
        numMesa.setText(item.getNumMesa());

        numMesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer nintendoSwitch = MediaPlayer.create(context,R.raw.nintendo_switcht_click);
                nintendoSwitch.start();
                MostrarMesas.mesaSeleccionada = position + 1;

                ConexionServer conServerComandaMesa = new ConexionServer();
                conServerComandaMesa.queConsultaHacer = "comanda_mesa";
                conServerComandaMesa.task(context);

                Intent intent = new Intent( context, OpcionesMesa.class);
                context.startActivity(intent);
            }
        });
        return view;
    }
}

class MesaComanda {

    ArrayList<Comanda> listaProductos = new ArrayList<>();

    public static void llenarKeyHasMapMesaComanda(){

        for (int i = 1; i <= MainActivity.numMaxMesas; i++){
            MostrarMesas.mesasComanda.put(i, new MesaComanda());
        }
    }

}