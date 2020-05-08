package obsoleto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.example.proyecto_bar_reinols_movil.ConfTool;
import com.example.proyecto_bar_reinols_movil.Herramientas;
import com.example.proyecto_bar_reinols_movil.VentanaPrincipal;

public class ModeloBarraOld {

	/*
	 * void cargarComanda(String numMesa) {

		if (!hm.get(numMesa)) { // Si la mesa ha sido cargada una vez, no se añade el producto al volver a darle
								// a la mesa
			try {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(new File("comanda" + numMesa + ".xml"));

				doc.getDocumentElement().normalize();

				NodeList nl = doc.getElementsByTagName("producte"); // Recogemos todos los productos

				for (int i = 0; i < nl.getLength(); i++) {
					Node nNode = nl.item(i);

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element e = (Element) nNode;

						añadirProductoDelXML(getComandaActual(),
								e.getElementsByTagName("nombreProducto").item(0).getTextContent());
					}
				}
				if (!hm.get(numMesa))
					hm.put(numMesa, true);

			} catch (Exception e) {
				try {
					new File("comanda" + numMesa + ".xml").createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("Error");
				}
			}
			updateTotal();
		}

	}

	void borrarFilasSel() { // Metodo para borrar seleciones
		// Uso de stream para borrar las selecciones

		IntStream.of(tableComanda.getSelectedRows()).boxed().sorted(Collections.reverseOrder()).forEach(
				x -> Herramientas.eliminarDelXML(getComandaActual(), tableComanda.getValueAt(x, 0).toString()));

		IntStream.of(tableComanda.getSelectedRows()).boxed().sorted(Collections.reverseOrder())
				.forEach(x -> dtm.removeRow(x));

	}

	void calculaDevolver() { // Metodo para cambiar el lbl de Devolver, se resta el total - lo pagado.
		lblDevolverNum.setText(df.format(
				Double.parseDouble(tfPagado.getText()) - Double.parseDouble(lblTotalNum.getText().replace(",", "."))));
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

	File getComandaActual() { // Llama al metodo getArchivoDef que devuelve el nombre de la comanda, el
								// substring se basa en coger el num de la mesa teniendo en cuenta que "mesa:
								// "ya son 6 caracteres
		return new File(Herramientas.getArchivoDef(lblMesa.getText().substring(6)));
	}

	void abrirCuina(ActionEvent e) throws PropertyVetoException {
		// Codigo para abrir nueva ventana

		if (VentanaPrincipal.tabbedPane.indexOfTab(((JButton) e.getSource())
				.getText()) > -1) { 
									 * Condicion para verificar si la mesa que seleccionada ya existe, en ese caso
									 * abre la pesta�a correspondiente, en caso de que no exista, la crea y la abre
									 * (else)
									 
			VentanaPrincipal.tabbedPane
					.setSelectedIndex(VentanaPrincipal.tabbedPane.indexOfTab((((JButton) e.getSource()).getText())));
		} else {
			VentanaPrincipal.tabbedPane.addTab(((JButton) e.getSource()).getText(),
					new Cuina(getComandaActual(),((JButton) e.getSource()).getText()));
			VentanaPrincipal.tabbedPane
					.setSelectedIndex(VentanaPrincipal.tabbedPane.indexOfTab((((JButton) e.getSource()).getText())));
		}
	}
	 	void añadirProducto(String prod) { // Metodo para a�adir producto a la jtable

		if (Herramientas.prodExistsXML(getComandaActual(), prod)) {
			System.out.println(Herramientas.getCantitatXML(getComandaActual(), prod) + "-------" + 1);

			if (!hm.get(Herramientas.getNumMesa(lblMesa.getText())))
				añadirProductoDelXML(getComandaActual(), prod);

			else {
				añadirProductoSumaDelXML(getComandaActual(), prod);

				Herramientas.actualizarComanda(getComandaActual(), prod,
						String.valueOf(Integer.parseInt(Herramientas.getCantitatXML(getComandaActual(), prod)) + 1),
						Herramientas.getPreuProd(prod));
			}
		} else {
			dtm.addRow(new String[] { prod, "1", Herramientas.getPreuProd(prod) });
			System.out.println(Herramientas.getCantitatXML(getComandaActual(), prod) + "-------" + 2);

//			Herramientas.cobrar(getComandaActual(), lblTotalNum.getText());

			if (hm.get(Herramientas.getNumMesa(lblMesa.getText())))
				Herramientas.actualizarComanda((getComandaActual()), prod, "1", Herramientas.getPreuProd(prod));
		}

		updateTotal();
	}

	void añadirProductoDelXML(File comanda, String prod) {
		dtm.addRow(new String[] { prod, Herramientas.getCantitatXML(comanda, prod), Herramientas.getPreuProd(prod) });
		tableComanda.setModel(dtm);
	}

	void añadirProductoSumaDelXML(File comanda, String prod) {
		int aux = IntStream.range(0, dtm.getRowCount())
				.filter(x -> ((String) tableComanda.getValueAt(x, 0)).equals(prod)).sum();
		dtm.removeRow(aux);

		System.out.println(Herramientas.getCantitatXML(getComandaActual(), prod) + "-------" + 3);

		dtm.addRow(new String[] { prod,
				String.valueOf(Integer.parseInt(Herramientas.getCantitatXML(getComandaActual(), prod)) + 1),
				Herramientas.getPreuProd(prod) });
		tableComanda.setModel(dtm);
	}

	void updateTotal() { // Metodo para cambiar el lbl del Total

		 * Este Stream recoge los valores de la columna 2 de todas las filas y lo
		 * multiplica por la cantidad (columna 1 de todas las filas), una vez recogidos
		 * todos los precios por la cantidad, suma el total

		double aux = IntStream.range(0, tableComanda.getRowCount())
				.mapToDouble(x -> (Double.parseDouble((String) tableComanda.getValueAt(x, 2))
						* Integer.parseInt((String) tableComanda.getValueAt(x, 1))))
				.sum();

		lblTotalNum.setText(df.format(aux));
	}

	*/
}
