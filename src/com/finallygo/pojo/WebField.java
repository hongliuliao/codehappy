/*
 * FileName:WebField.java, v1.0 Jun 19, 2009 12:21:30 PM created by finallygo
 *
 * Copyright (c) 2009 Wance 
 * All Rights Reserved.
 * Confidential and for internal user only.
 */

package com.finallygo.pojo;

/**
 * TODO描述类的功能
 *
 * @author finallygo
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class WebField extends Field {
	private String webType;
	public WebField() {
		// TODO Auto-generated constructor stub
	}
	public WebField(String chineseName,String fieldName,String webType){
		this.chineseName=chineseName;
		this.fieldName=fieldName;
		this.webType=webType;
	}
	public WebField(String fieldName, String fieldType, int fieldLength, boolean isNull,String chineseName) {
//		super();
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.fieldLength = fieldLength;
		this.isNull = isNull;
		this.chineseName=chineseName;
	}
	public String getWebType() {
		return webType;
	}

	public void setWebType(String webType) {
		this.webType = webType;
	}
}
