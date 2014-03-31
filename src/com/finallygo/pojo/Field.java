package com.finallygo.pojo;

public class Field {
	protected String fieldName;//字段名
	protected String fieldType;//字段类型
	protected int fieldLength;//字段长度
	protected boolean isNull;//是否允许为空
	protected String chineseName;//中文名
	
	public Field() {
		// TODO Auto-generated constructor stub
	}
	public Field(String fieldName, String fieldType, int fieldLength, boolean isNull,String chineseName) {
		super();
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.fieldLength = fieldLength;
		this.isNull = isNull;
		this.chineseName=chineseName;
	}
	public int getFieldLength() {
		return fieldLength;
	}
	public void setFieldLength(int fieldLength) {
		this.fieldLength = fieldLength;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public boolean isNull() {
		return isNull;
	}
	public void setNull(boolean isNull) {
		this.isNull = isNull;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
}
