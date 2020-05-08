package obsoleto;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.example.proyecto_bar_reinols_movil.Herramientas;
import com.example.proyecto_bar_reinols_movil.VentanaPrincipal;

public class CuinaOld {
/*
 * void cargarComanda(File comanda) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(comanda);

		doc.getDocumentElement().normalize();

		NodeList nl = doc.getElementsByTagName("producte"); // Recogemos todos los productos

		for (int i = 0; i < nl.getLength(); i++) {
			Node nNode = nl.item(i);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) nNode;

				añadirProductoDelXML(comanda, e.getElementsByTagName("nombreProducto").item(0).getTextContent());
			}
		}
	}

	void añadirProductoDelXML(File comanda, String prod) {
		dtm.addRow(new String[] { prod, Herramientas.getCantitatXML(comanda, prod), Herramientas.getPreuProd(prod) });
		tableCocinando.setModel(dtm);
	}

	void metaInfo(File f) { // Metodo que recoge informacion basica del archivo XML
		if (f.exists()) {
			lblHoraDeEntrada.setText("Hore de entrada: " + Herramientas.getFechaCreacion(f));
			try {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(f);

				doc.getDocumentElement().normalize();

				NodeList nl = doc.getElementsByTagName("camarero"); // Recogemos al camarero

				Node nNode = nl.item(0);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) nNode;

					lblCamarero.setText("Camarero: " + (e.getAttributeNode("nombre").getTextContent().isEmpty() ? "X"
							: e.getAttributeNode("nombre").getTextContent()));

				}

				VentanaPrincipal.tabbedPane.revalidate();
				VentanaPrincipal.tabbedPane.repaint();

			} catch (SAXException e) {
				// TODO Auto-generated catch block
				System.out.println("Archivo creado");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
 * */
}
