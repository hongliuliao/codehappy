package com.finallygo.tools;

import java.io.File;
import java.io.PrintWriter;
import java.util.Vector;

import com.finallygo.pojo.Field;

public class BuildPojo {
	
	public static boolean buildPojo(Vector<Field> fields,String pojoName){
		StringBuffer pojoStr=new StringBuffer();
		pojoStr=pojoStr.append("package "+FinTools.basePaceName.replaceAll("/",".")+".pojo;\r\n");
		pojoStr=pojoStr.append("import java.sql.Date;\r\n");
		pojoStr=pojoStr.append("public class "+pojoName.substring(0,1).toUpperCase()+pojoName.substring(1)+" {\r\n");
		boolean create=false;
		for(Field f:fields){
			if(f.getFieldType().equals("int")){
//				if(f.getFieldName().startsWith(pojoName)){
					pojoStr.append("private Integer "+change(f.getFieldName())+";\r\n");
//				}else{
//					pojoStr.append("private "+change2(f.getFieldName())+" "+change2(f.getFieldName()).toLowerCase()+";\r\n");
//					create=true;
//				}
			}else if(f.getFieldType().equals("varchar")){
				pojoStr.append("private String "+change(f.getFieldName())+";\r\n");
			}else if(f.getFieldType().equals("datetime")){
				pojoStr.append("private Date "+change(f.getFieldName())+";\r\n");
			}
		}
		pojoStr=pojoStr.append("}");
		try {
			File file=new File(FinTools.buildPath+"/"+FinTools.basePaceName+"/pojo");
			file.mkdirs();
			PrintWriter pw=new PrintWriter(file+"/"+pojoName.substring(0,1).toUpperCase()+pojoName.substring(1)+".java");
			pw.println(pojoStr);
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return create;
	}
	/**
	 * 将类似user_name改成userName
	 * 如果没有下划线就将首字母大写
	 * @param str 要转换的字符串
	 * @return 转换后的字符串
	 */
	public static String change(String str){
		int pos=str.indexOf("_");
		if(pos!=-1){
			str=str.substring(0,pos)+str.substring(pos+1,pos+2).toUpperCase()+str.substring(pos+2);
		}else{
			str=str.substring(pos+1,pos+2).toUpperCase()+str.substring(pos+2);
		}
		return str;
	}
	public static String change2(String str){
		int pos=str.indexOf("_");
		str=str.substring(0,pos).substring(0,1).toUpperCase()+str.substring(0,pos).substring(1);
		return str;
	}
	public static void main(String[] args) {
		BuildPojo b=new BuildPojo();
		File file=new File(FinTools.buildPath+"/"+FinTools.basePaceName+"/pojo/");
		System.out.println(file.getPath());
	}
}
