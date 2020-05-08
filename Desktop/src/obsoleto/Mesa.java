package obsoleto;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.example.proyecto_bar_reinols_movil.ConfTool;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Mesa extends JInternalFrame implements ActionListener {

	/**
	 * Launch the application.
	 */

	JPanel panelCategorias;
	JButton jbCat;
	JButton jbProd;
	JPanel panelProds;
	ArrayList<JButton> arrJBCat;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mesa frame = new Mesa();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws PropertyVetoException
	 */
	public Mesa() throws PropertyVetoException {
		setMaximum(true);
		setResizable(true);
		setBounds(100, 100, 1228, 673);
		setVisible(true);

		panelCategorias = new JPanel();

		panelProds = new JPanel();

		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(panelProds, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(panelCategorias, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 713,
										Short.MAX_VALUE))
						.addGap(30).addComponent(panel, GroupLayout.PREFERRED_SIZE, 432, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(33, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(panelCategorias, GroupLayout.PREFERRED_SIZE, 266, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 295, GroupLayout.PREFERRED_SIZE)
								.addComponent(panelProds, GroupLayout.PREFERRED_SIZE, 296, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(57, Short.MAX_VALUE)));
		panel.setLayout(new GridLayout(ConfTool.getNumTaulesSQL() / 2, (ConfTool.getNumTaulesSQL() / 2) + 1, 0, 0));
		panelProds.setLayout(new GridLayout(4, 4, 0, 0));
		panelCategorias.setLayout(new GridLayout(4, 4, 0, 0));
		getContentPane().setLayout(groupLayout);

		llenarCat();

	}

	void llenarCat() { // Metodo para a�adir los botones a la botonera de las categorias

		try {

			jbCat = new JButton("Volver a Mesas");
			jbCat.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
//					VentanaPrincipal.tabbedPane.setSelectedIndex(0);
				}

			});
			panelCategorias.add(jbCat);

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File("productes.xml"));

			doc.getDocumentElement().normalize();

			NodeList nl = doc.getElementsByTagName("categoria");

			for (int i = 0; i < nl.getLength(); i++) {
				Node nNode = nl.item(i);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) nNode;

					jbCat = new JButton(e.getAttributeNode("nombre").getTextContent());
					jbCat.addActionListener(this);
					panelCategorias.add(jbCat);

				}
			}

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			System.out.println("Error Mesa SAX: " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error Mesa IO: " + e.getMessage());
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			System.out.println("Error Mesa ParserConf: " + e.getMessage());
		}

	}

	void llenarProd(String cat) {
		try {

			jbProd = new JButton("+");
			panelProds.add(jbProd);

			jbProd = new JButton("-");
			panelProds.add(jbProd);

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File("productes.xml"));

			doc.getDocumentElement().normalize();

			NodeList nl = doc.getElementsByTagName("categoria");

			for (int i = 0; i < nl.getLength(); i++) {
				Node nNode = nl.item(i);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) nNode;

					if (e.getAttributeNode("nombre").getTextContent().equals(cat)) {
						NodeList nlaux = e.getElementsByTagName("producto");

						for (int q = 0; q < nlaux.getLength(); q++) {
							Node nNodeaux = nlaux.item(q);

							if (nNode.getNodeType() == Node.ELEMENT_NODE) {
								Element ee = (Element) nNodeaux;

								jbProd = new JButton(ee.getElementsByTagName("nombre").item(0).getTextContent());

								panelProds.add(jbProd);

							}
						}
					}

				}
			}

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			System.out.println("Error Mesa SAX: " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error Mesa IO: " + e.getMessage());
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			System.out.println("Error Mesa ParserConf: " + e.getMessage());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		panelProds.removeAll();
		llenarProd(((JButton) e.getSource()).getText());
		System.out.println(((JButton) e.getSource()).getText());
		panelProds.revalidate();
		panelProds.repaint();
	}
}
