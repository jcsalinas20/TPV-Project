package com.example.proyecto_bar_reinols_movil;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MostrarComandaMesa extends AppCompatActivity {

    static final int ANIMATION_DURATION = 400;// duracion de la animacion smooth (milisegundos)
    static private ComandasAdapter mMyAnimListAdapter;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        final MediaPlayer gato_josu = MediaPlayer.create(this, R.raw.gato_josu);
        final MediaPlayer gemido_josu = MediaPlayer.create(this, R.raw.gemido_josu);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gato_josu.start();
            super.onConfigurationChanged(newConfig);
            setContentView(R.layout.activity_mostrar_comanda_mesa_landscape);
            final MediaPlayer nintendoSwitch = MediaPlayer.create(this, R.raw.nintendo_switcht_click);
            TextView titulo = findViewById(R.id.numMesa);
            titulo.setText("Mesa " + MostrarMesas.mesaSeleccionada);

            // adapter para listaProductos
            mMyAnimListAdapter = new ComandasAdapter(this, MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos);
            ListView myListView = (ListView) findViewById(R.id.ListacomandaMesa);
            myListView.setAdapter(mMyAnimListAdapter);

            final Button confirmar = findViewById(R.id.confirmar);
            confirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    nintendoSwitch.start();
                    ConexionServer conServer = new ConexionServer();
                    conServer.queConsultaHacer = "enviar_productos";
                    conServer.task(MostrarComandaMesa.this);// pasar comanda al Server
                    Toast.makeText(MostrarComandaMesa.this, Login.camareroSeleccionado + ": Mesa " + MostrarMesas.mesaSeleccionada + " enviada", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            gemido_josu.start();
            super.onConfigurationChanged(newConfig);
            setContentView(R.layout.activity_mostrar_comanda_mesa);
            final MediaPlayer nintendoSwitch = MediaPlayer.create(this, R.raw.nintendo_switcht_click);
            TextView titulo = findViewById(R.id.numMesa);
            titulo.setText("Mesa " + MostrarMesas.mesaSeleccionada);

            // adapter para listaProductos
            mMyAnimListAdapter = new ComandasAdapter(this, MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos);
            ListView myListView = (ListView) findViewById(R.id.ListacomandaMesa);
            myListView.setAdapter(mMyAnimListAdapter);

            final Button confirmar = findViewById(R.id.confirmar);
            confirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    nintendoSwitch.start();
                    ConexionServer conServer = new ConexionServer();
                    conServer.queConsultaHacer = "enviar_productos";
                    conServer.task(MostrarComandaMesa.this);// pasar comanda al Server
                    Toast.makeText(MostrarComandaMesa.this, Login.camareroSeleccionado + ": Mesa " + MostrarMesas.mesaSeleccionada + " enviada", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int oreientacion = getResources().getConfiguration().orientation;
        if (oreientacion == Configuration.ORIENTATION_LANDSCAPE){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mostrar_comanda_mesa_landscape);
        } else  {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mostrar_comanda_mesa);
        }
        setTitle("Comanda");
final  MediaPlayer nintendoSwitch = MediaPlayer.create(this,R.raw.nintendo_switcht_click);
        TextView titulo = findViewById(R.id.numMesa);
        titulo.setText("Mesa " + MostrarMesas.mesaSeleccionada);

        // adapter para listaProductos
        mMyAnimListAdapter = new ComandasAdapter(this, MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos);
        ListView myListView = (ListView) findViewById(R.id.ListacomandaMesa);
        myListView.setAdapter(mMyAnimListAdapter);

        final Button confirmar = findViewById(R.id.confirmar);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nintendoSwitch.start();
                ConexionServer conServer = new ConexionServer();
                conServer.queConsultaHacer = "enviar_productos";
                conServer.task(MostrarComandaMesa.this);// pasar comanda al Server
                Toast.makeText(MostrarComandaMesa.this, Login.camareroSeleccionado + ": Mesa " + MostrarMesas.mesaSeleccionada + " enviada", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*public static void CalcularPreuTotal() {
        if (MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos.size()>0){
            float precioTotalFloat=0f;
            for (int i = 0; i < MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos.size(); i++) {
                precioTotalFloat=precioTotalFloat+(MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos.get(i).getPrecio()*MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos.get(i).getCantidad());
            }
            precioTotalFloat=Float.parseFloat(String.valueOf(round(precioTotalFloat,3)));
            preuTotal.setText("Total: " + precioTotalFloat + "€");
        }
    }
    public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }*/

    public static void deleteCell(final View v, final int index) {
        Animation.AnimationListener al = new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos.remove(index);// eliminacion en el array

                ViewHolder vh = (ViewHolder)v.getTag();
                vh.needInflate = true;

                mMyAnimListAdapter.notifyDataSetChanged();
            }
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationStart(Animation animation) {}
        };

        collapse(v, al);
    }

    private static void collapse(final View v, Animation.AnimationListener al) {
        final int initialHeight = v.getMeasuredHeight();

        Animation anim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                }
                else {
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        if (al!=null) {
            anim.setAnimationListener(al);
        }
        anim.setDuration(ANIMATION_DURATION);
        v.startAnimation(anim);
    }
}

