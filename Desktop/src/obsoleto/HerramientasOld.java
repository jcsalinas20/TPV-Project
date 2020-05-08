package obsoleto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class HerramientasOld {

	static String getArchivoDef(String aux) {
		return getArchivo(Integer.parseInt(getNumMesa(aux)));
	}

	static String getNumMesa(String aux) {
		return aux.substring(aux.length() - 1).equals(".") ? "" : aux.substring(aux.length() - 1);
	}

	static String getArchivo(int numMesa) {
		try {
			return Files.walk(Paths.get(System.getProperty("user.dir"))).filter(Files::isRegularFile)
					.map(x -> x.getFileName().toString())
					.filter(x -> x.matches("comanda" + String.valueOf(numMesa) + ".xml")).findFirst().get();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "Herramientas IOException";
		}
	}

	static void actualizarComanda(File comanda, String nombreProducto, String cantidad, String precio) {
		if (comanda.getPath().equals(" "))
			return; // Control de inputs erroneos

		if (prodExistsXML(comanda, nombreProducto)) {
			modificarXML(comanda, nombreProducto, cantidad);
		} else {

			try {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(comanda);

				Node nNode = doc.getDocumentElement();

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					// Crear nodo de camarero y buscarme la vida para sobreescribir el fichero

					Element producte = doc.createElement("producte");

					Element nomProducto = doc.createElement("nombreProducto");
					nomProducto.appendChild(doc.createTextNode(nombreProducto));
					producte.appendChild(nomProducto);

					Element cantidadTemp = doc.createElement("cantidad");
					cantidadTemp.appendChild(doc.createTextNode(cantidad));
					producte.appendChild(cantidadTemp);

					Element precioTemp = doc.createElement("precio");
					precioTemp.appendChild(doc.createTextNode(precio));
					producte.appendChild(precioTemp);

					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					DOMSource source = new DOMSource(doc);
					StreamResult result = new StreamResult(comanda);

					nNode.appendChild(producte);

					try {
						transformer.transform(source, result);
					} catch (TransformerException e) {
						// TODO Auto-generated catch block
						System.out.println("Error Transform Herramientas");
					}

				}

			} catch (SAXException e) {
				// TODO Auto-generated catch block
				System.out.println("SAXException Herramientas - actualizarComanda: " + e.getMessage());
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				System.out.println("ParserConfigurationException Herramientas - actualizarComanda: " + e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("IOException Herramientas - actualizarComanda: " + e.getMessage());
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				System.out.println(
						"TransformerConfigurationException Herramientas - actualizarComanda: " + e.getMessage());
			}
		}
	}

	static boolean prodExistsXML(File comanda, String nomProd) { // Funcion para saber si el producto existe en el XML,
																	// de esa
		// manera sabemos si lo tenemos que añadir o si tenemos que
		// cambiar el valor
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(comanda);

			NodeList nl = doc.getElementsByTagName("producte");

			for (int i = 0; i < nl.getLength(); i++) {
				Node nNode = nl.item(i);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) nNode;
					if (e.getElementsByTagName("nombreProducto").item(0).getTextContent().contentEquals(nomProd))
						return true;
				}
			}
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;

	}

	static void modificarXML(File comanda, String nomProd, String cantidad) { // metodo para modificar cantidadXML
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(comanda);

			NodeList nl = doc.getElementsByTagName("producte");

			for (int i = 0; i < nl.getLength(); i++) {
				Node nNode = nl.item(i);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) nNode;
					if (e.getElementsByTagName("nombreProducto").item(0).getTextContent().contentEquals(nomProd)) {
						e.getElementsByTagName("cantidad").item(0).setTextContent(cantidad);
					}
				}
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(comanda);

			System.out.println(getFechaCreacion(comanda));

			transformer.transform(source, result);
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void eliminarDelXML(File comanda, String nomProd) { // Metodo para eliminar producto del XML, la estructura
																// de es la misma que el de modificar pero en vez de
																// modificar, se borra el nodo
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(comanda);

			Node root = doc.getDocumentElement();

			NodeList nl = doc.getElementsByTagName("producte");

			for (int i = 0; i < nl.getLength(); i++) {
				Node nNode = nl.item(i);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) nNode;
					if (e.getElementsByTagName("nombreProducto").item(0).getTextContent().contentEquals(nomProd)) {
						root.removeChild(nNode);
					}
				}
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(comanda);

			transformer.transform(source, result);
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static boolean creaFactura(File comanda) {
		return true;
	}

	static String getFechaCreacion(File comanda) {
		try {
			return Files.readAttributes(comanda.toPath(), BasicFileAttributes.class).creationTime().toInstant()
					.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy/MM/dd - hh:mm:ss"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return "No trobada";
		}
	}

	static String getFechaCreacionID(File comanda) {
		try {
			return Files.readAttributes(comanda.toPath(), BasicFileAttributes.class).creationTime().toInstant()
					.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MMddhhmm"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return "No trobada";
		}
	}

	/*
	 * static String getCategorias() { String result = ""; try {
	 * DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	 * DocumentBuilder dBuilder = dbFactory.newDocumentBuilder(); Document doc =
	 * dBuilder.parse(new File("productes.xml"));
	 * 
	 * doc.getDocumentElement().normalize();
	 * 
	 * NodeList nl = doc.getElementsByTagName("categoria"); // Recogemos todas las
	 * categorias
	 * 
	 * for (int i = 0; i < nl.getLength(); i++) { Node nNode = nl.item(i);
	 * 
	 * if (nNode.getNodeType() == Node.ELEMENT_NODE) { Element e = (Element) nNode;
	 * 
	 * result += "-" + e.getAttributeNode("nombre").getTextContent();
	 * 
	 * } }
	 * 
	 * } catch (SAXException e) { // TODO Auto-generated catch block
	 * System.out.println("Error ModeloBarra SAX: " + e.getMessage()); } catch
	 * (IOException e) { // TODO Auto-generated catch block
	 * System.out.println("Error ModeloBarra IO: " + e.getMessage()); } catch
	 * (ParserConfigurationException e) { // TODO Auto-generated catch block
	 * System.out.println("Error ModeloBarra ParserConf: " + e.getMessage()); }
	 * return result.substring(1); }
	 * 
	 * static String getProds(String cat) { String result = "";
	 * 
	 * try { DocumentBuilderFactory dbFactory =
	 * DocumentBuilderFactory.newInstance(); DocumentBuilder dBuilder =
	 * dbFactory.newDocumentBuilder(); Document doc = dBuilder.parse(new
	 * File("productes.xml"));
	 * 
	 * doc.getDocumentElement().normalize();
	 * 
	 * NodeList nl = doc.getElementsByTagName("categoria"); // Recogemos todas las
	 * categorias
	 * 
	 * for (int i = 0; i < nl.getLength(); i++) { Node nNode = nl.item(i);
	 * 
	 * if (nNode.getNodeType() == Node.ELEMENT_NODE) { Element e = (Element) nNode;
	 * 
	 * if (e.getAttributeNode("nombre").getTextContent().equals(cat)) { // Si la
	 * categoria es la que // necesitamos, hacemos otro for NodeList nlaux =
	 * e.getElementsByTagName("producto");
	 * 
	 * for (int q = 0; q < nlaux.getLength(); q++) { // Por cada producto se a�ade
	 * un boton con su // listener Node nNodeaux = nlaux.item(q);
	 * 
	 * if (nNode.getNodeType() == Node.ELEMENT_NODE) { Element ee = (Element)
	 * nNodeaux;
	 * 
	 * result += "-" + ee.getElementsByTagName("nombre").item(0).getTextContent();
	 * 
	 * } } }
	 * 
	 * } } } catch (SAXException e) { // TODO Auto-generated catch block
	 * System.out.println("Error ModeloBarra SAX: " + e.getMessage()); } catch
	 * (IOException e) { // TODO Auto-generated catch block
	 * System.out.println("Error ModeloBarra IO: " + e.getMessage()); } catch
	 * (ParserConfigurationException e) { // TODO Auto-generated catch block
	 * System.out.println("Error ModeloBarra ParserConf: " + e.getMessage()); }
	 * 
	 * return result.substring(1); }
	 * 
	 */

	static String getPreuProd(String prod) {

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File("productes.xml"));

			doc.getDocumentElement().normalize();

			NodeList nl = doc.getElementsByTagName("producto"); // Recogemos todos los productos

			for (int i = 0; i < nl.getLength(); i++) {
				Node nNode = nl.item(i);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) nNode;
					if (e.getElementsByTagName("nombre").item(0).getTextContent().contentEquals(prod)) {
						return e.getElementsByTagName("precio").item(0).getTextContent();
					}
				}

			}

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			System.out.println("Error ModeloBarra SAX: " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error ModeloBarra IO: " + e.getMessage());
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			System.out.println("Error ModeloBarra ParserConf: " + e.getMessage());
		}
		return "rip";
	}

	static String getCantitatXML(File comanda, String prod) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(comanda);

			doc.getDocumentElement().normalize();

			NodeList nl = doc.getElementsByTagName("producte"); // Recogemos todos los productos

			for (int i = 0; i < nl.getLength(); i++) {
				Node nNode = nl.item(i);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) nNode;
					if (e.getElementsByTagName("nombreProducto").item(0).getTextContent().contentEquals(prod)) {
						return e.getElementsByTagName("cantidad").item(0).getTextContent();
					}
				}

			}

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			System.out.println("Error ModeloBarra SAX: " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error ModeloBarra IO: " + e.getMessage());
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			System.out.println("Error ModeloBarra ParserConf: " + e.getMessage());
		}
		return "0";
	}

	/*
	 * 
	 * static void cobrar(File comanda, String total) {
	 * 
	 * emmagatzemarCommanda(comanda);
	 * 
	 * emmagatzemarFactura(comanda, (Float.parseFloat(total.replace(",", ".")) *
	 * 1.21f)); }
	 * 
	 * static void emmagatzemarFactura(File comanda, Float total) { try { Connection
	 * conn = getConn();
	 * 
	 * PreparedStatement stmnt = conn.prepareStatement(
	 * "INSERT INTO `Facturas` (`Facturas_id`, `Comandas_id`, `Precio_total`) VALUES (NULL, ?, ?) "
	 * );
	 * 
	 * stmnt.setInt(1, Integer.parseInt( comanda.getName().substring(7,
	 * comanda.getName().length() - 4) + getFechaCreacionID(comanda)));
	 * stmnt.setFloat(2, total);
	 * 
	 * if (stmnt.execute()) System.out.println("yayyyyy"); ;
	 * 
	 * } catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * }
	 * 
	 * 
	 * 
	 * static void emmagatzemarCommanda(File comanda) { try { DocumentBuilderFactory
	 * dbFactory = DocumentBuilderFactory.newInstance(); DocumentBuilder dBuilder =
	 * dbFactory.newDocumentBuilder(); Document doc = dBuilder.parse(comanda);
	 * 
	 * NodeList nl = doc.getElementsByTagName("producte");
	 * 
	 * for (int i = 0; i < nl.getLength(); i++) { Node nNode = nl.item(i);
	 * 
	 * if (nNode.getNodeType() == Node.ELEMENT_NODE) { Element e = (Element) nNode;
	 * Connection conn = getConn();
	 * 
	 * PreparedStatement stmnt = conn.prepareStatement(
	 * "INSERT INTO `Comandas` (`Comandas_id`, `Producto`, `Cantidad`, `Precio`) VALUES (?, ?, ?, ?) "
	 * );
	 * 
	 * stmnt.setInt(1, Integer.parseInt(comanda.getName().substring(7,
	 * comanda.getName().length() - 4) + getFechaCreacionID(comanda)));
	 * stmnt.setString(2,
	 * e.getElementsByTagName("nombreProducto").item(0).getTextContent());
	 * stmnt.setInt(3,
	 * Integer.parseInt(e.getElementsByTagName("cantidad").item(0).getTextContent())
	 * ); stmnt.setFloat(4, Float
	 * .parseFloat(e.getElementsByTagName("precio").item(0).getTextContent().replace
	 * (",", ".")));
	 * 
	 * if (stmnt.execute()) System.out.println("we are the champions");
	 * 
	 * } } } catch (SAXException | IOException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } catch (ParserConfigurationException e1) { //
	 * TODO Auto-generated catch block e1.printStackTrace(); } catch (SQLException
	 * e1) { // TODO Auto-generated catch block e1.printStackTrace(); } }
	 */
}
