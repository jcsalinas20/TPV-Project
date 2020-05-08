package com.example.proyecto_bar_reinols_movil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MostrarCategorias extends AppCompatActivity {
    public static HashMap<String, Categoria> categorias = new HashMap<>();// HashMap para guardar las categorias
    public static GridView gv;// Mostrar
    public static String categoriaSeleccionada;// Guardar la categoria seleccionada
    public static ArrayList<String> ListCategorias = new ArrayList<String>();// Guardar las categorias

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        final MediaPlayer gato_josu = MediaPlayer.create(this, R.raw.gato_josu);
        final MediaPlayer gemido_josu = MediaPlayer.create(this, R.raw.gemido_josu);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gato_josu.start();
            super.onConfigurationChanged(newConfig);
            setContentView(R.layout.activity_mostrar_categorias_landscape);
            final MediaPlayer nintendoSwitch = MediaPlayer.create(this, R.raw.nintendo_switcht_click);

            // GridView
            gv = (GridView) findViewById(R.id.grid);
            gv.setAdapter(new CategoriaAdapter(MostrarCategorias.this));

            // listener del ListView
            Button btnRevisarPedido = findViewById(R.id.btnRevisarPedido);
            btnRevisarPedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nintendoSwitch.start();
                    if (MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos.isEmpty()) {
                        Toast.makeText(MostrarCategorias.this, "Ningun producto seleccionado.", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(MostrarCategorias.this, MostrarComandaMesa.class);
                        startActivity(intent);
                    }
                }
            });
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            gemido_josu.start();
            super.onConfigurationChanged(newConfig);
            setContentView(R.layout.activity_mostrar_categorias);
            final MediaPlayer nintendoSwitch = MediaPlayer.create(this, R.raw.nintendo_switcht_click);

            // GridView
            gv = (GridView) findViewById(R.id.grid);
            gv.setAdapter(new CategoriaAdapter(MostrarCategorias.this));

            // listener del ListView
            Button btnRevisarPedido = findViewById(R.id.btnRevisarPedido);
            btnRevisarPedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nintendoSwitch.start();
                    if (MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos.isEmpty()) {
                        Toast.makeText(MostrarCategorias.this, "Ningun producto seleccionado.", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(MostrarCategorias.this, MostrarComandaMesa.class);
                        startActivity(intent);
                    }
                }
            });
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int oreientacion = getResources().getConfiguration().orientation;
        if (oreientacion == Configuration.ORIENTATION_LANDSCAPE){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mostrar_categorias_landscape);
        } else  {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mostrar_categorias);
        }
        setTitle("Categorias");
        final  MediaPlayer nintendoSwitch = MediaPlayer.create(this,R.raw.nintendo_switcht_click);

        // GridView
        gv = (GridView) findViewById(R.id.grid);
        gv.setAdapter(new CategoriaAdapter(MostrarCategorias.this));

        // listener del ListView
        Button btnRevisarPedido = findViewById(R.id.btnRevisarPedido);
        btnRevisarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nintendoSwitch.start();
                if (MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos.isEmpty()) {
                    Toast.makeText(MostrarCategorias.this, "Ningun producto seleccionado.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MostrarCategorias.this, MostrarComandaMesa.class);
                    startActivity(intent);
                }
            }
        });
    }

    public static void agregarCategorias(int position, String nombre, byte[] imagen) {
        Categoria.ITEMS[position] = new Categoria(nombre, imagen);
    }

    // metodo para mostrar las categorias
    public void mostrarCategorias(final Context context){
        try {

            LecturaProductosXML.lecturaCategorias(context);// metodo para leer las categorias
            LecturaProductosXML.lecturaImagenCategorias(context);
            LecturaProductosXML.lecturaIdProductos(context);// metodo para leer los IDs
            LecturaProductosXML.lecturaNombreProductos(context);// metodo para leer los nombres productos
            LecturaProductosXML.lecturaPrecioProductos(context);// metodo para leer los precios de los productos
            LecturaProductosXML.lecturaImagenProductos(context);// metodo para leer la ruta de la imagen

            // añadir categorias del HashMap al arrayList ListCategorias
            for (String key : categorias.keySet()){
                ListCategorias.add(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Categoria {

    String nombre;// Nombre de la Categoria
    byte[] imagen;
    HashMap<Integer, Producto> productos = new HashMap<>();// HashMap para guardar los productos de las categorias

    public Categoria(String nombre, byte[] imagen) {
        this.imagen = imagen;
        this.nombre = nombre;
    }

    public Categoria() {}

    /*********SETTERS AND GETTERS*********/
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public int getId() {
        return imagen.hashCode();
    }

    public static Categoria[] ITEMS;

    public HashMap<Integer, Producto> getProductos() {
        return productos;
    }

    public static Categoria getItem(int id) {
        for (Categoria item : ITEMS) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}

class CategoriaAdapter extends BaseAdapter {
    private Context context;

    public CategoriaAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return Categoria.ITEMS.length;
    }

    @Override
    public Categoria getItem(int position) {
        return Categoria.ITEMS[position];
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(final int position, View view, final ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_item2, viewGroup, false);
        }

        Categoria item = getItem(position);

        ImageButton imagen = (ImageButton) view.findViewById(R.id.imagen_categoria);
        Bitmap bitmap = BitmapFactory.decodeByteArray(item.getImagen(), 0, item.getImagen().length);
        imagen.setImageBitmap(bitmap);

        TextView texto = (TextView) view.findViewById(R.id.textCategoria);
        texto.setText(item.getNombre());

        final String itemNombre = item.getNombre();
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer nintendoSwitch = MediaPlayer.create(context,R.raw.nintendo_switcht_click);
                nintendoSwitch.start();
                // Guardar el nombre de la categoria en una variable
                MostrarCategorias.categoriaSeleccionada = itemNombre;
                Intent intent = new Intent(context, MostrarProductos.class);
                context.startActivity(intent);// cambio de Actividad a MostrarProductos
            }
        });
        return view;
    }
}