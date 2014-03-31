/*
 * FileName:C3P0ConnectionManager.java, v1.0 2009-4-22 下午06:00:51 created by Administrator
 *
 * Copyright (c) 2009 Wance 
 * All Rights Reserved.
 * Confidential and for internal user only.
 */

package com.finallygo.dbutil;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * TODO描述类的功能
 *
 * @author finallygo
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class C3P0ConnectionManager {
	
	private static final ComboPooledDataSource cpds=new ComboPooledDataSource();
	
	private ThreadLocal<Connection> simple=new ThreadLocal<Connection>();
	
	public static ComboPooledDataSource getInstance(){

		return cpds;
	}
	
	public Connection getConection(){
		Connection conn=null;
		try {
			conn = cpds.getConnection();
			simple.set(conn);
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return conn;
	}
	
	public void close(Connection conn){
		if(conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public void destroy(){
		if(cpds != null){
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	protected void finalize() throws Throwable {
		destroy();
		super.finalize();
	}
	public static void main(String[] args) {
		try {
			Connection conn=C3P0ConnectionManager.getInstance().getConnection();
			System.out.println(conn);
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}
}
