/**
 * FileName:DaoFactory.java,v 1.0 created in 2008-11-5 ����02:44:00
 * Created by sean
 * Copyright (c) 2008 wanczy
 * All Rights Reserved.
 */
package com.finallygo.dbutil;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Dao�Ĺ�������ȡ��Ŀ¼�µ�dao.properties�ļ��������ļ��нӿ�������ʵ����ȫ�޶����Ķ�Ӧ��ϵ����ʵ����
 * @author sean
 * @version $Revision: 1.1 $
 * @since 1.0
 */
public class DaoFactory {
	private static ResourceBundle BUNDLE = ResourceBundle
			.getBundle("dao");

	private static Map<Class, BaseDao> daos = new HashMap<Class, BaseDao>();
	private static final JDBCTemplate template=new JDBCTemplate();
	public static BaseDao getDao(Class interfaceClass) {
		BaseDao dao = daos.get(interfaceClass);
		if (dao == null) {
			String interfaceName = interfaceClass.getName();// ȡ�ýӿ����ȫ�޶���
			String implClassName = BUNDLE.getString(interfaceName);// ͨ���ӿ����ȫ�޶���,��dao.properties�ļ���,���ҵ�ʵ�����ȫ�޶���
			// System.out.println(implClassName);
			try {
				Class clazz = Class.forName(implClassName);// ����ȫ�޶���ָ�������������ʵ��
				dao = (BaseDao) clazz.newInstance();// ͨ������ʵ������ָ�������һ��ʵ��
				dao.setTemplate(template);//ע��JDBCTemplate,���ڸ�DAO��ʼ�������ݿ�SQLִ����
				daos.put(interfaceClass, dao);//��ʵ������DAO�໺������,�Ա���һ�ε��õ�ʱ��ֱ��ȡ����ʹ��
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("�ӿ�ʵ����" + implClassName + "�Ҳ���", e);
			} catch (InstantiationException e) {
				throw new RuntimeException("�ӿ�ʵ����" + implClassName + "��ʼ������", e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException("�ӿ�ʵ����" + implClassName
						+ "����ʱ,����ĵ��÷�ʽ", e);
			}
		}
		return dao;
	}
}