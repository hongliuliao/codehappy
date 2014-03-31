/*
 * FileName:BuildJsp.java, v1.0 2009-6-19 上午08:05:05 created by finallygo
 *
 * Copyright (c) 2009 Wance 
 * All Rights Reserved.
 * Confidential and for internal user only.
 */

package com.finallygo.tools;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Vector;

import com.finallygo.common.CommonData;
import com.finallygo.pojo.WebField;

/**
 * 生成增加,编辑,按类表显示pojo信息的jsp页面
 *
 * @author finallygo
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class BuildJsp {
	public final static int EDIT=0;
	public final static int SHOW=1;
	public final static int SHOW_ONE=2;
	
	public static void printHead(PrintWriter pw){
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<title>");
		pw.println("this is my html");
		pw.println("</title>");
		pw.println("</head>");
		pw.println("<body>");
	}
	public static void printEnd(PrintWriter pw){
		pw.println("</body>");
		pw.println("</html>");
		pw.flush();
		pw.close();
	}
	/**
	 * 创建增加与编辑页面
	 * @param webFields 用于显示网页中的字段集合
	 */
	public static void buildEditJsp(Vector<WebField> webFields){
		try {
			PrintWriter pw=new PrintWriter(CommonData.webRoot+"edit.html");
			printHead(pw);
			pw.println("<form action='' method='post'>");
			for (WebField f : webFields) {
				//判断控件类型
				if(f.getWebType().equalsIgnoreCase("checkbox")){
					pw.println("<input type='"+f.getWebType()+"' name='"+BuildPojo.change(f.getFieldName()).toLowerCase()+"'>");
					pw.println(f.getChineseName()+"<br />");
				}else if(f.getWebType().equalsIgnoreCase("select")){
					pw.println("<select name='"+BuildPojo.change(f.getFieldName()).toLowerCase()+"'>");
					pw.println("</select>");
				}else{
					if(!f.getWebType().equalsIgnoreCase("hidden")){
						pw.println(f.getChineseName()+":");
					}
					pw.println("<input type='"+f.getWebType()+"' name='"+BuildPojo.change(f.getFieldName()).toLowerCase()+"'><br />");
				}
			}
			pw.println("<input type='submit' value='提交'>");
			pw.println("</form>");
			printEnd(pw);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 创建显示pojo列表的页面
	 * @param webFields 用于显示网页中的字段集合
	 */
	public static void buildShowJsp(Vector<WebField> webFields){
		try {
			PrintWriter pw=new PrintWriter(CommonData.webRoot+"show.jsp");
			String jspHead="<%@ page language='java' import='java.util.*' pageEncoding='gbk'%>";
			String jspTaglib="<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>";
			pw.println(jspHead);
			pw.println(jspTaglib);
			printHead(pw);
			pw.println("<table border='1'>");
			pw.println("<tr>");
			for (WebField f : webFields) {
				pw.println("<td>");
				pw.println(f.getChineseName());
				pw.println("</td>");
			}
			pw.println("</tr>");
			
			pw.println("<c:forEach item='${requestScope.list}' var='v'>");
			pw.println("<tr>");
			for (WebField f : webFields) {
				pw.println("<td>");
				pw.println("${v."+BuildPojo.change(f.getFieldName())+"}");
				pw.println("</td>");
			}
			pw.println("</tr>");
			pw.println("</c:forEach>");
			
			pw.println("</table>");
			pw.println("<%@include file='mypage.jsp' %>");
			printEnd(pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 创建显示单个pojo信息的网页
	 * @param webFields 用于显示网页中的字段集合
	 */
	public static void buildShowOneJsp(Vector<WebField> webFields){
		try {
			PrintWriter pw=new PrintWriter(CommonData.webRoot+"show_one.html");
			printHead(pw);
			for (WebField f : webFields) {
				pw.println(f.getChineseName());
				pw.println("${pojo."+BuildPojo.change(f.getFieldName())+"}");
				pw.println("<br />");
			}
			printEnd(pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
