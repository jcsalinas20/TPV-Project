package com.example.proyecto_bar_reinols_movil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class ComandasAdapter extends ArrayAdapter<Comanda> {

    private Context mContext;
    static ArrayList<Comanda> listaUsers = new ArrayList<Comanda>();

    public ComandasAdapter(@NonNull Context context, ArrayList<Comanda> list) {
        super(context, 0 , list);
        mContext = context;
        listaUsers = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.activity_comandas_adapter, parent, false);
        }

        Comanda productoSeleccionado = listaUsers.get(position);

        /*ImageView imagen = (ImageView) listItem.findViewById(R.id.imageView_fotoPerfil);
        imagen.setImageResource(perfilUsuario.getFotoPerfil());*/

        TextView nombreUser = (TextView) listItem.findViewById(R.id.nombreProducto);
        nombreUser.setText(productoSeleccionado.getNombreProducto());

        TextView contadorIntentos = (TextView) listItem.findViewById(R.id.textView_contador);
        contadorIntentos.setText(productoSeleccionado.getCantidad() + " cantidad");

        Button btnSumar = (Button) listItem.findViewById(R.id.sumar);
        btnSumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comanda com = new Comanda();
                com.sumarProducto();
                Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();

            }
        });

        return listItem;
    }
}