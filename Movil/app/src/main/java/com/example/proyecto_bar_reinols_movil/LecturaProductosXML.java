package com.example.proyecto_bar_reinols_movil;

import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class LecturaProductosXML {

    // metodo para leer las categorias del archivo producto
    public static void lecturaCategorias(Context context) throws ParserConfigurationException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(context.getFilesDir(), "productes.xml"));
            NodeList nList = doc.getElementsByTagName("categoria");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    String key = nNode.getAttributes().getNamedItem("nombre").getNodeValue();
                    MostrarCategorias.categorias.put(key, new Categoria());
                    MostrarCategorias.categorias.get(key).setNombre(key);
                }
            }
        }catch (SAXException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // metodo para leer las categorias del archivo producto
    public static void lecturaImagenCategorias(Context context) throws ParserConfigurationException {
        Categoria.ITEMS = new Categoria[MostrarCategorias.categorias.size()];
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(context.getFilesDir(), "productes.xml"));
            NodeList nList = doc.getElementsByTagName("imagen-categoria");
            int contador = 0;
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    byte[] imagen = nNode.getTextContent().getBytes();
                    String keyCategoria = nNode.getParentNode().getAttributes().getNamedItem("nombre").getNodeValue();
                    MostrarCategorias.categorias.get(keyCategoria).setImagen(imagen);
                    MostrarCategorias.agregarCategorias(contador, keyCategoria, ConexionServer.categoriasImg.get(temp));
                    contador++;
                }
            }
        }catch (SAXException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // metodo para leer los IDs del archivo producto
    public static void lecturaIdProductos(Context context) throws ParserConfigurationException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(context.getFilesDir(), "productes.xml"));
            NodeList nList = doc.getElementsByTagName("producto");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    String key = nNode.getAttributes().getNamedItem("ID").getNodeValue();
                    int keyID = Integer.parseInt(key);
                    String keyCategoria = nNode.getParentNode().getParentNode().getAttributes().getNamedItem("nombre").getNodeValue();
                    MostrarCategorias.categorias.get(keyCategoria).productos.put(keyID, new Producto());
                    MostrarCategorias.categorias.get(keyCategoria).productos.get(keyID).setId(keyID);
                }
            }
        }catch (SAXException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // metodo para leer los nombres del archivo producto
    public static void lecturaNombreProductos(Context context) throws ParserConfigurationException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(context.getFilesDir(), "productes.xml"));
            NodeList nList = doc.getElementsByTagName("nombre");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    String nodeNombre = nNode.getFirstChild().getNodeValue();
                    String keyCategoria = nNode.getParentNode().getParentNode().getParentNode().getAttributes().getNamedItem("nombre").getNodeValue();
                    String keyID = nNode.getParentNode().getAttributes().getNamedItem("ID").getNodeValue();
                    MostrarCategorias.categorias.get(keyCategoria).productos.get(Integer.parseInt(keyID)).setNombre(nodeNombre);
                }
            }
        }catch (SAXException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // metodo para leer las descripciones del archivo producto
    /*public static void lecturaDescripcionProductos(Context context) throws ParserConfigurationException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(context.getResources().openRawResource(R.raw.productes));
            NodeList nList = doc.getElementsByTagName("descripcion");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    String nodeDesc = nNode.getFirstChild().getNodeValue();
                    String keyCategoria = nNode.getParentNode().getParentNode().getParentNode().getAttributes().getNamedItem("nombre").getNodeValue();
                    String keyID = nNode.getParentNode().getAttributes().getNamedItem("ID").getNodeValue();
                    MostrarCategorias.categorias.get(keyCategoria).productos.get(Integer.parseInt(keyID)).setDescripcion(nodeDesc);
                }
            }
        }catch (SAXException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }*/

    // metodo para leer los precios del archivo producto
    public static void lecturaPrecioProductos(Context context) throws ParserConfigurationException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(context.getFilesDir(), "productes.xml"));
            NodeList nList = doc.getElementsByTagName("precio");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    String nodePrecio = nNode.getFirstChild().getNodeValue();
                    String keyCategoria = nNode.getParentNode().getParentNode().getParentNode().getAttributes().getNamedItem("nombre").getNodeValue();
                    String keyID = nNode.getParentNode().getAttributes().getNamedItem("ID").getNodeValue();
                    MostrarCategorias.categorias.get(keyCategoria).productos.get(Integer.parseInt(keyID)).setPrecio(Float.parseFloat(nodePrecio));
                }
            }
        }catch (SAXException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // metodo para leer la ruta de la imagen del archivo producto
    public static void lecturaImagenProductos(Context context) throws ParserConfigurationException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(context.getFilesDir(), "productes.xml"));
            NodeList nList = doc.getElementsByTagName("imagen");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    String nodeImagen = nNode.getFirstChild().getNodeValue();
                    String keyCategoria = nNode.getParentNode().getParentNode().getParentNode().getAttributes().getNamedItem("nombre").getNodeValue();
                    String keyID = nNode.getParentNode().getAttributes().getNamedItem("ID").getNodeValue();
                    System.out.println("BYTES: " + ConexionServer.productosImg.size());
                    MostrarCategorias.categorias.get(keyCategoria).productos.get(Integer.parseInt(keyID)).setRutaImagen(ConexionServer.productosImg.get(temp));
                }
            }
        }catch (SAXException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void actualizarProductosXML(ArrayList<String> productos, Context mContext){
        ArrayList<String> nombreCategorias = new ArrayList<>();
        ArrayList<String> idProducto = new ArrayList<>();
        ArrayList<String> nombreProductos = new ArrayList<>();
        ArrayList<String> precios = new ArrayList<>();
        ArrayList<byte[]> URLimagen = ConexionServer.productosImg;
        ArrayList<byte[]> URLimagenCat = ConexionServer.categoriasImg;

        for (int i = 0; i < productos.size(); i++) {
            String[] producto = productos.get(i).split("-");
            nombreCategorias.add(producto[0]);
            idProducto.add(producto[1]);
            nombreProductos.add(producto[2]);
            precios.add(producto[3]);
        }

        ArrayList<Integer> contador = new ArrayList<>();
        ArrayList<String> nomCategorias = new ArrayList<>();
        ArrayList<String> imgCategorias = new ArrayList<>();
        String categoriaSeleccionada = nombreCategorias.get(0);
        nomCategorias.add(nombreCategorias.get(0));
        imgCategorias.add(URLimagenCat.get(0).toString());
        int cont = 0;
        int siguienteImagen = 1;
        for (int i = 0; i < nombreCategorias.size(); i++) {
            if (categoriaSeleccionada.equals(nombreCategorias.get(i))) {
                cont++;
            } else {
                System.out.println("SELECCIONADA: " + categoriaSeleccionada);
                categoriaSeleccionada = nombreCategorias.get(i);
                nomCategorias.add(nombreCategorias.get(i));
                imgCategorias.add(URLimagenCat.get(siguienteImagen).toString());
                contador.add(cont);
                cont = 1;
                siguienteImagen++;
            }
            if (i == nombreCategorias.size() - 1) {
                contador.add(cont);
            }
        }

        try {
            int contadorProducto = 0;
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            // root element
            Element rootElement = doc.createElement("bar_reynols");
            doc.appendChild(rootElement);

            Element categorias = doc.createElement("categorias");
            rootElement.appendChild(categorias);

            for (int i = 0; i < contador.size(); i++) {
                Element categoria = doc.createElement("categoria");
                categoria.setAttribute("nombre", nomCategorias.get(i));
                categorias.appendChild(categoria);

                Element imgCategoria = doc.createElement("imagen-categoria");
                imgCategoria.setTextContent(imgCategorias.get(i));
                categoria.appendChild(imgCategoria);

                Element listaPproductos = doc.createElement("productos");
                categoria.appendChild(listaPproductos);
                for (int j = 0; j < contador.get(i); j++) {
                    Element producto =  doc.createElement("producto");
                    producto.setAttribute("ID", idProducto.get(contadorProducto));
                    listaPproductos.appendChild(producto);

                    Element nombre = doc.createElement("nombre");
                    nombre.setTextContent(nombreProductos.get(contadorProducto));
                    producto.appendChild(nombre);

                    Element precio = doc.createElement("precio");
                    precio.setTextContent(precios.get(contadorProducto));
                    producto.appendChild(precio);

                    Element imagen = doc.createElement("imagen");
                    imagen.setTextContent(URLimagen.get(contadorProducto).toString());
                    producto.appendChild(imagen);

                    contadorProducto++;
                }
            }


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(mContext.getFilesDir(), "productes.xml"));
            transformer.transform(source, result);

        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
