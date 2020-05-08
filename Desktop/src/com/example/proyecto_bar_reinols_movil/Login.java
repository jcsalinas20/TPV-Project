package com.example.proyecto_bar_reinols_movil;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;

public class Login extends JInternalFrame implements KeyListener {
	private JTextField textFieldUsuario;
	private JPasswordField  jPasswordField1;
	private String targetUsuarioPassword = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		//getContentPane().setBackground(Color.white);
		setContentPane(new JLabel(new ImageIcon("logo_apojosu12x1080_landscape2.png")));
		setBounds(100, 100, 1188, 745);
		setTitle("Camareros");
		getContentPane().setLayout(null);

		JButton button_2 = new JButton("3");
		button_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_2.setBounds(228, 211, 58, 39);
		getContentPane().add(button_2);

		JButton button_3 = new JButton("4");
		button_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_3.setBounds(296, 211, 58, 39);
		getContentPane().add(button_3);

		JButton button_4 = new JButton("5");
		button_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_4.setBounds(364, 211, 58, 39);
		getContentPane().add(button_4);

		JButton button_5 = new JButton("6");
		button_5.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_5.setBounds(432, 211, 58, 39);
		getContentPane().add(button_5);

		JButton button_6 = new JButton("7");
		button_6.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_6.setBounds(500, 211, 58, 39);
		getContentPane().add(button_6);

		JButton button_7 = new JButton("8");
		button_7.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_7.setBounds(568, 211, 58, 39);
		getContentPane().add(button_7);

		JButton button_8 = new JButton("9");
		button_8.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_8.setBounds(636, 211, 58, 39);
		getContentPane().add(button_8);

		JButton btnA = new JButton("Q");
		btnA.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnA.setBounds(91, 265, 58, 39);
		getContentPane().add(btnA);

		JButton button = new JButton("2");
		button.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button.setBounds(159, 211, 58, 39);
		getContentPane().add(button);

		JButton button_1 = new JButton("1");
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_1.setBounds(91, 211, 58, 39);
		getContentPane().add(button_1);

		JButton btnB = new JButton("W");
		btnB.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnB.setBounds(159, 265, 58, 39);
		getContentPane().add(btnB);

		JButton btnC = new JButton("E");
		btnC.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnC.setBounds(228, 265, 58, 39);
		getContentPane().add(btnC);

		JButton btnD = new JButton("R");
		btnD.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnD.setBounds(296, 265, 58, 39);
		getContentPane().add(btnD);

		JButton button_13 = new JButton("0");
		button_13.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_13.setBounds(704, 211, 58, 39);
		getContentPane().add(button_13);

		JButton btnE = new JButton("T");
		btnE.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnE.setBounds(364, 265, 58, 39);
		getContentPane().add(btnE);

		JButton btnY = new JButton("Y");
		btnY.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnY.setBounds(432, 265, 58, 39);
		getContentPane().add(btnY);

		JButton btnU = new JButton("U");
		btnU.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnU.setBounds(500, 265, 58, 39);
		getContentPane().add(btnU);

		JButton btnI = new JButton("I");
		btnI.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnI.setBounds(568, 265, 58, 39);
		getContentPane().add(btnI);

		JButton btnO = new JButton("O");
		btnO.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnO.setBounds(636, 265, 58, 39);
		getContentPane().add(btnO);

		JButton btnP = new JButton("P");
		btnP.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnP.setBounds(704, 265, 58, 39);
		getContentPane().add(btnP);

		JButton btnA_1 = new JButton("A");
		btnA_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnA_1.setBounds(91, 315, 58, 39);
		getContentPane().add(btnA_1);

		JButton btnS = new JButton("S");
		btnS.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnS.setBounds(159, 315, 58, 39);
		getContentPane().add(btnS);

		JButton btnD_1 = new JButton("D");
		btnD_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnD_1.setBounds(228, 315, 58, 39);
		getContentPane().add(btnD_1);

		JButton btnF = new JButton("F");
		btnF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnF.setBounds(296, 315, 58, 39);
		getContentPane().add(btnF);

