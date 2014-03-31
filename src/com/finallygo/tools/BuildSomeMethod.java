package com.finallygo.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Vector;

import com.finallygo.pojo.Field;

public class BuildSomeMethod {

	// private static PrintWriter pw;

	public static String buildSave(Vector<Field> fields, String pojoName) {
		StringBuffer sql = new StringBuffer();
		sql = sql.append("insert into tb_" + pojoName + " values(");
		for (int i = 1; i < fields.size(); i++) {
			sql.append("?,");
		}
		sql = sql.deleteCharAt(sql.length() - 1);
		sql = sql.append(")");
		return sql.toString();
		// pw.println(sql);
		// pw.flush();
	}

	public static String buildUpdate(Vector<Field> fields, String pojoName) {
		StringBuffer sql = new StringBuffer();
		sql = sql.append("update tb_" + pojoName + " set ");
		for (int i = 1; i < fields.size(); i++) {
			sql.append(fields.get(i).getFieldName() + "=?,");
		}
		sql = sql.deleteCharAt(sql.length() - 1);
		sql = sql.append(" where " + fields.get(0).getFieldName() + "=?");
		return sql.toString();
		// pw.println(sql);
		// pw.flush();
	}

	public static void buildSaveAndUpdate(Vector<Field> fields, String pojoName) {
		try {
			String filePath = FinTools.buildPath + FinTools.basePaceName
					+ "/dao/impl/" + pojoName.substring(0,1).toUpperCase()+pojoName.substring(1) + "DaoImpl.java";
			File changefile = new File(filePath);
			FileInputStream fis = new FileInputStream(changefile);
			byte[] b = new byte[(int) changefile.length()];
			fis.read(b);
			String inputStr = new String(b);
			fis.close();
			inputStr=inputStr.replaceAll("fin_insert", buildSave(fields, pojoName));
			inputStr=inputStr.replaceAll("fin_update", buildUpdate(fields, pojoName));

			FileOutputStream fos = new FileOutputStream(changefile);
			fos.write(inputStr.getBytes());
			fos.close();
		} catch (Exception e) {
			throw new RuntimeException("“Ï≥£", e);
		}
	}
}
