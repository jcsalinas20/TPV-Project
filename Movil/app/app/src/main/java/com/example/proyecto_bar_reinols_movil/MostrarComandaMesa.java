package com.example.proyecto_bar_reinols_movil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MostrarComandaMesa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_comanda_mesa);

        ListView listView = (ListView) findViewById(R.id.ListacomandaMesa);

        ComandasAdapter adapter = new ComandasAdapter(this, ComandasAdapter.listaUsers);
        listView.setAdapter(adapter);

        /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                MostrarProductos.productosComanda);

        lv.setAdapter(arrayAdapter);*/
    }
}
