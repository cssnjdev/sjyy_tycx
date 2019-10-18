package com.cwks.bizcore.tycx.core.utils;

import com.raqsoft.common.UUID;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.SocketException;

public class FtpFileUtil {

	/**
	 * 
	 * 
	 * @param ip			ip地址(必需)
	 * @param port			端口(必需)
	 * @param userName		账户(必需)
	 * @param password		密码
	 * @param uploadPath	文件存放路径
	 * @param filePath		本地文件路径
	 * @return success		0:成功；1：失败；2：服务器连接失败
	 */
	public String FTPUpload(String ip,Integer port,String userName,String password,String uploadPath,String filePath){

		FTPClient ftp=new FTPClient();
		InputStream local=null;
		String success="1";
		int reply;
		 try {
			 //连接到ftp服务器
			 ftp.connect(ip,port);
			 //登录
			 ftp.login(userName, password);
			 reply=ftp.getReplyCode();
			 if(!FTPReply.isPositiveCompletion(reply)){
				 ftp.disconnect();
				 success="2";
				 return success;
			 }
			 //设置上传路径
			 String path=uploadPath;
			 //检查上传路径是否存在，如果不存在返回false
			 boolean flag=ftp.changeWorkingDirectory(path);
			 if(!flag){
				 //创建上传路径，该方法只能创建一级目录，在这里如果/home存在则创建test1
				 ftp.makeDirectory(path);
			 }
			 //指定上传路径
			 ftp.changeWorkingDirectory(path);
			 //指定上传文件类型，二进制文件
			 ftp.setFileType(FTP.BINARY_FILE_TYPE);
			 //传输的字符编码
			 ftp.setControlEncoding("UTF-8");
			 //连接超时
//			 ftp.setConnectTimeout(60*1000);
			 //传输超时
//			 ftp.setDataTimeout(60*1000);
//			 ftp.setDefaultTimeout(60*1000);
			 //读取本地文件
			 File file=new File(filePath);
			 local=new FileInputStream(file);
			 //第一个参数是文件名
			 String fileName=getFileName(file.getName());
			 ftp.storeFile(fileName, local);
			 success="0"+fileName;
//			 success="0"+file.getName();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(ftp.isConnected()){
				try {
					//关闭文件流
					local.close();
					//退出
					ftp.logout();
					//断开连接
					ftp.disconnect();
					return success;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		 return success;
	}
	
	/**
	 * 
	 * @param ip			ip地址(必需)
	 * @param port			端口(必需)
	 * @param userName		账户(必需)
	 * @param password		密码
	 * @param downloadPath	文件所在服务器路径
	 * @param fileName		服务器保存文件名称
	 * @param fileMc		文件原名称
	 * @param localPath		下载路径
	 * @return success		0:成功；1:失败；
	 */
	public String FTPDownload(String ip,Integer port,String userName,String password,String downloadPath,String fileName,String fileMc,String localPath){
		String success="1";
		FTPClient ftp=new FTPClient();
		OutputStream os=null;
		int reply;
		try {
			ftp.connect(ip,port);
			ftp.login(userName, password);
			reply=ftp.getReplyCode();
			if(!FTPReply.isPositiveCompletion(reply)){
				ftp.disconnect();
				return success;
			}
			//检查下载路径是否存在，如果不存在返回false
			File fileExist=new File(localPath);
			 boolean flag=fileExist.exists();
			 if(!flag){
				 //创建下载路径，该方法只能创建一级目录
				 fileExist.mkdir();
			 }
			ftp.changeWorkingDirectory(downloadPath);
			FTPFile[] files=ftp.listFiles();
			for(FTPFile file:files){
				if(file.getName().equals(fileName)){
					File localFile=new File(localPath+"\\"+fileMc);
					os=new FileOutputStream(localFile);
					ftp.retrieveFile(fileName, os);
					success="0";
					break;
				}
			}
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(ftp.isConnected()){
				try {
					//关闭文件流
					os.close();
					//退出
					ftp.logout();
					//断开连接
					ftp.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return success;
	}
	
	private String getFileName(String fileName){
		String fileNameUUID= UUID.randomUUID().toString();
		String suffix=fileName.substring(fileName.lastIndexOf("."));
		return fileNameUUID+suffix;
	}
}
