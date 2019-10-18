package com.cwks.bizcore.comm.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

/**
 * 文档下载帮助类
 * <p>Title: FileTransUtil.java</p>
 * <p>Description: 文档下载帮助类 </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: cwks</p>
 * @author H.R
 * @version 1.0
 */
public class FileTransUtil {

    private static Logger logger = LoggerFactory.getLogger(FileTransUtil.class);

    public static String upload(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //允许上传的文件类型
        String fileType = "mp3,mp4,video,rmvb,pdf,txt,xml,doc,docx,xls,xlsx,pptx,ppt,gif,png,jpg,bmp,jpeg,zip,rar,html,exe,chm";
        //允许上传的文件最大大小(30M,单位为byte)
        int maxSize = 1024*1024*30;
        //设置返回的编码字符集
        response.setCharacterEncoding("UTF-8");
        // 文件上传
        MultipartHttpServletRequest mr = (MultipartHttpServletRequest) request;
        Iterator iter = mr.getFileMap().values().iterator();
        // 获取上传的文件
        MultipartFile file = (MultipartFile) iter.next();
        // 获得文件全名
        String fname = file.getOriginalFilename();
        //获取文件后缀名
        String extName = fname.substring(fname.indexOf(".") + 1).toLowerCase().trim();
        String ftype = file.getContentType();

        //获取前台传过来的参数
        String fileurl = "";
        //获取当天时间来创建文件夹
        String DatePath = new SimpleDateFormat("yyyyMMdd").format(new Date());  //获取时间
		String sessid = request.getSession().getId();  //获取时间
        String pathfile="";
        //保存文件路径
        if("".equals(fileurl)){
            //默认存放的地址 cssnjworks/WebRoot/WEB-INF/upload/file
            pathfile = request.getSession().getServletContext().getRealPath(File.separator)+ File.separator+"WEB-INF" + File.separator + "upload" + File.separator + "file" + File.separator + DatePath + File.separator + sessid;
            File filename = new File(pathfile);
            //新建时间文件夹
            if (!filename.exists()) {
                filename.mkdir();
            }
            pathfile=pathfile+ File.separator + fname;
        }else{
            //截取每个 . 之前的字母
            String[] names = fileurl.split("\\.");
            pathfile = names[0] + ":\\";
            for (int i = 1; i < names.length; i++) {
                pathfile = pathfile+names[i];
                //判断是否存在文件夹，不存在就新建
                File filename = new File(pathfile);
                if (!filename.exists()) {
                    filename.mkdir();
                }
                pathfile=pathfile+File.separator;
            }
            //创建最后的时间文件夹
            pathfile = pathfile+DatePath;
            File newfile = new File(pathfile);
            if (!newfile.exists()) {
                newfile.mkdir();
            }
            //获取保存文件的全部路径
            pathfile=pathfile+File.separator+fname;
        }

        String uploadflag="";
        //判断文件是否重命名
        File files = new File(pathfile);
        if(files.exists()){
            logger.debug("File name already exists. Please rename!");
            uploadflag = "falseName";
        }
        //判断文件大小是否超过30M
        else if(file.getSize() > maxSize){
            logger.debug("File size exceeds 30M!");
            uploadflag = "falseSize";
            //判断文件类型
        } else if(!Arrays.<String> asList(fileType.split(",")).contains(extName)){
            logger.debug("Unable to upload this file type!");
            uploadflag = "falseType";
        }else{
            try {
                //保存文件
                file.transferTo(new File(pathfile));
                uploadflag = "true";
            } catch (Exception e) {
                logger.error("Upload failed!");
            }
        }
        return uploadflag;
    }

    /**
     * 下载公共方法
     * @param file
     * @param filenames
     * @param response
     */
    public static void download(File file, String filenames,HttpServletResponse response,HttpServletRequest request) throws Exception{
        InputStream inputStream;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            response.reset();

            //判断浏览器解决中文乱码
            String codedfilename = null;
            try {
                String agent = request.getHeader("USER-AGENT");
                if (null != agent && -1 != agent.indexOf("MSIE") || null != agent
                        && -1 != agent.indexOf("Trident")) {// ie
                    String name = java.net.URLEncoder.encode(filenames, "UTF8");
                    codedfilename = name;
                } else if (null != agent && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等
                    codedfilename = new String(filenames.getBytes("UTF-8"), "iso-8859-1");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
            response.addHeader("Content-Disposition", "attachment;filename=" + codedfilename.replaceAll(" ", ""));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            os.write(buffer);// 输出文件
            os.flush();
            os.close();
        } catch (Exception e) {
            logger.debug("Downloaded file failed!");
        }
    }

}
