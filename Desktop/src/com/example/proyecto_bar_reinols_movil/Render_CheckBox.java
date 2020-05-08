package com.example.proyecto_bar_reinols_movil;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class Render_CheckBox extends JCheckBox implements TableCellRenderer {
	private JComponent component = new JCheckBox();
	
	public Render_CheckBox() {
		// TODO Auto-generated constructor stub
		setOpaque(true);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable tabla, Object value, boolean seleccionado, boolean focuseado,
			int fila, int columna) {
		// TODO Auto-generated method stub
		((JCheckBox)component).setBackground(new Color(0,128,0));
		
		boolean b = (value != null && (((Boolean)value).booleanValue()));
		
		((JCheckBox)component ).setSelected(b);
		
		return ((JCheckBox) component);
	}
		
}