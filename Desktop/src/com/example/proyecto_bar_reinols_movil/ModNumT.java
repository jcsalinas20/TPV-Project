package com.example.proyecto_bar_reinols_movil;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ModNumT extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModNumT frame = new ModNumT();
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
	public ModNumT() {
		setBounds(100, 100, 374, 220);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblMesasActuales = new JLabel("Mesas actuales: " + ConfTool.getNumTaulesSQL());

		JLabel lblNuevoNumeroDe = new JLabel("Nuevo numero de mesas:");

		textField = new JTextField();
		textField.setColumns(10);

		setVisible(true);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JButton btnNewButton = new JButton("Hecho");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ConfTool.cambiarNumMesasSQL(Integer.parseInt(textField
						.getText()))) { /*
										 * Este listener es lo unico que ver en esta clase, ya que lo unico que hace es
										 * mandar el valor del text field al metodo para cambiar las mesas, en caso de
										 * que todo vaya bien, esta ventana se cierra y abre una nueva ventana principal
										 */
					try {
						new VentanaPrincipal().setVisible(true);
					} catch (PropertyVetoException e1) {
						// TODO Auto-generated catch block
						System.out.println("Error ModNumT PropertyVetoException: " + e1.getMessage());
					}
					System.out.println("esta mierda no va");
				}
				dispose();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
										.addComponent(lblNuevoNumeroDe, GroupLayout.PREFERRED_SIZE, 171,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18).addComponent(textField, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup().addGap(128).addComponent(btnNewButton))
								.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(
										lblMesasActuales, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(55, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(32)
				.addComponent(lblMesasActuales, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNuevoNumeroDe, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED, 21, Short.MAX_VALUE).addComponent(btnNewButton)
				.addGap(36)));
		contentPane.setLayout(gl_contentPane);
	}
}
