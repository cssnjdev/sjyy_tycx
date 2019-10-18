package com.cwks.bizcore.help;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;

public class ClobHelper {
	 
	public static String ClobToString(Clob clob) throws SQLException, IOException {
		if(clob==null){
			return "";
		}
		String reString = "";
		Reader is = clob.getCharacterStream();// 得到流
		BufferedReader br = new BufferedReader(is);
		String s = br.readLine();
		StringBuffer sb = new StringBuffer();
		while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
			sb.append(s + "\r\n");
			s = br.readLine();
		}
		reString = sb.toString();
		return reString;
	}
	
}
