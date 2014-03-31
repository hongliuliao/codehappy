/*
 * FileName:CommonData.java, v1.0 Jun 19, 2009 12:28:12 PM created by finallygo
 *
 * Copyright (c) 2009 Wance 
 * All Rights Reserved.
 * Confidential and for internal user only.
 */

package com.finallygo.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * 该类存储一些常用的公有的数据
 *
 * @author finallygo
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class CommonData {
	public static ResourceBundle BUNDLE = ResourceBundle.getBundle("config");//配置文件
	public static String buildPath=ResourceBundle.getBundle("config").getString("buildPath");//构建路径
	public static int pos;
	static{
		pos=buildPath.indexOf("/src/");
	}
	public static String webRoot=buildPath.substring(0,pos)+"/webRoot/";
	private static Map<String, String> nameMap=new HashMap<String, String>();
	static{
		nameMap.put("user_name", "用户名");
		nameMap.put("password", "密码");
		nameMap.put("gender", "性别");
		nameMap.put("age", "年龄");
		nameMap.put("date", "日期");
		nameMap.put("name", "名字");
		nameMap.put("brith", "生日");
	}
	public static String getSmartName(String inputName){
		Set<String> names=nameMap.keySet();
		for (String name : names) {
			if(inputName.indexOf(name)!=-1){
				return nameMap.get(name);
			}
		}
		return "";
		
	}
	public static void main(String[] args) throws Exception {
		Class.forName("com.ncr.teradata.TeraDriver");
		Connection conn=DriverManager.getConnection("jdbc:odbc:tdds");
	}
}