/********CLASS COMANDA********/
class Comanda {

    String nombreProducto;
    float precio;
    int cantidad;

    /********CONSTRUCTOR********/
    public Comanda(String nombreProducto, float precio, int cantidad) {
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    /********SETTERS AND GETTERS********/
    public void sumarProducto(){
        this.cantidad++;
    }

    public void restarProducto(){
        this.cantidad--;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

/********CLASS VIEWHOLDER********/
class ViewHolder {
    public boolean needInflate;
    public TextView nombre;
    public TextView cantidad;
    Button btnSumar;
    Button btnRestar;
    Button btnRemove;

}

/********CLASS COMANDASADAPTER********/
class ComandasAdapter extends ArrayAdapter<Comanda> {
    private LayoutInflater mInflater;
    private Context mContext;

    /********CONSTRUCTOR********/
    public ComandasAdapter(@NonNull Context context, ArrayList<Comanda> list) {
        super(context, 0 , list);
        mContext = context;
        MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos = list;
        this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view;
        ViewHolder vh = new ViewHolder();
        final Comanda productoSeleccionado = MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos.get(position);// Guardar producto seleccionado

        if (convertView == null) {
            view = mInflater.inflate(R.layout.activity_comandas_adapter, parent, false);
            vh.nombre = (TextView) view.findViewById(R.id.nombreProducto);
            vh.cantidad = (TextView) view.findViewById(R.id.textView_contador);
            vh.btnSumar = (Button) view.findViewById(R.id.sumar);
            vh.btnRestar = (Button) view.findViewById(R.id.resta);
            vh.btnRemove = (Button) view.findViewById(R.id.remove);
            vh.needInflate = false;
            view.setTag(vh);
        } else if (((ViewHolder) convertView.getTag()).needInflate) {
            view = mInflater.inflate(R.layout.activity_comandas_adapter, parent, false);
            vh.nombre = (TextView) view.findViewById(R.id.nombreProducto);
            vh.cantidad = (TextView) view.findViewById(R.id.textView_contador);
            vh.btnSumar = (Button) view.findViewById(R.id.sumar);
            vh.btnRestar = (Button) view.findViewById(R.id.resta);
            vh.btnRemove = (Button) view.findViewById(R.id.remove);
            vh.needInflate = false;
            view.setTag(vh);
        } else {
            view = convertView;
        }

        vh = (ViewHolder) view.getTag();
        vh.nombre.setText(productoSeleccionado.getNombreProducto());// aplicar el nombre del producto
        vh.cantidad.setText(productoSeleccionado.getCantidad()+"");// aplicar la cantidad del producto

        final TextView cantidad = vh.cantidad;
        vh.btnRestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productoSeleccionado.getCantidad() > 1){
                    productoSeleccionado.restarProducto();
                    cantidad.setText(productoSeleccionado.getCantidad() + "");// actualizar la cantidad
                }else {
                    productoSeleccionado.setCantidad(0);
                    MostrarComandaMesa.deleteCell(view, position);// Efecto smooth al eliminarlo
                }

                if (MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos.size() == 1 && productoSeleccionado.getCantidad() == 0) {
                    Intent intent = new Intent(mContext, OpcionesMesa.class);
                    mContext.startActivity(intent);
                    ConexionServer conServer = new ConexionServer();
                    conServer.queConsultaHacer = "enviar_productos";
                    conServer.task(mContext);// pasar comanda al Server

                }
            }
        });
        vh.btnSumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productoSeleccionado.sumarProducto();
                cantidad.setText(productoSeleccionado.getCantidad() + "");// actualizar la cantidad
            }
        });
        vh.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productoSeleccionado.setCantidad(0);
                MostrarComandaMesa.deleteCell(view, position);// Efecto smooth al eliminarlo

                if (MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos.size() == 1 && productoSeleccionado.getCantidad() == 0) {
                    Intent intent = new Intent(mContext, OpcionesMesa.class);
                    mContext.startActivity(intent);
                    ConexionServer conServer = new ConexionServer();
                    conServer.queConsultaHacer = "enviar_productos";
                    conServer.task(mContext);// pasar comanda al Server
                }
            }
        });

        return view;
    }
}