		JButton btnG = new JButton("G");
		btnG.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnG.setBounds(364, 315, 58, 39);
		getContentPane().add(btnG);

		JButton btnH = new JButton("H");
		btnH.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnH.setBounds(432, 315, 58, 39);
		getContentPane().add(btnH);

		JButton btnJ = new JButton("J");
		btnJ.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnJ.setBounds(500, 315, 58, 39);
		getContentPane().add(btnJ);

		JButton btnK = new JButton("K");
		btnK.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnK.setBounds(568, 315, 58, 39);
		getContentPane().add(btnK);

		JButton btnL = new JButton("L");
		btnL.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnL.setBounds(636, 315, 58, 39);
		getContentPane().add(btnL);

		JButton btnZ = new JButton("Z");
		btnZ.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnZ.setBounds(704, 315, 58, 39);
		getContentPane().add(btnZ);

		JButton btnX = new JButton("X");
		btnX.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnX.setBounds(91, 365, 58, 39);
		getContentPane().add(btnX);

		JButton btnC_1 = new JButton("C");
		btnC_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnC_1.setBounds(159, 365, 58, 39);
		getContentPane().add(btnC_1);

		JButton btnV = new JButton("V");
		btnV.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnV.setBounds(228, 365, 58, 39);
		getContentPane().add(btnV);

		JButton btnB_1 = new JButton("B");
		btnB_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnB_1.setBounds(296, 365, 58, 39);
		getContentPane().add(btnB_1);

		JButton btnN = new JButton("N");
		btnN.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnN.setBounds(364, 365, 58, 39);
		getContentPane().add(btnN);

		JButton btnM = new JButton("M");
		btnM.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnM.setBounds(432, 365, 58, 39);
		getContentPane().add(btnM);

		textFieldUsuario = new JTextField();
		textFieldUsuario.setFont(new Font("Tahoma", Font.PLAIN, 25));
		textFieldUsuario.setBounds(887, 233, 199, 46);
		getContentPane().add(textFieldUsuario);
		textFieldUsuario.setColumns(10);

