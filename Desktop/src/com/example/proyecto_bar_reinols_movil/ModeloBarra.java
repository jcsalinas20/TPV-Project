package com.example.proyecto_bar_reinols_movil;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.stream.IntStream;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;

public class ModeloBarra extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tableComanda;
	private JButton jbCat;
	private JButton jbProd;
	private DefaultTableModel dtm;
	static JTextField tfPagado;
	private JLabel lblTotalNum;
	private JLabel lblDevolverNum;
	private DecimalFormat df = new DecimalFormat("#.##");
	private JLabel lblCamarero;
	private JLabel lblHoraDeEntrada;
	private JLabel lblMesa;
	private JScrollPane scrollCatProd;
	private JPanel panelBotons;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModeloBarra frame = new ModeloBarra("testin");
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
	@SuppressWarnings("serial")

	/*
	 * #############################################################################
	 * FRAME Y FEATURES SENCILLAS
	 * #############################################################################
	 */

	public ModeloBarra(String title) {
		setBackground(Color.PINK);
		setBorder(null);
		setClosable(true);
		setResizable(true);
		setTitle(title);
		setBounds(100, 100, 956, 1058);
		setVisible(true);

		JScrollPane scrollPane = new JScrollPane();

		JLabel lblTotal = new JLabel("TOTAL:");
		lblTotal.setFont(new Font("Sitka Heading", Font.BOLD, 30));

		lblTotalNum = new JLabel("0");
		lblTotalNum.setBackground(Color.WHITE);

		JLabel lblPagado = new JLabel("PAGADO:");
		lblPagado.setFont(new Font("Sitka Heading", Font.BOLD, 30));

		tfPagado = new JTextField();
		tfPagado.setEditable(false);
		tfPagado.setColumns(10);
		
		tfPagado.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				new TecladoNumerico().setVisible(true);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});

		JLabel lblDevolver = new JLabel("A DEVOLVER:");
		lblDevolver.setFont(new Font("Sitka Heading", Font.BOLD, 30));

		lblDevolverNum = new JLabel("0");
		lblDevolverNum.setBackground(Color.WHITE);

		JButton btnCalcularDevolver = new JButton("Calcular devolver");
		btnCalcularDevolver.setForeground(Color.WHITE);
		btnCalcularDevolver.setFont(new Font("Arial", Font.PLAIN, 14));
		btnCalcularDevolver.setBackground(Color.DARK_GRAY);

		btnCalcularDevolver.addActionListener(new ActionListener() { // Listener del boton CalcularDevolver
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				calculaDevolver(); // Tiene metodo propio
			}
		});

		JPanel panelMesas = new JPanel();
		panelMesas.setBackground(Color.DARK_GRAY);

		lblCamarero = new JLabel("Camarero: ");

		lblHoraDeEntrada = new JLabel("Hora de entrada: ");

		lblMesa = new JLabel("Mesa: ");

		JButton btnCobrar = new JButton("Cobrar");
		btnCobrar.setForeground(Color.WHITE);
		btnCobrar.setFont(new Font("Arial", Font.PLAIN, 14));
		btnCobrar.setBackground(Color.DARK_GRAY);
		btnCobrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Herramientas.revisarPreparadoYServido(getIdComandaActual())) {
					Herramientas.cobrar(getIdComandaActual(),
							Float.parseFloat(lblTotalNum.getText().replace(",", ".")));
					limpiarTabla();
					lblTotalNum.setText("");
				} else {
					JOptionPane.showMessageDialog(null, "Aun hay productos por cocinar/servir", "Ojo",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		scrollCatProd = new JScrollPane();
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(19)
							.addComponent(lblCamarero, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
							.addGap(97)
							.addComponent(lblHoraDeEntrada, GroupLayout.PREFERRED_SIZE, 316, GroupLayout.PREFERRED_SIZE)
							.addGap(136)
							.addComponent(lblMesa))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(scrollCatProd)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(12)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(btnCobrar)
											.addGap(58)
											.addComponent(btnCalcularDevolver, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(lblTotalNum, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
												.addGroup(groupLayout.createSequentialGroup()
													.addGap(2)
													.addComponent(lblTotal, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(lblPagado)
												.addComponent(tfPagado, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)))
										.addComponent(lblDevolver)
										.addComponent(lblDevolverNum, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(18)
									.addComponent(panelMesas, GroupLayout.PREFERRED_SIZE, 282, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap(27, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblCamarero)
							.addComponent(lblHoraDeEntrada))
						.addComponent(lblMesa))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblTotal)
								.addComponent(lblPagado, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblTotalNum, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
								.addComponent(tfPagado, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnCobrar)
								.addComponent(btnCalcularDevolver))
							.addGap(18)
							.addComponent(lblDevolver, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblDevolverNum, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(panelMesas, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(scrollCatProd, GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE))
					.addContainerGap(337, Short.MAX_VALUE))
		);
		
		panelBotons = new JPanel();
		panelBotons.setBackground(Color.DARK_GRAY);
		scrollCatProd.setViewportView(panelBotons);
		panelBotons.setLayout(new GridLayout(0, 4, 0, 0));

		try {
			llenarMesas(panelMesas);
			panelMesas.setLayout(new GridLayout(0, 2, 0, 0));
			llenarCat(); // Ejecutamos el metodo para llenar la botonera de las categorias

		} catch (PropertyVetoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		tableComanda = new JTable();
		tableComanda.setBackground(Color.WHITE);
		dtm = new DefaultTableModel(new Object[][] {}, new String[] { "Producto", "Cantidad", "Precio" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		}; // Declaracion de la tabla con la opcion False en la edicion de las celdas

		tableComanda.setModel(dtm);
		scrollPane.setViewportView(tableComanda);
		getContentPane().setLayout(groupLayout);

	}

	/*
	 * #############################################################################
	 * METODOS
	 * #############################################################################
	 */

	void llenarCat() throws SQLException, IOException { // Metodo para a�adir los botones a la botonera de las categorias

		ResultSet rs = Herramientas.getCatSQL();

		while (rs.next()) {
			jbCat = new JButton(rs.getString(1)); // Una por una creamos los
			// botones y sus listeners
			
			byte[] data = rs.getBlob(2).getBytes(1,(int) rs.getBlob(2).length());
			
			Image img = ImageIO.read(new ByteArrayInputStream(data));

			Image novaImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			
			jbCat.setBackground(Color.DARK_GRAY);
			
			jbCat.setIcon(new ImageIcon(novaImg));
			
			jbCat.setFont(new Font("Arial",Font.ITALIC,12));
			
			jbCat.setHorizontalTextPosition(JButton.CENTER);
			
			jbCat.setForeground(Color.WHITE);

			jbCat.setVerticalTextPosition(JButton.BOTTOM);
			
			jbCat.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(
						ActionEvent e) { /*
											 * Al presionar un boton de categoria se llama al metodo para sustituir las
											 * categorias por los productos, usamos el e.getSource().getText() para
											 * diferenciar entre botones y asi cargar la categoria que toca
											 */
					// TODO Auto-generated method stub
					panelBotons.removeAll();
					try {
						llenarProd(((JButton) e.getSource()).getText());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println((((JButton) e.getSource()).getWidth()) + " ------ "
							+ ((JButton) e.getSource()).getHeight());
					panelBotons.revalidate();
					panelBotons.repaint();
					
				}
				
			});

			panelBotons.add(jbCat);
			
		}
	}

	void llenarProd(String cat) throws SQLException, IOException { // Metodo similar para llenar la botonera con los productos de una categoria en
									// concreto

		jbProd = new JButton("Volver a Categorias"); // Definimos el primer boton manualmente ya que siempre sera el
														// boton para volver a las categorias
		
		Image img = new ImageIcon("back.png").getImage();

		Image novaImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		
		jbProd.setBackground(Color.DARK_GRAY);
		
		jbProd.setIcon(new ImageIcon(novaImg));
		
		jbProd.setFont(new Font("Arial",Font.ITALIC,12));
		
		jbProd.setHorizontalTextPosition(JButton.CENTER);
		
		jbProd.setForeground(Color.WHITE);

		jbProd.setVerticalTextPosition(JButton.BOTTOM);

		
		jbProd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panelBotons.removeAll();
				try {
					llenarCat();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // Volvemos a las categorias
 catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				panelBotons.revalidate();
				panelBotons.repaint();
			}

		});
		panelBotons.add(jbProd);

		ResultSet rs = Herramientas.getProdsSQL(cat);

		while (rs.next()) {
			jbProd = new JButton(rs.getString(1));
			
			byte[] data = rs.getBlob(2).getBytes(1,(int) rs.getBlob(2).length());
			
			img = ImageIO.read(new ByteArrayInputStream(data));

			novaImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			
			jbProd.setBackground(Color.DARK_GRAY);
			
			jbProd.setIcon(new ImageIcon(novaImg));
			
			jbProd.setFont(new Font("Arial",Font.ITALIC,12));
			
			jbProd.setHorizontalTextPosition(JButton.CENTER);
			
			jbProd.setForeground(Color.WHITE);

			jbProd.setVerticalTextPosition(JButton.BOTTOM);

			jbProd.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
//					añadirProducto(((JButton) e.getSource()).getText()); // Listener para a�adir
					// producto a la jtable
					// del pedido
				}

			});

			panelBotons.add(jbProd);
		}

	}

	void llenarMesas(JPanel pare)
			throws PropertyVetoException { /*
											 * Metodo para llenar el panel inicial del menu con los botones, con el
											 * correspondiente numero de mesas, a la vez que se a�aden los listeners
											 */

		for (int i = 0; i < ConfTool.getNumTaulesSQL(); i++) {
			String aux = "Mesa " + (i + 1);

			JButton jb = new JButton(aux);
			
			jb.setBackground(Color.DARK_GRAY);
			
			jb.setFont(new Font("Arial",Font.BOLD,20));
			
			jb.setForeground(Color.WHITE);
			
			jb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					lblMesa.setText("Mesa :" + " " + aux.substring(aux.length() - 1));
					limpiarTabla();

					if (Herramientas.comprobarFactura(getIdComandaActual())) {
						cargarComandaBarra(Herramientas.devolverComandaBarra(
								Herramientas.getId_ComandaPorMesa(Integer.parseInt(aux.substring(aux.length() - 1)))));
						llenarMetaInfo(getIdComandaActual());
					}
				}
			}

			);
			pare.add(jb);
		}
	}

	void updateTotal() { // Metodo para cambiar el lbl del Total

		/*
		 * Este Stream recoge los valores de la columna 2 de todas las filas y lo
		 * multiplica por la cantidad (columna 1 de todas las filas), una vez recogidos
		 * todos los precios por la cantidad, suma el total
		 */

		double aux = IntStream.range(0, tableComanda.getRowCount())
				.mapToDouble(x -> (Float) tableComanda.getValueAt(x, 2) * (int) tableComanda.getValueAt(x, 1)).sum();

		lblTotalNum.setText(df.format(aux));
	}

	void cargarComandaBarra(ResultSet rs) {
		if (rs == null) {
			System.out.println("Error al recuperar comanda");
			return;
		}

		try {
			while (rs.next()) {
				dtm.addRow(new Object[] { rs.getString(1), rs.getInt(2), rs.getFloat(3) });

				tableComanda.setModel(dtm);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al cargarComandaBarraSQL \rError: " + e.getMessage());
		}
		updateTotal();
	}

	void limpiarTabla() {
		IntStream.range(0, dtm.getRowCount()).boxed().sorted(Collections.reverseOrder()).forEach(x -> dtm.removeRow(x));
	}

	int getIdComandaActual() {
		return Herramientas.getId_ComandaPorMesa(Integer.parseInt(lblMesa.getText().substring(7)));
	}

	void llenarMetaInfo(int id_comanda) {
		String[] aux = Herramientas.devolverMetaInfo(id_comanda);

		lblCamarero.setText("Camarero: " + aux[1]);
		lblHoraDeEntrada.setText("Hora de entrada: " + aux[2]);
	}
	
	void calculaDevolver() { // Metodo para cambiar el lbl de Devolver, se resta el total - lo pagado.
		lblDevolverNum.setText(df.format(
				Double.parseDouble(tfPagado.getText().replace(",", ".")) - Double.parseDouble(lblTotalNum.getText().replace(",", "."))));
	}
}
