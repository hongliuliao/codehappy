package com.finallygo.tools;

import java.io.PrintWriter;
import java.util.Vector;

import com.finallygo.pojo.Field;

public class BuildSQL {
	
	private static String path=FinTools.buildPath;
	
	public static void buildSQL(Vector<Field> fields,String pojoName){
		String sql="create table tb_"+pojoName+"(\r\n";
		for(int i=0;i<fields.size();i++){
			if(i==0){
				Field f=fields.get(0);
				sql=sql+f.getFieldName()+" "+f.getFieldType();
				sql=sql+" IDENTITY (1, 1) primary key,\r\n";
				continue;
			}
			sql=sql+buildOne(fields.get(i));
		}
		sql=sql+");";
		try {
			PrintWriter pw=new PrintWriter(path+"/"+pojoName+".sql");
			pw.println(sql);
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String buildOne(Field f){
		String sql=null;
		if(f.getFieldType().equals("varchar")){
			sql=f.getFieldName()+" "+f.getFieldType()+"("+f.getFieldLength()+") ";
		}else{
			sql=f.getFieldName()+" "+f.getFieldType()+" ";
		}
		String isNull=f.isNull()?"null":"not null";
		sql=sql+isNull+",\r\n";
		return sql;
	}
}
