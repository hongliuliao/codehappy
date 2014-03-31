/*
 * FileName:Test.java, v1.0 2009-5-15 下午07:44:34 created by Administrator
 *
 * Copyright (c) 2009 Wance 
 * All Rights Reserved.
 * Confidential and for internal user only.
 */

package com.finallygo.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * finallygo的工具
 *
 * @author finallygo
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class FinTools {

	/**
	 * @param args
	 */
	private static ResourceBundle BUNDLE = ResourceBundle.getBundle("config");
	public static String buildPath=ResourceBundle.getBundle("config").getString("buildPath");
	public static String basePaceName=BUNDLE.getString("basePackName");
	public static String modePojoName="news";
	public static String modeChangeName=modePojoName.substring(0, 1).toUpperCase()+modePojoName.substring(1);
	static {
		try {
			File createFile=new File(buildPath);
			createFile.mkdirs();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据输入的pojo名生成文件,pojo要求小写
	 * @param inputPojo
	 */
	public static boolean build(String inputPojo) {
		String filepath=buildPath+basePaceName;
		String daopath,daoImplpath,servicepath,serviceImplpath,pojopath;
		
		String className;
		File[] files=new File("mode").listFiles();
		Map<String,File> map=new HashMap<String, File>();
		for(File file:files){
			if(!file.getName().endsWith(".java")){//如果不是java后缀的直接跳过
				continue;
			}
			if(file.getName().endsWith("Dao.java")){
				//如果是dao接口
				map.put("Dao", file);
			}else if(file.getName().endsWith("DaoImpl.java")){
				//如果是dao的实现类
				map.put("DaoImpl", file);
			}else if(file.getName().endsWith("Service.java")){
				//如果是service接口
				map.put("Service", file);
			}else if(file.getName().endsWith("ServiceImpl.java")){
				//如果是service的实现类
				map.put("ServiceImpl", file);
			}else if(file.getName().endsWith("Page.java")){
				//如果是page對象
				map.put("Page", file);
			}
		}

			//如果是dao接口
			daopath=filepath+"/dao";
			buildFile(inputPojo,map.get("Dao"),daopath);
				
			className=inputPojo.substring(0, 1).toUpperCase()+inputPojo.substring(1)+"Dao";
			buildConfig(BUNDLE.getString("basePackName")+"/dao",className,false,0);

			//如果是dao的实现类
			daoImplpath=filepath+"/dao/impl";
			buildFile(inputPojo,map.get("DaoImpl"),daoImplpath);
				
			className=inputPojo.substring(0, 1).toUpperCase()+inputPojo.substring(1)+"DaoImpl";
			buildConfig(BUNDLE.getString("basePackName")+"/dao/impl",className,true,0);

			//如果是service接口
			servicepath=filepath+"/service";
			buildFile(inputPojo,map.get("Service"),servicepath);
				
			className=inputPojo.substring(0, 1).toUpperCase()+inputPojo.substring(1)+"Service";
			buildConfig(BUNDLE.getString("basePackName")+"/service",className,false,1);

			//如果是service的实现类
			serviceImplpath=filepath+"/service/impl";
			buildFile(inputPojo,map.get("ServiceImpl"),serviceImplpath);

			className=inputPojo.substring(0, 1).toUpperCase()+inputPojo.substring(1)+"ServiceImpl";
			buildConfig(BUNDLE.getString("basePackName")+"/service/impl",className,true,1);

			//如果是page對象
			pojopath=filepath+"/pojo";
			buildFile(inputPojo,map.get("Page"),pojopath);
			
			return true;
	}
	/**
	 * 根据模板生成相应的文件
	 * @param pojoName 用户输入的pojo名,要求小写
	 * @param file 模板中的一个文件
	 * @param filePath 要生成的文件的路径
	 */
	public static void buildFile(String pojoName,File file,String filePath){
		String changeName=pojoName.substring(0, 1).toUpperCase()+pojoName.substring(1);
		String fileName=file.getName();
		PrintWriter pw=null;
		try {
			File createFile=new File(filePath);
			createFile.mkdirs();
			if(fileName.equals("Page.java")){
				pw=new PrintWriter(createFile+"/"+fileName);//输出文件
			}else{
				pw=new PrintWriter(createFile+"/"+changeName+fileName.substring(4));//输出文件
			}
			//输出包名
			pw.println("package "+filePath.substring(buildPath.length()).replaceAll("/", ".")+";\r\n");
			pw.println("import "+basePaceName.replaceAll("/", ".")+".dao.*;\r\n");
			pw.println("import "+basePaceName.replaceAll("/", ".")+".service.*;\r\n");
			pw.println("import "+basePaceName.replaceAll("/", ".")+".pojo.*;\r\n");
			pw.println("import com.finallygo.dbutil.*;\r\n");
			BufferedReader br=new BufferedReader(new FileReader(file));
			String line=null;
			while((line=br.readLine())!=null){
				line=line.replaceAll(modeChangeName, changeName);
				line=line.replaceAll(modePojoName, pojoName);
				pw.println(line);
			}
			pw.flush();
			pw.close();
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据模板生成配置文件
	 * @param base 基礎包名 
	 * @param className 类名
	 * @param isNextLine 是否换行
	 * @param flag 0表示dao 1表示service
	 */
	public static void buildConfig(String base,String className,boolean isNextLine,int flag){
			//先判斷是否有內容
			File file=null;
			if(flag==0){
				file=new File(buildPath+"dao.properties");
			}else if(flag==1){
				file=new File(buildPath+"service.properties");
			}
			FileOutputStream fos=null;
			try {
				fos=new FileOutputStream(file,true);
				if(isNextLine){
					fos.write((base.replaceAll("/", ".")+"."+className+"\r\n").getBytes());
				}else{
					fos.write((base.replaceAll("/", ".")+"."+className+"=").getBytes());
				}
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}


}
