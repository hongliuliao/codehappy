package com.finallygo.model;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.finallygo.pojo.Field;

public class Dbmodel extends AbstractTableModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Vector<Field> fields=new Vector<Field>();
	
	public void addField(Field field){
		fields.add(field);
		fireTableRowsInserted(fields.size(), fields.size());
	}

	public void removeField(Field field){
		for(int i=0;i<fields.size();i++){
			Field f=fields.get(i);
			if(f.equals(field)){
				fields.remove(i);
				fireTableRowsDeleted(i, i);
				break;
			}
		}
	}
	
	public int getColumnCount() {
		return 5;
	}

	public int getRowCount() {
		return fields.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Field field=fields.get(rowIndex);
		switch(columnIndex){
			case 0:
				return field.getFieldName();
			case 1:
				return field.getChineseName();
			case 2:
				return field.getFieldType();
			case 3:
				return field.getFieldLength();
			case 4:
				return field.isNull();
			default:
				return "";
		}
	}
	@Override
	public String getColumnName(int column) {
		switch(column){
		case 0:
			return "列名";
		case 2:
			return "数据类型";
		case 3:
			return "长度";
		case 4:
			return "允许空";
		case 1:
			return "中文名";
		default:
			return "";
		}
	}
}
