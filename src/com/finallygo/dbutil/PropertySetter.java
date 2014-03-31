/*
 * FileName:PropertySetter.java, v1.0 Apr 24, 2009 8:06:50 AM created by Administrator
 *
 * Copyright (c) 2009 Wance 
 * All Rights Reserved.
 * Confidential and for internal user only.
 */

package com.finallygo.dbutil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;

import com.finallygo.pojo.Field;
import com.finallygo.pojo.User;



/**
 * ����BEAN�������������ݿ����ֶ����Ķ�Ӧ��ϵ,ȡ����Ӧ�ֶ��е���Ϣ,���õ�JAVA BEAN��
 * �ֶ�        ����
 * user_name   userName
 * userName    userName
 * USERNAME		userName
 * �ֶ���ȥ��_,equalsIgnoreCase(������)
 * @author sean
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class PropertySetter {
	/**
	 * �������е�setter����,�ɸ�������������
	 */
	private Map<String, Method> setMethods=new HashMap<String, Method>();
	/**
	 * ����BEAN����,��������һ������
	 */
	private Class beanClass;
	/**
	 * ���췽��,ȡ�����е���������,��ɨ��һ��,ȡ��setter����
	 * @param beanClass
	 */
//	private static List<Class> list=new ArrayList<Class>();
//	static{
//		//���������ͼ���
//		list.add(Integer.class);
//		list.add(String.class);
//		list.add(Date.class);
//		list.add(Boolean.class);
//		list.add(Number.class);
//		list.add(int.class);
//		list.add(boolean.class);
//	}
	public PropertySetter(Class beanClass) {
		this.beanClass=beanClass;		
		Method[] methods=beanClass.getMethods();
		if(methods==null){
			return;
		}
		for(Method m:methods){
			if(m.getName().startsWith("set")){
				if(m.getName().substring(3).startsWith(beanClass.getSimpleName())){
					setMethods.put(m.getName().substring(3).toLowerCase(),m);
				}else{
					setMethods.put("foreign",m);
				}
			}
		}

	}
	/**
	 * ���ֶ���ת��Ϊ������Сд
	 * @param columnName
	 * @return
	 */
	private String toPropertyName(String columnName){
		String temp=columnName.replaceAll("_", "");
		return temp.toLowerCase();
	}
	/**
	 * ���н������java bean��ת��
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	@SuppressWarnings("unchecked")
	public List mapping(ResultSet rs) throws SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		List objs=new ArrayList();
		ResultSetMetaData metaData=rs.getMetaData();
		Map<Integer, String> columns=new HashMap<Integer, String>();//�����±��Ӧ��������
		for(int i=1;i<=metaData.getColumnCount();i++){
			columns.put(i, toPropertyName(metaData.getColumnName(i)));
		}
		while(rs.next()){
			Object bean=beanClass.newInstance();
			for(int i=1;i<=columns.size();i++){
				Object value=rs.getObject(i);
				Method method=setMethods.get(columns.get(i));
				if(method != null){
					method.invoke(bean, new Object[]{ConvertUtils.convert(value, method.getParameterTypes()[0])});
					continue;
				}
				method=setMethods.get("foreign");
				if(method!=null){
					Class cla=method.getParameterTypes()[0];
					Object bean2=cla.newInstance();
					java.lang.reflect.Field f=cla.getDeclaredFields()[0];
					
					Method[] ms=cla.getDeclaredMethods();
					for(Method m:ms){
						if(m.getName().startsWith("set")&&m.getName().substring(3).equalsIgnoreCase(f.getName())){
							m.invoke(bean2, new Object[]{ConvertUtils.convert(value, m.getParameterTypes()[0])});
						}
					}
					method.invoke(bean, new Object[]{bean2});
				}
			}

			objs.add(bean);
		}
		return objs;
	}

	public static void main(String[] args) {
		PropertySetter ps=new PropertySetter(Field.class);
		Method[] ms=User.class.getDeclaredMethods();
		for(Method m:ms){
//			if(m.getName().startsWith("set"))
//			System.out.println(m.getParameterTypes()[0]+":"+PropertySetter.checkIsNormal(m));
		}
	}
}
