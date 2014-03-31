/*
 * FileName:JDBCTemplate.java, v1.0 2009-4-22 ����06:04:34 created by Administrator
 *
 * Copyright (c) 2009 Wance 
 * All Rights Reserved.
 * Confidential and for internal user only.
 */

package com.finallygo.dbutil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import com.finallygo.pojo.User;



/**
 * TODO������Ĺ���
 * 
 * @author finallygo
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class JDBCTemplate {

	private static final Log log = LogFactory.getLog(JDBCTemplate.class);

	private static final Map<Class, PropertySetter> setters = new HashMap<Class, PropertySetter>();
	
	private static final Map<Class,Map<String, Method>> classGetMethods=new HashMap<Class, Map<String, Method>>();
//	private static final 

	private Connection connection;

	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = C3P0ConnectionManager.getInstance().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void close(Connection conn) {
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public List executeQuery(String sql, Class clazz) {
		log.trace("��ѯ��ʼ:" + sql);
		Connection conn = getConnection();
		List<Object> list = new ArrayList<Object>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			try {
				ResultSet rs = pstmt.executeQuery();
				PropertySetter setter = getPropertySetter(clazz);
				list.addAll(setter.mapping(rs));
				rs.close();
			} catch (Exception e) {
				throw new RuntimeException("��ѯ�쳣:" + sql, e);
			} finally {
				close(conn);
			}

		} catch (SQLException e) {
			log.error("��ѯ�쳣:" + sql, e);
			throw new RuntimeException("��ѯ�쳣:" + sql, e);
		}
		log.trace("��ѯ����:" + sql);
		return list;
	}

	public int executeUpdate(String sql) {
		int rows = 0;
		Connection conn = getConnection();
		try {
			Statement pstmt = conn.createStatement();
			try {
				rows = pstmt.executeUpdate(sql);
			} finally {
				pstmt.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(conn);
		}
		return rows;
	}

	/**
	 * ��ӷ���
	 * 
	 * @param sql
	 *            ��ӵ�sql���
	 * @param obj
	 *            ��ӵĶ���
	 * @return ��ӵĶ����id
	 */
	public long executeSave(String sql, Object obj) {
		Connection conn = getConnection();
		PreparedStatement ps=null;
		Map<String, Method> getMethods=null;
		Field[] fields=null;
		String name=null;
		try {
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			Class clazz = obj.getClass();
			getMethods=classGetMethods.get(clazz);
			if(getMethods==null){
				getMethods = new HashMap<String, Method>();
				Method[] methods = clazz.getDeclaredMethods();
				for (Method m : methods) {
					if (m.getName().startsWith("get")) {
						getMethods.put(m.getName().substring(3), m);
					}
				}
				classGetMethods.put(clazz, getMethods);
			}
			fields= clazz.getDeclaredFields();
			for (int i = 1; i < fields.length; i++) {
				name= fields[i].getName();
				name = name.substring(0, 1).toUpperCase() + name.substring(1);
				Method m = getMethods.get(name);
				ps.setObject(i, (Object)m.invoke(obj, new Object[] {}));
			}
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				return rs.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn);
		}
		return 0;
	}

	/**
	 * finallygoд�ĸ���
	 * 
	 * @param sql
	 *            ���µ�sql���
	 * @param obj
	 *            Ҫ���µĶ���
	 * @throws Exception
	 */
	public int executeUpdate(String sql, Object obj) {

		Class clazz = obj.getClass();
		Connection conn = getConnection();
		PreparedStatement ps=null;
		String name=null;
		Map<String, Method> getMethods=null;
		Method[] methods=null;
		try {
			getMethods=classGetMethods.get(clazz);
			if(getMethods==null){
				getMethods = new HashMap<String, Method>();
				classGetMethods.put(clazz, getMethods);
				methods = clazz.getDeclaredMethods();
				for (Method m : methods) {
					if (m.getName().startsWith("get")) {
						getMethods.put(m.getName().substring(3), m);
					}
				}
			}

			ps=conn.prepareStatement(sql);
			Field[] fields = clazz.getDeclaredFields();

			for (int i = 1; i <= fields.length; i++) {
				if (i < fields.length) {
					name = fields[i].getName();
				} else {
					name = fields[0].getName();
				}

				name = name.substring(0, 1).toUpperCase() + name.substring(1);
				Method m2 = getMethods.get(name);
				ps.setObject(i, m2.invoke(obj, new Object[]{}));

			}
			return ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn);
		}
		return 0;
	}
	/**
	 * ���select count(1) from oa_department��������䣬ֻ��Ҫ���ص�������������
	 * @param sql
	 * @return
	 */
	public Object getSingleValue(String sql){
		log.debug("��ѯ:"+sql);
		Object ret=null;
		Connection conn=getConnection();
		try{
			Statement stmt=conn.createStatement();
			try{
				ResultSet rs=stmt.executeQuery(sql);
				if(rs.next()){
					ret=rs.getObject(1);
				}
				rs.close();
			}finally{
				stmt.close();
			}
		}catch(SQLException e){
			log.error("ִ��"+sql+"ʧ��",e);
			throw new RuntimeException("ִ��"+sql+"ʧ��",e);
		}finally{
			close(conn);
		}
		return ret;
	}
	/**
	 * ����оۺ���SQL���������ִ��в����ģ���select count(1) from salarys where salary >?��ֻ�᷵��һ��ֵ�������
	 * @param sql
	 * @param params
	 * @return
	 */
	public Object getSingleValue(String sql, Object [] params){
		log.debug("��ѯ:"+sql);
		Object ret=null;
		Connection conn=getConnection();
		try{
			PreparedStatement pstmt=conn.prepareStatement(sql);
			try{
				for(int i=0;i<params.length;i++){
					pstmt.setObject(i+1, params[i]);
				}
				ResultSet rs=pstmt.executeQuery();
				if(rs.next()){
					ret=rs.getObject(1);
				}
				rs.close();
			}finally{
				pstmt.close();
			}
		}catch(SQLException e){
			log.error("ִ��"+sql+"ʧ��",e);
			throw new RuntimeException("ִ��"+sql+"ʧ��",e);
		}finally{
			close(conn);
		}
		return ret;
	}
	private synchronized PropertySetter getPropertySetter(Class pojoClass) {
		PropertySetter setter = setters.get(pojoClass);
		if (setter == null) {
			setter = new PropertySetter(pojoClass);
			setters.put(pojoClass, setter);
		}
		return setter;
	}

	public static void main(String[] args) {
//		User user = new User();
//		user.setUserId(1);
//		user.setUserName("555");
//		user.setUserPassword("55");
//		user.setUserRole(5);
//		
//		JDBCTemplate t = new JDBCTemplate();
//		t.executeSave("insert into tb_user values(?,?,?)", user);
////		t.executeUpdate("update tb_user set user_name=?,user_password=?,user_role=? where user_id=?", user);
//		user.setUserName("123");
////		t.executeSave("insert into tb_user values(?,?,?)", user);
//		t.executeUpdate("update tb_user set user_name=?,user_password=?,user_role=? where user_id=?", user);
	}
}
