package com.example.proyecto_bar_reinols_movil;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import obsoleto.Mesa;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import java.awt.Color;

@SuppressWarnings("serial")
public class VentanaPrincipal extends JFrame implements ActionListener {

	private JPanel contentPane;
	static JTabbedPane tabbedPane;
	static JMenuBar menuBar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * #############################################################################
	 * FRAME Y FEATURES SENCILLAS
	 * #############################################################################
	 */

	/**
	 * Create the frame.
	 * 
	 * @throws PropertyVetoException
	 */
	public VentanaPrincipal() throws PropertyVetoException {
		setBackground(Color.PINK);
//		ServerTCP server = new ServerTCP();
//		server.start();

		setTitle("Ventana Principal"); // Declaracion titulo y accion al cerrar
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1371, 1060);

		menuBar = new JMenuBar(); // declaracion del JMenuBar
		setJMenuBar(menuBar);

		JMenu mnArchivo = new JMenu("Archivo"); // declaracion del elemento archivo
		menuBar.add(mnArchivo);
		
		JMenu mnVista = new JMenu("Vista"); // declaracion del elemento Vista
		menuBar.add(mnVista);
		
		JMenuItem mntmBarra = new JMenuItem("Abrir Barra");
		mntmBarra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.removeAll();
				tabbedPane.add("Barra", new ModeloBarra("Barra"));
			}
		});
		mnVista.add(mntmBarra);
		
		JMenuItem mntmCocinas = new JMenuItem("Abrir Cocinas");
		mntmCocinas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.removeAll();
				Herramientas.cargarCocinas();
			}
		});
		mnVista.add(mntmCocinas);

		JMenuItem mntmModificarNumtaules = new JMenuItem(
				"Modificar numTaules"); /*
										 * declaracion del item de menu, el cual abre la ventana para cambiar el numero
										 * de mesas (clase ModNumT)
										 */
		mntmModificarNumtaules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ModNumT();
				dispose();
			}
		});
		mnArchivo.add(mntmModificarNumtaules);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 943, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 997, Short.MAX_VALUE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);

//		Herramientas.cargarCocinas();

//		tabbedPane.add("Barra", new ModeloBarra("Barra"));
		
		menuBar.setVisible(false);
		
		tabbedPane.add("Login", new Login());
		
		tabbedPane.setSize(getSize());
		
	}

	/*
	 * #############################################################################
	 * METODOS
	 * #############################################################################
	 */

	@Override
	public void actionPerformed(ActionEvent e) { // Action performed para todos los botones de las mesas
		// TODO Auto-generated method stub
		try {
			if (tabbedPane.indexOfTab(((JButton) e.getSource())
					.getText()) > -1) { /*
										 * Condicion para verificar si la mesa que seleccionada ya existe, en ese caso
										 * abre la pesta�a correspondiente, en caso de que no exista, la crea y la abre
										 * (else)
										 */
				tabbedPane.setSelectedIndex(tabbedPane.indexOfTab((((JButton) e.getSource()).getText())));
			} else {
				tabbedPane.addTab(((JButton) e.getSource()).getText(), new Mesa());
				tabbedPane.setSelectedIndex(tabbedPane.indexOfTab((((JButton) e.getSource()).getText())));
			}
		} catch (PropertyVetoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}