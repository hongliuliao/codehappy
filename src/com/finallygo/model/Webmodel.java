package com.finallygo.model;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.finallygo.pojo.WebField;

public class Webmodel extends AbstractTableModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Vector<WebField> webFields=new Vector<WebField>();
	
	public void addWebField(WebField webField){
		webFields.add(webField);
		fireTableRowsInserted(webFields.size(), webFields.size());
	}

	public void removeWebField(WebField webField){
		for(int i=0;i<webFields.size();i++){
			WebField f=webFields.get(i);
			if(f.equals(webField)){
				webFields.remove(i);
				fireTableRowsDeleted(i, i);
				break;
			}
		}
	}
	public void updateWebField(int index,WebField webField){
		webFields.set(index, webField);
		fireTableRowsUpdated(index, index);
	}
	
	public int getColumnCount() {
		return 3;
	}

	public int getRowCount() {
		return webFields.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		WebField webField=webFields.get(rowIndex);
		switch(columnIndex){
			case 0:
				return webField.getChineseName();
			case 1:
				return webField.getFieldName();
			case 2:
				return webField.getWebType();
			default:
				return "";
		}
	}
	@Override
	public String getColumnName(int column) {
		switch(column){
		case 0:
			return "中文名";
		case 1:
			return "属性名";
		case 2:
			return "类型";
		default:
			return "";
		}
	}
}
