package com.example.proyecto_bar_reinols_movil;

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
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    public static String camareroSeleccionado;
    public static boolean comienzoDeAplicacion = false;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        final MediaPlayer gemido_josu = MediaPlayer.create(this, R.raw.gemido_josu);
        final MediaPlayer gato_josu = MediaPlayer.create(this, R.raw.gato_josu);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gato_josu.start();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            gemido_josu.start();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        if (!comienzoDeAplicacion) {
            comienzoDeAplicacion = true;
            agregarCamareros();
        }

        GridView gridview = (GridView) findViewById(R.id.grid);
        gridview.setAdapter(new AdaptadorDeCamareros(this));
    }

    private void agregarCamareros() {
        for (int i = 0; i < Camareros.ITEMS.length; i++) {
            Camareros.ITEMS[i] = new Camareros(MainActivity.nombreCamareros.get(i), MainActivity.imagenCamareros.get(i));
        }
    }
}

class Camareros {
    private String nombre;
    private byte[] imageByte;

    public Camareros(String nombre, byte[] imageByte) {
        this.nombre = nombre;
        this.imageByte = imageByte;
    }

    public String getNombre() {
        return nombre;
    }

    public byte[] getImageByte() {
        return imageByte;
    }

    public int getId() {
        return nombre.hashCode();
    }

    public static Camareros[] ITEMS = new Camareros[MainActivity.nombreCamareros.size()];

    public static Camareros getItem(int id) {
        for (Camareros item : ITEMS) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}

class AdaptadorDeCamareros extends BaseAdapter {
    private Context context;

    public AdaptadorDeCamareros(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return Camareros.ITEMS.length;
    }

    @Override
    public Camareros getItem(int position) {
        return Camareros.ITEMS[position];
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_item, viewGroup, false);
        }

        ImageButton imagen = (ImageButton) view.findViewById(R.id.imagen_camarero);
        TextView nombre = (TextView) view.findViewById(R.id.nombre_camarero);

        final Camareros item = getItem(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(item.getImageByte(), 0, item.getImageByte().length);
        imagen.setImageBitmap(bitmap);
        nombre.setText(item.getNombre());

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer nintendoSwitch = MediaPlayer.create(context,R.raw.nintendo_switcht_click);
                nintendoSwitch.start();
                Toast.makeText(context, "Bienvenido " + item.getNombre(), Toast.LENGTH_SHORT).show();
                Login.camareroSeleccionado = item.getNombre();
                Intent intent = new Intent( context, MostrarMesas.class);
                context.startActivity(intent);
            }
        });
        return view;
    }

}