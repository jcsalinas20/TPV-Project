package com.example.proyecto_bar_reinols_movil;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.ConnectException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ConexionServer extends Activity {

    Context mContext;
    public static String IP_Server;
    public String queConsultaHacer;
    public static ArrayList<byte[]> categoriasImg;
    public static ArrayList<byte[]> productosImg;

    public void task(Context context) {
        mContext = context;

        lecturaIP(mContext);// metodo para guardar la ip del archivo a la variable
        //lecturaNombrePDA(mContext);// metodo para guardar el nombre del archivo a la variable

        Conn conn = new Conn();
        conn.execute();// comenzar conexion

    }

    class Conn extends AsyncTask<Void, Void, ConexionServer> {

        @Override
        protected ConexionServer doInBackground(Void... params) {
            try {
                CallHandler callHandler = new CallHandler();
                Client client = new Client(IP_Server, 5000, callHandler);
                TestService testService = (TestService) client.getGlobal(TestService.class);
                if ("camareros".equals(queConsultaHacer)) {
                    MainActivity.nombreCamareros = testService.getCamareros("Consulta Camareros");
                    MainActivity.imagenCamareros = testService.getImagenCamareros();
                } else if ("num_mesas".equals(queConsultaHacer)) {
                    MainActivity.numMaxMesas = testService.getNumMesas("Consulta Numero de mesas");
                } else if ("categorias".equals(queConsultaHacer)) {
                    ArrayList<String> productos = testService.getCategorias("Consulta Categorias y Productos");
                    LecturaProductosXML.actualizarProductosXML(productos, mContext);
                } else if ("enviar_productos".equals(queConsultaHacer)) {
                    ArrayList<String> productos = conseguirProductos();
                    testService.envioDeProductos("Productos reibidos de la mesa " + MostrarMesas.mesaSeleccionada, Login.camareroSeleccionado, MostrarMesas.mesaSeleccionada, productos);
                } else if ("comanda_mesa".equals(queConsultaHacer)) {
                    ArrayList<String> comandaMesa = testService.getComandaMesa("Coger comanda mesa " + MostrarMesas.mesaSeleccionada, MostrarMesas.mesaSeleccionada);
                    actualizarComanda(comandaMesa);
                } else if ("guardar_imagen".equals(queConsultaHacer)) {
                    testService.guardarImagen("Guardar imagen", Registrarse.byteImage, Registrarse.nombre, Registrarse.password);
                } else if ("byte_cate".equals(queConsultaHacer)) {
                    categoriasImg = testService.imagenesCate();
                } else if ("byte_prod".equals(queConsultaHacer)) {
                    productosImg = testService.imagenesProd();
                }
                client.close();
            } catch (ConnectException e) {
                System.err.println("ERROR DE CONEXION CON EL SERVER.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    private void actualizarComanda(ArrayList<String> comandaMesa) {
        MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos.clear();
        for (int i = 0; i < comandaMesa.size(); i++) {
            String[] valores = comandaMesa.get(i).split("-");
            MostrarMesas.mesasComanda.get(MostrarMesas.mesaSeleccionada).listaProductos.add(new Comanda(valores[0], Float.parseFloat(valores[1]), Integer.parseInt(valores[2])));
        }
    }

    private ArrayList<String> conseguirProductos() {
        int index = MostrarMesas.mesaSeleccionada;
        ArrayList<String> productos = new ArrayList<>();
        for (int i = 0; i < MostrarMesas.mesasComanda.get(index).listaProductos.size(); i++) {
            if (MostrarMesas.mesasComanda.get(index).listaProductos.size() == 1 && MostrarMesas.mesasComanda.get(index).listaProductos.get(i).getCantidad() == 0){
                break;
            }
            String nombre = MostrarMesas.mesasComanda.get(index).listaProductos.get(i).getNombreProducto();
            String precio = String.valueOf(MostrarMesas.mesasComanda.get(index).listaProductos.get(i).getPrecio());
            String cantidad = String.valueOf(MostrarMesas.mesasComanda.get(index).listaProductos.get(i).getCantidad());

            productos.add(nombre + "-" +precio + "-" + cantidad);
        }
        return productos;
    }

    // metodo para sacar la IP del servidor
    public void lecturaIP(Context context){
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(context.getResources().openRawResource(R.raw.config));
            NodeList nList = doc.getElementsByTagName("ip-server");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    IP_Server = nNode.getTextContent();
                }
            }
        }catch (SAXException e){
            e.printStackTrace();
        }catch (ParserConfigurationException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String leerArchivoComandaXML(Context context, int index){
        // arraylist donde guardar los valores de los productos
        ArrayList<String> pasarComanda = new ArrayList<>();
        try {
            String IDproductos = "";
            String nombreProductos = "";
            String cantidadProductos = "";
            String precioProductos = "";

            // sacar id de los productos
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(context.getFilesDir(), "comanda" + index + ".xml"));

            NodeList nList = doc.getElementsByTagName("producte");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    IDproductos += nNode.getAttributes().getNamedItem("id").getNodeValue() + "-";
                }
            }
            pasarComanda.add(IDproductos);

            // sacar nombre de los productos
            nList = doc.getElementsByTagName("nombreProducto");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    nombreProductos += nNode.getTextContent() + "-";
                }
            }
            pasarComanda.add(nombreProductos);

            // sacar precio de los productos
            nList = doc.getElementsByTagName("precio");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    precioProductos += nNode.getTextContent() + "-";
                }
            }
            pasarComanda.add(precioProductos);

            // sacar cantidad de los productos
            nList = doc.getElementsByTagName("cantidad");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    cantidadProductos += nNode.getTextContent() + "-";
                }
            }
            pasarComanda.add(cantidadProductos);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String comanda = "";
        // hacer split con guiones para separar
        String[] arrayIDs = pasarComanda.get(0).split("-");
        String[] arrayNombres = pasarComanda.get(1).split("-");
        String[] arrayPrecios = pasarComanda.get(2).split("-");
        String[] arrayCantidad = pasarComanda.get(3).split("-");

        // juntar los productos con sus valores
        for (int i = 0; i < arrayIDs.length; i++) {
            comanda += arrayIDs[i] + "-" + arrayNombres[i] + "-" + arrayPrecios[i] + "-" + arrayCantidad[i] + "|";
        }
        return comanda;
    }

}

interface TestService extends Serializable {

    public ArrayList<String> getCamareros(String data);

    public ArrayList<byte[]> getImagenCamareros();

    public int getNumMesas(String data);

    public void envioDeProductos(String data, String camarero, int mesa, ArrayList<String> productos);

    public ArrayList<String> getCategorias(String data);

    public ArrayList<String> getComandaMesa(String data, int num_mesa);

    public ArrayList<byte[]> imagenesCate();

    public ArrayList<byte[]> imagenesProd();

    public void guardarImagen(String data, byte[] byteImage, String nombre, String password);

}