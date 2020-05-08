package com.example.proyecto_bar_reinols_movil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.stream.IntStream;

import javax.swing.JInternalFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Cuina extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultTableModel dtm;
	private DefaultTableModel dtmServit;
	private JLabel lblHoraDeEntrada;
	private JLabel lblCamarero;
	JLabel lblMesa;
	private JTable tableCocinando;
	private JTable tablePreparado;
	private JScrollPane scrollPaneCocinando;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public Cuina(int id_comanda) {
		setBounds(100, 100, 956, 498);

		lblCamarero = new JLabel("Camarero:");
		lblCamarero.setFont(new Font("Arial", Font.BOLD, 12));

		lblHoraDeEntrada = new JLabel("Hora de entrada: ");
		lblHoraDeEntrada.setFont(new Font("Arial", Font.BOLD, 12));

		scrollPaneCocinando = new JScrollPane();

		JScrollPane scrollPanePreparado = new JScrollPane();

		JLabel lblCocinando = new JLabel("Cocinando: ");
		lblCocinando.setFont(new Font("Arial", Font.BOLD, 25));

		JLabel labelPreparado = new JLabel("Preparado: ");
		labelPreparado.setFont(new Font("Arial", Font.BOLD, 25));

		lblMesa = new JLabel("Mesa: ");
		lblMesa.setFont(new Font("Arial", Font.BOLD, 12));

		JButton btnPreparado = new JButton("-->");

		btnPreparado.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				botonServit(id_comanda);
			}

		});

		JButton btnDevolver = new JButton("<--");

		btnDevolver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				botonTornat(id_comanda);
			}

		});

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
						.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
								.createSequentialGroup()
								.addComponent(scrollPaneCocinando, GroupLayout.PREFERRED_SIZE, 384,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(btnDevolver, GroupLayout.PREFERRED_SIZE, 64,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btnPreparado, GroupLayout.PREFERRED_SIZE, 64,
												GroupLayout.PREFERRED_SIZE)))
								.addComponent(lblCocinando, GroupLayout.PREFERRED_SIZE, 196,
										GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(labelPreparado, GroupLayout.PREFERRED_SIZE, 218,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPanePreparado, GroupLayout.PREFERRED_SIZE, 423,
										GroupLayout.PREFERRED_SIZE)))
						.addGroup(
								groupLayout.createParallelGroup(Alignment.TRAILING).addComponent(lblHoraDeEntrada)
										.addGroup(groupLayout.createSequentialGroup().addComponent(lblCamarero)
												.addGap(104).addComponent(lblMesa))))
				.addContainerGap(27, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
						.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(labelPreparado, GroupLayout.PREFERRED_SIZE, 45,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblCocinando, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
						.addGap(6)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
								groupLayout.createSequentialGroup().addGap(9).addComponent(scrollPaneCocinando,
										GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(scrollPanePreparado,
												GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup().addGap(129).addComponent(btnPreparado).addGap(36)
								.addComponent(btnDevolver)))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblCamarero)
						.addComponent(lblMesa))
				.addGap(34).addComponent(lblHoraDeEntrada).addContainerGap(562, Short.MAX_VALUE)));

		tablePreparado = new JTable();
		tablePreparado.setColumnSelectionAllowed(true);
		tablePreparado.setCellSelectionEnabled(true);
		scrollPanePreparado.setViewportView(tablePreparado);

		tableCocinando = new JTable();
		tableCocinando.setColumnSelectionAllowed(true);
		tableCocinando.setCellSelectionEnabled(true);
		scrollPaneCocinando.setViewportView(tableCocinando);

