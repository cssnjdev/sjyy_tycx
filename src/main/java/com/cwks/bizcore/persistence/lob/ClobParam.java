package com.cwks.bizcore.persistence.lob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import com.css.sword.platform.comm.log.LogFactory;
//import com.css.sword.platform.comm.log.LogWritter;


/**
 * 构造的CLOB参数，用于输入，将字符串转换成clob对象</br>
 
  <br/>
  方法：
  ClobParam param = new ClobParam("千秋万载一统江湖");
  
  <br/>
  <br/>备注</br>
 【输出获取的是sun.jdbc.rowset.SerialBlob类型，该类型的public byte[] getBuf();】</br>	
 【方法可以从数据库得到byte数组，就是文件的内存格式】
 * @author Administrator
 *
 */
public class ClobParam implements java.io.Serializable{
//	private final static LogWritter logger = LogFactory.getLogger(ClobParam.class);
	private static Logger logger = LoggerFactory.getLogger(ClobParam.class);
	private String data;
	
	
	
	public ClobParam() {
	}

	public ClobParam(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

//	public Clob getClob(Connection conn){
		//新建了一个引用
//		Connection con;
//		try {
//			con = DbUtils.getPrimaryConnection(conn);
//		} catch (Exception e) {
//			throw new RuntimeException("获取原生链接失败!!",e);
//		}
		
//		if(data == null){
//			logger.error("没有任何数据，你想转成Clob对象吗？");
//			//throw new RuntimeException("ClobParam参数构造出错!!");
//			try {
//				return CLOB.createTemporary(con, true, CLOB.DURATION_SESSION);
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				throw new RuntimeException("构造空CLOB对象出错!!");
//			}
//		}
//		try {
//			CLOB tempClob = CLOB.createTemporary(con, true,
//					CLOB.DURATION_SESSION);
//			// call the getCharacterOutpitStream method
//			Writer tempClobWriter = tempClob.getCharacterOutputStream();//(Writer) getCharacterOutputStreamMethod.invoke(tempClob, null);
//			// write the string to the clob
//			tempClobWriter.write(data);
//			tempClobWriter.flush();
//			tempClobWriter.close();
//			return tempClob;
//		} catch (Exception e) {
//			// TODO: handle exception
//			throw new RuntimeException("通过字符串构造Clob对象失败!!",e);
//		}
//	}

}
