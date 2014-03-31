/*
 * FileName:Test.java, v1.0 2009-5-15 ����07:44:34 created by Administrator
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
 * finallygo�Ĺ���
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
	 * ���������pojo�������ļ�,pojoҪ��Сд
	 * @param inputPojo
	 */
	public static boolean build(String inputPojo) {
		String filepath=buildPath+basePaceName;
		String daopath,daoImplpath,servicepath,serviceImplpath,pojopath;
		
		String className;
		File[] files=new File("mode").listFiles();
		Map<String,File> map=new HashMap<String, File>();
		for(File file:files){
			if(!file.getName().endsWith(".java")){//�������java��׺��ֱ������
				continue;
			}
			if(file.getName().endsWith("Dao.java")){
				//�����dao�ӿ�
				map.put("Dao", file);
			}else if(file.getName().endsWith("DaoImpl.java")){
				//�����dao��ʵ����
				map.put("DaoImpl", file);
			}else if(file.getName().endsWith("Service.java")){
				//�����service�ӿ�
				map.put("Service", file);
			}else if(file.getName().endsWith("ServiceImpl.java")){
				//�����service��ʵ����
				map.put("ServiceImpl", file);
			}else if(file.getName().endsWith("Page.java")){
				//�����page����
				map.put("Page", file);
			}
		}

			//�����dao�ӿ�
			daopath=filepath+"/dao";
			buildFile(inputPojo,map.get("Dao"),daopath);
				
			className=inputPojo.substring(0, 1).toUpperCase()+inputPojo.substring(1)+"Dao";
			buildConfig(BUNDLE.getString("basePackName")+"/dao",className,false,0);

			//�����dao��ʵ����
			daoImplpath=filepath+"/dao/impl";
			buildFile(inputPojo,map.get("DaoImpl"),daoImplpath);
				
			className=inputPojo.substring(0, 1).toUpperCase()+inputPojo.substring(1)+"DaoImpl";
			buildConfig(BUNDLE.getString("basePackName")+"/dao/impl",className,true,0);

			//�����service�ӿ�
			servicepath=filepath+"/service";
			buildFile(inputPojo,map.get("Service"),servicepath);
				
			className=inputPojo.substring(0, 1).toUpperCase()+inputPojo.substring(1)+"Service";
			buildConfig(BUNDLE.getString("basePackName")+"/service",className,false,1);

			//�����service��ʵ����
			serviceImplpath=filepath+"/service/impl";
			buildFile(inputPojo,map.get("ServiceImpl"),serviceImplpath);

			className=inputPojo.substring(0, 1).toUpperCase()+inputPojo.substring(1)+"ServiceImpl";
			buildConfig(BUNDLE.getString("basePackName")+"/service/impl",className,true,1);

			//�����page����
			pojopath=filepath+"/pojo";
			buildFile(inputPojo,map.get("Page"),pojopath);
			
			return true;
	}
	/**
	 * ����ģ��������Ӧ���ļ�
	 * @param pojoName �û������pojo��,Ҫ��Сд
	 * @param file ģ���е�һ���ļ�
	 * @param filePath Ҫ���ɵ��ļ���·��
	 */
	public static void buildFile(String pojoName,File file,String filePath){
		String changeName=pojoName.substring(0, 1).toUpperCase()+pojoName.substring(1);
		String fileName=file.getName();
		PrintWriter pw=null;
		try {
			File createFile=new File(filePath);
			createFile.mkdirs();
			if(fileName.equals("Page.java")){
				pw=new PrintWriter(createFile+"/"+fileName);//����ļ�
			}else{
				pw=new PrintWriter(createFile+"/"+changeName+fileName.substring(4));//����ļ�
			}
			//�������
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
	 * ����ģ�����������ļ�
	 * @param base ���A���� 
	 * @param className ����
	 * @param isNextLine �Ƿ���
	 * @param flag 0��ʾdao 1��ʾservice
	 */
	public static void buildConfig(String base,String className,boolean isNextLine,int flag){
			//���Д��Ƿ��Ѓ���
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