//		metaInfo(comanda);
		dtm = new DefaultTableModel(new Object[][] {}, new String[] { "Producto", "Cantidad", "Listo" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				if (column != 2)
					return false;
				else
					return true;
			}
		};

		tableCocinando.setModel(dtm);

		tableCocinando.getColumnModel().getColumn(2).setCellEditor(new CeldaCheckBox());

		tableCocinando.getColumnModel().getColumn(2).setCellRenderer(new Render_CheckBox());

		cargarComandaSQL(Herramientas.devolverComanda(id_comanda));

		metaInfoSQL(id_comanda);

		tableCocinando.setModel(dtm);

		dtmServit = new DefaultTableModel(new Object[][] {}, new String[] { "Producto", "Cantidad", "Devolver" }) {
			/*
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				if (column != 2)
					return false;
				else
					return true;
			}
		};

		tablePreparado.setModel(dtmServit);

		tablePreparado.getColumnModel().getColumn(2).setCellEditor(new CeldaCheckBox());

		tablePreparado.getColumnModel().getColumn(2).setCellRenderer(new Render_CheckBox());
		
		tableCocinando.setCellSelectionEnabled(false);
		tablePreparado.setCellSelectionEnabled(false);
		dtm.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
				int row = e.getFirstRow();
		        int column = e.getColumn();
		        if (column == 2) {
		            TableModel model = (TableModel) e.getSource();
//		            String columnName = model.getColumnName(column);
		            Boolean checked = (Boolean) model.getValueAt(row, column);
		            if (checked) {
//		                System.out.println(columnName + ": " + true);
		                Herramientas.preparadoTrue(id_comanda, (String) tableCocinando.getValueAt(row, 0));
		            } else {
//		                System.out.println(columnName + ": " + false);
		                Herramientas.preparadoFalse(id_comanda, (String) tableCocinando.getValueAt(row, 0));
		            }
		        }
			}
			
		});

		getContentPane().setLayout(groupLayout);

	}

	void cargarComandaSQL(ResultSet rs) {
		if (rs == null) {
			System.out.println("Error al recuperar comanda");
			return;
		}

		try {
			while (rs.next()) {
				dtm.addRow(new Object[] { rs.getString(1), rs.getInt(2), rs.getBoolean(3) });

				tableCocinando.setModel(dtm);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al cargarComandaSQL \rError: " + e.getMessage());
		}
	}

	void metaInfoSQL(int id_comanda) {
		String[] aux = Herramientas.devolverMetaInfo(id_comanda);

		lblMesa.setText("Mesa: " + aux[0]);

		lblCamarero.setText("Camarero: " + aux[1]);
		
		lblHoraDeEntrada.setText("Hora de entrada: "+aux[2]);
	}

	void botonServit(int id_comanda) {

		IntStream.range(0, tableCocinando.getRowCount()).boxed().filter(x -> (boolean) tableCocinando.getValueAt(x, 2))
				.sorted(Collections.reverseOrder()).forEach(x -> {
					dtmServit.addRow(new Object[] { tableCocinando.getValueAt(x, 0), tableCocinando.getValueAt(x, 1), false });
					Herramientas.servidoTrue(id_comanda, (String) tableCocinando.getValueAt(x, 0));
					dtm.removeRow(x);
				});

//		for (int i = tableCocinando.getRowCount()-1; i >= 0; i--) {
//			if ((boolean) tableCocinando.getValueAt(i, 2)) {
//				dtmServit.addRow(new Object[] { tableCocinando.getValueAt(i, 0), tableCocinando.getValueAt(i, 1), false });
//				Herramientas.preparadoTrue(id_comanda, (String) tableCocinando.getValueAt(i, 0));
//				dtm.removeRow(i);
//			}
//		}
		
	}

	void botonTornat(int id_comanda) {
		IntStream.range(0, tablePreparado.getRowCount()).boxed().filter(x -> (boolean) tablePreparado.getValueAt(x, 2))
				.sorted(Collections.reverseOrder()).forEach(x -> {
					dtm.addRow(
							new Object[] { tablePreparado.getValueAt(x, 0), tablePreparado.getValueAt(x, 1), false });
					Herramientas.servidoFalse(id_comanda, (String) tablePreparado.getValueAt(x, 0));
					Herramientas.preparadoFalse(id_comanda, (String) tablePreparado.getValueAt(x, 0));
					dtmServit.removeRow(x);
				});
	}

	///////////////////////////////////////////////////// NO SQL

}
