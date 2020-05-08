package com.example.proyecto_bar_reinols_movil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {
    HashMap<String, Categoria> categorias = new HashMap<>();
    private ListView lv;
    public static String categoriaSeleccionada;
    TextView tv;
    private ArrayList<String>ListCategorias = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Categorias");

        Comanda c1 = new Comanda("Frankfurt", 2);
        ComandasAdapter.listaUsers.add(c1);
        Comanda c2 = new Comanda("Coca", 1);
        ComandasAdapter.listaUsers.add(c2);
        Comanda c3 = new Comanda("Maria", 1);
        ComandasAdapter.listaUsers.add(c3);

        ImageButton imgBtn = findViewById(R.id.imageButton);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MostrarComandaMesa.class);
                startActivity(intent);
            }
        });

        leerCategoriasXML(MainActivity.this);
    }

    public void leerCategoriasXML(final Context context){
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(getResources().openRawResource(R.raw.productes));
            NodeList nList = doc.getElementsByTagName("categoria");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    String key = nNode.getAttributes().getNamedItem("nombre").getNodeValue();
                    categorias.put(key, new Categoria());
                    categorias.get(key).setNombre(key);
                }
            }

            nList = doc.getElementsByTagName("producto");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    String key = nNode.getAttributes().getNamedItem("ID").getNodeValue();
                    int keyID = Integer.parseInt(key);
                    String keyCategoria = nNode.getParentNode().getParentNode().getAttributes().getNamedItem("nombre").getNodeValue();
                    categorias.get(keyCategoria).productos.put(keyID, new Producto());
                    categorias.get(keyCategoria).productos.get(keyID).setId(keyID);
                }
            }

            nList = doc.getElementsByTagName("nombre");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    String nodeNombre = nNode.getFirstChild().getNodeValue();
                    String keyCategoria = nNode.getParentNode().getParentNode().getParentNode().getAttributes().getNamedItem("nombre").getNodeValue();
                    String keyID = nNode.getParentNode().getAttributes().getNamedItem("ID").getNodeValue();
                    categorias.get(keyCategoria).productos.get(Integer.parseInt(keyID)).setNombre(nodeNombre);
                }
            }

            nList = doc.getElementsByTagName("precio");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    String nodePrecio = nNode.getFirstChild().getNodeValue();
                    String keyCategoria = nNode.getParentNode().getParentNode().getParentNode().getAttributes().getNamedItem("nombre").getNodeValue();
                    String keyID = nNode.getParentNode().getAttributes().getNamedItem("ID").getNodeValue();
                    categorias.get(keyCategoria).productos.get(Integer.parseInt(keyID)).setPrecio(Float.parseFloat(nodePrecio));
                    //tv.setText(tv.getText()+"\n"+keyCategoria+" - "+keyID+" - "+nodeNombre);
                }
            }

            nList = doc.getElementsByTagName("descripcion");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    String nodeDesc = nNode.getFirstChild().getNodeValue();
                    String keyCategoria = nNode.getParentNode().getParentNode().getParentNode().getAttributes().getNamedItem("nombre").getNodeValue();
                    String keyID = nNode.getParentNode().getAttributes().getNamedItem("ID").getNodeValue();
                    categorias.get(keyCategoria).productos.get(Integer.parseInt(keyID)).setDescripcion(nodeDesc);
                    //tv.setText(tv.getText()+"\n"+keyCategoria+" - "+keyID+" - "+nodeNombre);
                }
            }

            nList = doc.getElementsByTagName("imagen");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    String nodeImagen = nNode.getFirstChild().getNodeValue();
                    String keyCategoria = nNode.getParentNode().getParentNode().getParentNode().getAttributes().getNamedItem("nombre").getNodeValue();
                    String keyID = nNode.getParentNode().getAttributes().getNamedItem("ID").getNodeValue();
                    categorias.get(keyCategoria).productos.get(Integer.parseInt(keyID)).setRutaImagen(nodeImagen);
                    //tv.setText(tv.getText()+"\n"+keyCategoria+" - "+keyID+" - "+nodeNombre);
                }
            }

            for (String key : categorias.keySet()){
                ListCategorias.add(key);
            }
            //Toast.makeText(MainActivity.this, "el numero de categorias:  "+nList.getLength(), Toast.LENGTH_LONG).show();
            //mostrar en la llista
            lv = (ListView) findViewById(R.id.ListVCategorias);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    ListCategorias );

            lv.setAdapter(arrayAdapter);

            /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(context, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                    categoriaSeleccionada = parent.getItemAtPosition(position).toString();
                    Intent intent = new Intent(MainActivity.this, MostrarProductos.class);
                    startActivity(intent);
                }
            });*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}