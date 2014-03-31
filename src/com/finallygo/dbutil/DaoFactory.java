/**
 * FileName:DaoFactory.java,v 1.0 created in 2008-11-5 下午02:44:00
 * Created by sean
 * Copyright (c) 2008 wanczy
 * All Rights Reserved.
 */
package com.finallygo.dbutil;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Dao的工厂，读取根目录下的dao.properties文件，根据文件中接口类名与实现类全限定名的对应关系生成实例。
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
			String interfaceName = interfaceClass.getName();// 取得接口类的全限定名
			String implClassName = BUNDLE.getString(interfaceName);// 通过接口类的全限定名,到dao.properties文件中,查找到实现类的全限定名
			// System.out.println(implClassName);
			try {
				Class clazz = Class.forName(implClassName);// 建立全限定名指定的类的描述类实例
				dao = (BaseDao) clazz.newInstance();// 通过描述实例建立指定的类的一个实例
				dao.setTemplate(template);//注入JDBCTemplate,等于给DAO初始化了数据库SQL执行器
				daos.put(interfaceClass, dao);//将实例化的DAO类缓存起来,以便下一次调用的时候直接取出来使用
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("接口实现类" + implClassName + "找不到", e);
			} catch (InstantiationException e) {
				throw new RuntimeException("接口实现类" + implClassName + "初始化错误", e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException("接口实现类" + implClassName
						+ "构造时,错误的调用方式", e);
			}
		}
		return dao;
	}
}