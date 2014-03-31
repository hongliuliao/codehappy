/*
 * FileName: BaseDao.java,v 1.0 2009 4 30 17:12:51 created by sean
 * 
 * Copyright (c) 2008 Sean
 * All Rights Reserved.
 * Confidential and for internal use only.
 */
package com.finallygo.dbutil;

/**
 * TODO描述类的功能
 * 
 * @author sean
 * @version $Revision: 1.1 $
 * @since 1.0
 */
public abstract class BaseDao {
	protected JDBCTemplate template;

	public JDBCTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JDBCTemplate template) {
		this.template = template;
	}
	
}
