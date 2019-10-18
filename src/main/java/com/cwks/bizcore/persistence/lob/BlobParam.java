package com.cwks.bizcore.persistence.lob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

//import com.css.sword.cssnj.bizcore.persistence.DbUtils;
//import com.css.sword.platform.comm.log.LogFactory;
//import com.css.sword.platform.comm.log.LogWritter;

/**
 * 构造的BLOB参数，用于输入，输入可以传入两种参数，byte[]或Inputstream，</br>
 * 通过这两种参数之一（优先判断Inputstream），构造Oracle的BLOB对象，将对象作为存储过程参数传给数据库
  <br/>
  方法：
  BlobParam param = new BlobParam();
  param.setIs(inputStream);
  <br/>
  <br/>备注</br>
 【输出获取的是sun.jdbc.rowset.SerialBlob类型，该类型的public byte[] getBuf();】</br>	
 【方法可以从数据库得到byte数组，就是文件的内存格式】
 * @author Administrator
 *
 */
public class BlobParam implements java.io.Serializable{
	//private final static LogWritter logger = LogFactory.getLogger(BlobParam.class);
	private static Logger logger = LoggerFactory.getLogger(BlobParam.class);
	private InputStream is;
	private byte[] bytes;
	public InputStream getIs() {
		return is;
	}
	public void setIs(InputStream is) {
		this.is = is;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
//	public Blob getBlob(Connection conn){
//		//新建了一个引用
//		Connection con;
//		try {
//			con = DbUtils.getPrimaryConnection(conn);
//		} catch (Exception e) {
//			throw new RuntimeException("获取原生链接失败!!",e);
//		}
//		if(is==null && bytes==null ){
//			logger.error("BlobParam没有任何值，无法构造blob对象，请调用setIs或setBytes方法绑定数据!!");
//			//throw new RuntimeException("BlobParam参数构造出错!!");
//			try {
//				return BLOB.createTemporary(con, true, BLOB.DURATION_SESSION);
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				//e.printStackTrace();
//				throw new RuntimeException("构造空BLOB对象出错!!");
//			}
//		}
//		
//		byte[] data = null;
//		if(is!=null){
//			ByteArrayOutputStream btos = new ByteArrayOutputStream();
//
//			try {
//				while (true) {
//					byte[] bytes = new byte[1024 * 8];
//					int length = is.read(bytes);
//					if (length == -1) {
//						break;
//					}
//					btos.write(bytes, 0, length);
//				}
//				data = btos.toByteArray();
//			} catch (Exception e) {
//				//logger.debug("构造");
//				throw new RuntimeException("输入流转成byte数组出错!!",e);
//			}
//			
//		}else{
//			//传入了字符数组
//			data = bytes;
//		}
//		
//		BLOB tempBlob = null;
//		try {
//			OutputStream tempBlobOutputStream = null;
//			tempBlob = BLOB.createTemporary(con, true,
//					BLOB.DURATION_SESSION);
//			try {
//				tempBlob.open(BLOB.MODE_READWRITE);
//				tempBlobOutputStream = tempBlob.getBinaryOutputStream();
//
//				tempBlobOutputStream.write((byte[]) data);
//				tempBlobOutputStream.flush();
//			} finally {
//				if (tempBlobOutputStream != null)
//					tempBlobOutputStream.close();
//				if (tempBlobOutputStream != null) {
//					tempBlobOutputStream.close();
//				}
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			throw new RuntimeException("通过byte数组构造Blob对象出错!!!",e);
//		}
//		return tempBlob;
//	}
	
}
