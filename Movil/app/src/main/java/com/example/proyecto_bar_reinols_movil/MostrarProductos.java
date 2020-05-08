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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

 public class MostrarProductos extends AppCompatActivity {
    private ListView lv;// visualizar ListView

     @Override
     public void onConfigurationChanged(Configuration newConfig) {
         super.onConfigurationChanged(newConfig);
         final MediaPlayer gato_josu = MediaPlayer.create(this, R.raw.gato_josu);
         final MediaPlayer gemido_josu = MediaPlayer.create(this, R.raw.gemido_josu);
         // Checks the orientation of the screen
         if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
             gato_josu.start();
             super.onConfigurationChanged(newConfig);
             setContentView(R.layout.activity_mostrar_productos_landscape);
             final MediaPlayer nintendoSwitch = MediaPlayer.create(this, R.raw.nintendo_switcht_click);
             leerCategoriasXML(MostrarProductos.this);// metodo para leer

             TextView title = findViewById(R.id.titleCategoria);
             title.setText(MostrarCategorias.categoriaSeleccionada);

             // listener del ListView
             Button btnRevisarPedido = findViewById(R.id.btnRevisarPedido);
             btnRevisarPedido.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     nintendoSwitch.start();
                     if (MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos.isEmpty()) {
                         Toast.makeText(MostrarProductos.this, "Ningun producto seleccionado.", Toast.LENGTH_SHORT).show();
                     } else {
                         Intent intent = new Intent(MostrarProductos.this, MostrarComandaMesa.class);
                         startActivity(intent);
                     }
                 }
             });
         } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
             gemido_josu.start();
             super.onConfigurationChanged(newConfig);
             setContentView(R.layout.activity_mostrar_productos);
             final MediaPlayer nintendoSwitch = MediaPlayer.create(this, R.raw.nintendo_switcht_click);
             leerCategoriasXML(MostrarProductos.this);// metodo para leer

             TextView title = findViewById(R.id.titleCategoria);
             title.setText(MostrarCategorias.categoriaSeleccionada);

             // listener del ListView
             Button btnRevisarPedido = findViewById(R.id.btnRevisarPedido);
             btnRevisarPedido.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     nintendoSwitch.start();
                     if (MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos.isEmpty()) {
                         Toast.makeText(MostrarProductos.this, "Ningun producto seleccionado.", Toast.LENGTH_SHORT).show();
                     } else {
                         Intent intent = new Intent(MostrarProductos.this, MostrarComandaMesa.class);
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
            setContentView(R.layout.activity_mostrar_productos_landscape);
        } else  {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mostrar_productos);
        }
        setTitle(MostrarCategorias.categoriaSeleccionada);
        final MediaPlayer nintendoSwitch = MediaPlayer.create(this,R.raw.nintendo_switcht_click);

        leerCategoriasXML(MostrarProductos.this);// metodo para leer

        TextView title = findViewById(R.id.titleCategoria);
        title.setText(MostrarCategorias.categoriaSeleccionada);

        // listener del ListView
        Button btnRevisarPedido = findViewById(R.id.btnRevisarPedido);
        btnRevisarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nintendoSwitch.start();
                if (MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos.isEmpty()) {
                    Toast.makeText(MostrarProductos.this, "Ningun producto seleccionado.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MostrarProductos.this, MostrarComandaMesa.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void leerCategoriasXML(final Context context){
         final  MediaPlayer nintendoSwitch = MediaPlayer.create(context,R.raw.nintendo_switcht_click);
        ProducteAdapter.listaProductos.clear();// limpiar el array
        try {
            // añadir producto a listaProductos
            for (Integer key : MostrarCategorias.categorias.get(MostrarCategorias.categoriaSeleccionada).getProductos().keySet()){
                Producto p = MostrarCategorias.categorias.get(MostrarCategorias.categoriaSeleccionada).getProductos().get(key);
                ProducteAdapter.listaProductos.add(p);
            }

            // mostrar ListView
            lv = (ListView) findViewById(R.id.llistaProductos);
            ProducteAdapter adapter = new ProducteAdapter(this, ProducteAdapter.listaProductos);
            lv.setAdapter(adapter);

            // Listener del ListView
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    nintendoSwitch.start();
                    Producto productoSeleccionado = ProducteAdapter.listaProductos.get(position);

                    boolean onArray=false;
                    for (Integer i = 0; i < MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos.size(); i++) {
                        // si el producto esta repetido se suma
                        if (MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos.get(i).getNombreProducto().equalsIgnoreCase(productoSeleccionado.getNombre())){
                            onArray=true;
                            MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos.get(i).setCantidad(MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos.get(i).getCantidad()+1);
                            Toast.makeText(context, "Hay "+MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos.get(i).getCantidad()+" "+productoSeleccionado.getNombre()+" en el carrito", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    if (!onArray) {

                        Comanda c1 = new Comanda(productoSeleccionado.getNombre(),productoSeleccionado.getPrecio(), 1);
                        MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos.add(c1);

                        Toast.makeText(context, "Hay 1 "+productoSeleccionado.getNombre()+" en el carrito", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ProducteAdapter extends ArrayAdapter<Producto> {

    private Context mContext;
    static ArrayList<Producto> listaProductos = new ArrayList<Producto>();// guardar lista de productos

    /********CONSTRUCTOR********/
    public ProducteAdapter(@NonNull Context context, ArrayList<Producto> list) {
        super(context, 0 , list);
        mContext = context;
        listaProductos = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.activity_productos_adapter, parent, false);
        }

        Producto productoSeleccionado = listaProductos.get(position);

        ImageView imagen = (ImageView) listItem.findViewById(R.id.fotoProducto);
        Bitmap bitmap = BitmapFactory.decodeByteArray(productoSeleccionado.getRutaImagen(), 0, productoSeleccionado.getRutaImagen().length);
        imagen.setImageBitmap(bitmap);

        TextView nombreProducto = (TextView) listItem.findViewById(R.id.nombreProducto);
        nombreProducto.setText(productoSeleccionado.getNombre());

        TextView precioProducto = (TextView) listItem.findViewById(R.id.precioProducto);
        precioProducto.setText(productoSeleccionado.getPrecio()+"€");

        return listItem;
    }
}

class Producto {

    /********VARIABLES********/
    int id;
    String nombre;
    float precio;
    String descripcion;
    byte[] rutaImagen;

    /********SETTERS AND GETTERS********/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(byte[] rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    /********TO_STRING********/
    @Override
    public String toString() {
        return nombre+" - "+precio+"€";
    }
}
