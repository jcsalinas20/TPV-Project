package com.example.proyecto_bar_reinols_movil;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class CeldaCheckBox extends DefaultCellEditor {
	private JComponent jcomp = new JCheckBox();
	private boolean valor = false;

	public CeldaCheckBox() {
		super(new JCheckBox());
	}

	@Override
	public Object getCellEditorValue() { //
		// TODO Auto-generated method stub
		return ((JCheckBox) jcomp).isSelected();
	}

	@Override
	public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) { //
		// TODO Auto-generated method stub
		((JCheckBox) jcomp).setBackground(new Color(255, 165, 0));

		((JCheckBox) jcomp).setSelected((boolean) valor);

		return ((JCheckBox) jcomp);
 
	}

	@Override
	public boolean stopCellEditing() { //
		// TODO Auto-generated method stub
		valor = ((Boolean) getCellEditorValue()).booleanValue();
		((JCheckBox) jcomp).setSelected(valor);
		return super.stopCellEditing();
	}

	public Component getTableCellRenderComponent(JTable tabla, Object value, boolean seleccionado, boolean focuseado,
			int fila, int columna) {
		if (value == null) return null;
		return ((JCheckBox)jcomp);
	}

}