		textFieldUsuario.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					enviarUsuarioLogin();
				}
			}
		});

		JLabel lblLogin = new JLabel("LOGIN");
		lblLogin.setFont(new Font("Impact", Font.PLAIN, 39));
		lblLogin.setBounds(142, 71, 227, 96);
		getContentPane().add(lblLogin);

		jPasswordField1 = new JPasswordField();
		jPasswordField1.setFont(new Font("Tahoma", Font.PLAIN, 25));
		jPasswordField1.setColumns(10);
		jPasswordField1.setBounds(887, 329, 199, 46);
		getContentPane().add(jPasswordField1);

		jPasswordField1.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					enviarUsuarioLogin();
				}
			}
		});

		JLabel lblUsuari = new JLabel("Usuari:");
		lblUsuari.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblUsuari.setBounds(887, 172, 101, 56);
		getContentPane().add(lblUsuari);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPassword.setBounds(887, 280, 110, 45);
		getContentPane().add(lblPassword);

		JButton btnLogin = new JButton("LOGIN");
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 26));
		btnLogin.setBounds(926, 386, 118, 45);
		getContentPane().add(btnLogin);

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enviarUsuarioLogin();
			}
		});

		ActionListener actionListenerBotonesTeclado = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (targetUsuarioPassword.equalsIgnoreCase("textFieldUsuario")) {
					textFieldUsuario.setText(textFieldUsuario.getText() + e.getActionCommand());
				} else if (targetUsuarioPassword.equalsIgnoreCase("jPasswordField1")) {
					jPasswordField1.setText(jPasswordField1.getText() + e.getActionCommand());
				}

			}
		};

		textFieldUsuario.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				targetUsuarioPassword = "textFieldUsuario";
			}
		});

		jPasswordField1.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				targetUsuarioPassword = "jPasswordField1";
			}
		});
		button_2.addActionListener(actionListenerBotonesTeclado);
		button_3.addActionListener(actionListenerBotonesTeclado);
		button_4.addActionListener(actionListenerBotonesTeclado);
		button_5.addActionListener(actionListenerBotonesTeclado);
		button_6.addActionListener(actionListenerBotonesTeclado);
		button_7.addActionListener(actionListenerBotonesTeclado);
		button_8.addActionListener(actionListenerBotonesTeclado);
		btnA.addActionListener(actionListenerBotonesTeclado);
		button.addActionListener(actionListenerBotonesTeclado);
		button_1.addActionListener(actionListenerBotonesTeclado);
		btnB.addActionListener(actionListenerBotonesTeclado);
		btnC.addActionListener(actionListenerBotonesTeclado);
		btnD.addActionListener(actionListenerBotonesTeclado);
		button_13.addActionListener(actionListenerBotonesTeclado);
		btnE.addActionListener(actionListenerBotonesTeclado);
		btnY.addActionListener(actionListenerBotonesTeclado);
		btnU.addActionListener(actionListenerBotonesTeclado);
		btnI.addActionListener(actionListenerBotonesTeclado);
		btnO.addActionListener(actionListenerBotonesTeclado);
		btnP.addActionListener(actionListenerBotonesTeclado);
		btnA_1.addActionListener(actionListenerBotonesTeclado);
		btnS.addActionListener(actionListenerBotonesTeclado);
		btnD_1.addActionListener(actionListenerBotonesTeclado);
		btnF.addActionListener(actionListenerBotonesTeclado);
		btnG.addActionListener(actionListenerBotonesTeclado);
		btnH.addActionListener(actionListenerBotonesTeclado);
		btnJ.addActionListener(actionListenerBotonesTeclado);
		btnK.addActionListener(actionListenerBotonesTeclado);
		btnL.addActionListener(actionListenerBotonesTeclado);
		btnZ.addActionListener(actionListenerBotonesTeclado);
		btnX.addActionListener(actionListenerBotonesTeclado);
		btnC_1.addActionListener(actionListenerBotonesTeclado);
		btnV.addActionListener(actionListenerBotonesTeclado);
		btnB_1.addActionListener(actionListenerBotonesTeclado);
		btnN.addActionListener(actionListenerBotonesTeclado);
		btnM.addActionListener(actionListenerBotonesTeclado);

		/*
		 * JButton buttons[] = new JButton[nombreCamareros.length];
		 * 
		 * for (int i = 0; i < buttons.length; ++i) { JButton btn = new
		 * JButton(nombreCamareros[i]); btn.setName(nombreCamareros[i]);
		 * btn.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { System.out.println("boton"+btn.getName());
		 * VentanaPrincipal.tabbedPane.remove(0); try {
		 * VentanaPrincipal.tabbedPane.add("Barra", new ModeloBarra("Barra"));
		 * ModeloBarra.lblCamarero.setText("Camarero: "+btn.getName()); } catch
		 * (PropertyVetoException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } } }); getContentPane().add(btn); buttons[i] = btn; }
		 */

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			enviarUsuarioLogin();
		}

	}

	public void enviarUsuarioLogin() {
		String[] nombreCamareros = Herramientas.nombreCamareros().split("-");
		boolean existeCamarero = false;
		String nombreCamarero = "";
		for (int i = 0; i < nombreCamareros.length; i++) {
			if (textFieldUsuario.getText().equalsIgnoreCase(nombreCamareros[i])) {
				existeCamarero = true;
				nombreCamarero = nombreCamareros[i];
			}
		}
		if (existeCamarero) {
			VentanaPrincipal.tabbedPane.remove(0);
//			VentanaPrincipal.tabbedPane.add("Barra", new ModeloBarra("Barra"));
//				ModeloBarra.lblCamarero.setText("Camarero: " + nombreCamarero);
			
			VentanaPrincipal.menuBar.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null,
					"Camarero " + textFieldUsuario.getText().toLowerCase() + " no reconocido", "Error!",
					JOptionPane.ERROR_MESSAGE);

			textFieldUsuario.setText("");
			jPasswordField1.setText("");

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}