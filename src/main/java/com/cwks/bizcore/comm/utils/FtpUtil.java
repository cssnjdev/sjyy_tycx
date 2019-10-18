package com.cwks.bizcore.comm.utils;

import com.cwks.common.core.systemConfig.BizContext;
import com.cwks.common.util.StringUtils;
import it.sauronsoftware.ftp4j.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ftp4j 文件处理工具类
 */
public class FtpUtil {

    FTPClient ftpClient = null;

    private String hostname = "";
    private int port = 21;
    private String username = "";
    private String password = "";

    public FtpUtil(String fileType) {
        if(fileType!=null&&!"".equals(fileType)){
            fileType = fileType.toLowerCase();
            this.hostname = StringUtils.stringTrim(BizContext.singleton().getValueAsString("biz.mobile.resource.server."+fileType+".ftp.ip"));
            String p = StringUtils.stringTrim(BizContext.singleton().getValueAsString("biz.mobile.resource.server."+fileType+".ftp.port"));
            if(p!=null&&!"".equals(p)){
                this.port = Integer.parseInt(p);
            }
            this.username = StringUtils.stringTrim(BizContext.singleton().getValueAsString("biz.mobile.resource.server."+fileType+".ftp.username"));
            this.password = StringUtils.stringTrim(BizContext.singleton().getValueAsString("biz.mobile.resource.server."+fileType+".ftp.password"));
        }
    }

    public void openConnection(){
        ftpClient = new FTPClient();
        try{
            ftpClient.connect(hostname, port);
            ftpClient.login(username, password);
        }catch(Exception e){
            ftpClient = null;
        }
    }

    public void closeConnection() {
        if ( null != ftpClient && ftpClient.isConnected()) {
            try {
                ftpClient.disconnect(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean download(String fileName, String ftpUploadPath) {
        boolean flag = true;
        try {
            ftpUploadPath=ftpUploadPath+ File.separator + fileName;
            File uploadFile = new File(ftpUploadPath);
            if (!uploadFile.exists()) {
                uploadFile.mkdir();
            }
            openConnection();
            ftpClient.setType(FTPClient.TYPE_BINARY);
            ftpClient.download(fileName,uploadFile);
            closeConnection();
        }catch (IOException e){
            flag = false;
        } catch (FTPAbortedException e) {
            flag = false;
        }  catch (FTPException e) {
            flag = false;
        } catch (FTPDataTransferException e) {
            flag = false;
        } catch (FTPIllegalReplyException e) {
            flag = false;
        } catch (Exception e) {
        }
        return flag;
    }


    public boolean upload(String fileName, byte[] buf,String uploadPath) {
        boolean flag = true;
        InputStream inputStream = new ByteArrayInputStream(buf);
        try {
            openConnection();
            if(uploadPath!=null && !"".equals(uploadPath)){
            	//try {
               // FTPFile[] files = ftpClient.list();
            	//} catch (Exception e) {
            		//e.printStackTrace();
            	//}
//                for (int i = 0; i < files.length; i++) {
//                    if (files[i].getType()==1 && !files[i].getName().equalsIgnoreCase(".")
//                            && !files[i].getName().equalsIgnoreCase("..")) {
//                        if(files[i].getName().equals(uploadPath)){
//                            flag = false;
//                            break;
//                        }
//                    }
//                }
                if(flag){
                    String[] path = uploadPath.split("/");
                    for (int i = 0; i < path.length; i++) {
                        try{
                            ftpClient.changeDirectory(path[i]);
                        }catch(Exception e){
                        	//e.printStackTrace();
                            ftpClient.createDirectory(path[i]);
                            ftpClient.changeDirectory(path[i]);
                        }
                    }
                }
            }
            ftpClient.setType(FTPClient.TYPE_BINARY);
            ftpClient.upload(fileName,inputStream,0L,0L,null);
            closeConnection();
        } catch (IOException e) {
            flag = false;
        } catch (FTPIllegalReplyException e) {
            flag = false;
        } catch (FTPException e) {
            flag = false;
        } catch (FTPDataTransferException e) {
            flag = false;
        } catch (FTPAbortedException e) {
            flag = false;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
    
    public boolean delete(String fileName) {
        boolean flag = true;
        try {
            openConnection();
            ftpClient.deleteFile(fileName);
            closeConnection();
        } catch (IOException e) {
            flag = false;
        } catch (FTPIllegalReplyException e) {
            flag = false;
        } catch (FTPException e) {
            flag = false;
        }catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static void main(String[] args) {
        String datePath = (new SimpleDateFormat("yyyyMMdd")).format(new Date());
        str2HexStr(datePath);
    }

    public static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++){
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString().trim();
    }

}
