package com.cwks.core.util;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.SaveOptions;
import com.cwks.common.api.dto.ext.RJson;
import com.cwks.common.util.db.BeanUtils;
import com.cwks.delegate.OfficeToPdfUtil;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import wsepr.easypoi.excel.Excel;
import wsepr.easypoi.excel.editor.IPrintSetup;
import wsepr.easypoi.excel.style.Align;
import wsepr.easypoi.excel.style.BorderStyle;
import wsepr.easypoi.excel.style.Color;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 文档下载帮助类
 * <p>Title: OfficeDownloadUtil.java</p>
 * <p>Description: 文档下载帮助类 </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: cssnj</p>
 * @author yyz
 * @version 1.0
 */
public class OfficeDownloadUtil {
    private static Logger logger = LoggerFactory.getLogger(OfficeDownloadUtil.class);

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

    /**
     * 导出Excel公共方法
     * @param ExcelMap
     * @param response
     * @param request
     * @param flag
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void exportExcle(Map ExcelMap,HttpServletRequest request,HttpServletResponse response,String flag) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        //===============表单title 正文第一行内容==========================
        String title = ExcelMap.get("fileName").toString();

        Map colMap = (Map) ExcelMap.get("colMap");
        //获取对于的bean的class clazz
        Class clazz = (Class) ExcelMap.get("class");
        List listContent = (List) ExcelMap.get("listContent");

        //单元格边框颜色
        Color borderColor = Color.GREY_50_PERCENT;
        Excel excel = new Excel();

        //================开始加载Excel内容=========================================
        Iterator<String> iterator = colMap.keySet().iterator();
        String colKey="";
        String value = "";
        String methodName;
        int headerLen=colMap.size();
        int perSheetMaxRows = 30000; //每个工作表最大行数
        int n = 0;//所有的行数
        boolean newSheet = false;//是否有新加的sheet页

        Method method;
        //保存并导出Excel
        String pathfile;
        int iteratorIndex = 0;
        List list = null;
        int endNum = 0;
        int sheetSie = (int)Math.ceil((double)listContent.size()/perSheetMaxRows);
        for(int jj=0;jj<sheetSie;jj++){
            if(jj==sheetSie-1){
                list = listContent.subList(perSheetMaxRows*jj,listContent.size());
            }else{
                list = listContent.subList(perSheetMaxRows*jj,perSheetMaxRows*(jj+1));
            }

            //设置表单信息
            excel.setWorkingSheet(jj)//设置第1个工作表为工作状态
                    .sheetName(title + jj)
                    .fitToPage(true)
                    .horizontallyCenter(true)
                    .printGridlines(false)
                    .displayGridlines(true)
                    .autobreaks(true)
                    .printSetup(new IPrintSetup() {
                        public void setup(HSSFPrintSetup printSetup) {
                            printSetup.setLandscape(true);//是否打印背景
                            printSetup.setFitHeight((short) 1);//调整缩放
                            printSetup.setFitWidth((short) 1);//调整缩放
                        }
                    });
            iterator = colMap.keySet().iterator();
            iteratorIndex=0;
            while (iterator.hasNext()) {
                n=0;
                colKey = iterator.next();
                //设置标题内容，标题行列合并，标题样式
                excel.row(0).height(30);
                excel.cell(0, 0).value(title)
                        .align(Align.CENTER);
                excel.region(0, 0, 0, headerLen - 1).merge();//合并标题的单元格
                //设置表头及样式
                excel.column(iteratorIndex).width(6000).warpText(false);
                excel.cell(1, iteratorIndex).value(colMap.get(colKey)).align(Align.CENTER).border(BorderStyle.THIN,Color.BLACK);
                //设置正文内容
                for (Object ssTopModel : list) {
                    //获取对于的bean的class
//                    if(clazz!=null){
//                        //保留传入pojo的方式，不然老版本会报错
//                        methodName = "get" + colKey.substring(0, 1).toUpperCase() + colKey.substring(1);
//                        method = clazz.getMethod(methodName);
//                        value = method.invoke(ssTopModel) == null ? "" : method.invoke(ssTopModel).toString();
//                    }else{
                        if(ssTopModel instanceof Map){
                            value = ((Map)ssTopModel).get(colKey) ==null?"":((Map)ssTopModel).get(colKey).toString();
                        }else{
                            value = BeanUtils.toMap(ssTopModel).get(colKey) ==null?"":BeanUtils.toMap(ssTopModel).get(colKey).toString();
                        }
//                    }
                    excel.cell(n + 2, iteratorIndex).value(value).align(Align.LEFT).border(BorderStyle.THIN,Color.BLACK).warpText(false);
                    methodName = null;
                    method = null;
                    value = null;
                    n++;
                }
                iteratorIndex++;
            }
            list= null;
        }

        //新建 Excel
        //1 为导出excel转pdf
        if("1".equals(flag)){
            //保存并导出Excel
            pathfile = request.getSession().getServletContext().getRealPath(File.separator)+ File.separator+"WEB-INF" + File.separator + "upload" + File.separator + "temporaryFile"+ File.separator + title+".xls";

            excel.saveExcel(pathfile);
            excel = null;
        }else {
            OutputStream out = response.getOutputStream();
            excel.saveExcel(out);
            out.flush();
            out.close();
            excel = null;
        }

    }

    /**
     * 导出word公共方法
     * @param content
     * @param request
     * @param response
     */
    public static void exportWord(String fileName, String content, HttpServletRequest request, HttpServletResponse response, String flag) throws Exception {
        if (OfficeToPdfUtil.getLicenseWord()) {
            try {
                Document doc = new Document();
                DocumentBuilder builder = new DocumentBuilder(doc);
                builder.insertHtml(content);
                request.setCharacterEncoding("utf-8");
                response.reset();
                response.setContentType("application/msword");
                String codedfilename = null;

                String wjUrl;
                try {
                    wjUrl = request.getHeader("USER-AGENT");
                    if ((wjUrl == null || -1 == wjUrl.indexOf("MSIE")) && (wjUrl == null || -1 == wjUrl.indexOf("Trident"))) {
                        if (wjUrl != null && -1 != wjUrl.indexOf("Mozilla")) {
                            codedfilename = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
                        }
                    } else {
                        String name = URLEncoder.encode(fileName, "UTF8");
                        codedfilename = name;
                    }
                } catch (Exception var10) {
                    var10.printStackTrace();
                }

                response.addHeader("Content-Disposition", "attachment;filename=" + codedfilename + ".doc");
                if ("1".equals(flag)) {
                    wjUrl = request.getSession().getServletContext().getRealPath(File.separator) + File.separator + "WEB-INF" + File.separator + "upload" + File.separator + "temporaryFile" + File.separator;
                    FileOutputStream ostream = new FileOutputStream(wjUrl + fileName + ".doc");
                    doc.save(ostream, SaveOptions.createSaveOptions(10));
                    ostream.flush();
                    ostream.close();
                } else {
                    OutputStream ostream = response.getOutputStream();
                    doc.save(ostream, SaveOptions.createSaveOptions(10));
                    ostream.flush();
                    ostream.close();
                }
            } catch (Exception var11) {
                logger.debug("导出出错： " + var11.getMessage());
            }

        }
    }

    /**
     * word 转PDF
     * @param fileName
     * @param content
     * @param request
     * @param response
     * @throws IOException
     */
    public static void Word2Pdf(String fileName,String content,HttpServletRequest request,HttpServletResponse response) throws IOException {
        String wjUrl = request.getSession().getServletContext().getRealPath(File.separator)+ File.separator+"WEB-INF" + File.separator + "upload" + File.separator + "temporaryFile" + File.separator;
        
        File wjUrlname = new File(wjUrl);
        //新建时间文件夹
        if (!wjUrlname.exists()) {
        	wjUrlname.mkdir();
        }
        
        String filepath=wjUrl+fileName+".doc";
        //生成pdf文件名
        String pdfname = fileName+".pdf";
        //生成pdf存放的目录
        String pdfFile = wjUrl + pdfname;
        PrintWriter printWriter = null;
        //生成word文件
        try {
            //执行生成word文件
            OfficeDownloadUtil officeDownloadUtil = new OfficeDownloadUtil();
            officeDownloadUtil.exportWord(fileName,content,request,response,"1");
        }catch(Exception e){
            logger.debug("导出出错： "+e.getMessage());
            printWriter = response.getWriter();
            printWriter.print(new RJson(false, "error:  "+e.getMessage()));
            printWriter.flush();
            printWriter.close();
        }
        //执行word转pdf方法
        String flag="";
        try {
            OfficeToPdfUtil office = new OfficeToPdfUtil();
            flag = office.doOffice(filepath,pdfFile);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            printWriter = response.getWriter();
            printWriter.print(new RJson(false, "error:  "+e.getMessage()));
            printWriter.flush();
            printWriter.close();
            e.printStackTrace();
        }
        //执行下载pdf方法
        try {
            File file2 = new File(filepath);
            File file1 = new File(pdfFile);
            if("1".equals(flag)){
                //下载pdf文件
                OfficeDownloadUtil officeDownloadUtil = new OfficeDownloadUtil();
                officeDownloadUtil.download(file1,pdfname,response,request);
            }else {
                printWriter = response.getWriter();
                printWriter.print(new RJson(false, "error:  Word2Pdf error"));
                printWriter.flush();
                printWriter.close();
            }
            //在临时文件的word删除
            if (file2.exists()) {
                file2.delete();
            }
            //在临时文件的pdf删除
            if (file1.exists()) {
                file1.delete();
            }
        } catch (Exception e) {
            logger.debug(e.getMessage());
            printWriter = response.getWriter();
            printWriter.print(new RJson(false, "error:  "+e.getMessage()));
            printWriter.flush();
            printWriter.close();
            e.printStackTrace();
        }
    }

    /**
     * Excel2Pdf
     * @param MapName
     * @param Excel
     * @param request
     * @param response
     * @throws IOException
     */
    public static void export2Excle(String MapName,Map Excel,HttpServletRequest request,HttpServletResponse response) throws IOException {
        PrintWriter printWriter=null;
        //===================ExcelMap=导出Excel文件=================
        if("ExcelMap".equals(MapName)) {
            //resEvent.getResMap()获取传过来的map值
            Map ExcelMap = (Map) Excel.get("ExcelMap");
            System.out.println("-----------------结果-------------------"+ExcelMap.get("listContent"));
            //==============设置导出文件名称======================
            String filename = ExcelMap.get("fileName").toString() + ".xls";

            response.reset();
            //判断浏览器解决中文乱码
            String codedfilename = null;
            try {
                String agent = request.getHeader("USER-AGENT");
                if (null != agent && -1 != agent.indexOf("MSIE") || null != agent
                        && -1 != agent.indexOf("Trident")) {// ie
                    String name = java.net.URLEncoder.encode(filename, "UTF8");
                    codedfilename = name;
                } else if (null != agent && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等
                    codedfilename = new String(filename.getBytes("UTF-8"), "iso-8859-1");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + codedfilename);

            //执行导出excel方法
            try {
                OfficeDownloadUtil officeDownloadUtil = new OfficeDownloadUtil();
                officeDownloadUtil.exportExcle(ExcelMap,request,response,"0");
            } catch (NoSuchMethodException e) {
                logger.debug(e.getMessage());
                printWriter = response.getWriter();
                printWriter.print(new RJson(false, "error:  "+e.getMessage()));
                printWriter.flush();
                printWriter.close();
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                logger.debug(e.getMessage());
                printWriter = response.getWriter();
                printWriter.print(new RJson(false, "error:  "+e.getMessage()));
                printWriter.flush();
                printWriter.close();
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                logger.debug(e.getMessage());
                printWriter = response.getWriter();
                printWriter.print(new RJson(false, "error:  "+e.getMessage()));
                printWriter.flush();
                printWriter.close();
                e.printStackTrace();
            }
            //==================================================================================================================
            //============================下载Excel文件=========================
        } else if("ExcelPath".equals(MapName)){
            //获取下载Excel文件的url
            String ExcelPath = Excel.get("ExcelPath").toString();
            File file = new File(ExcelPath);
            if(!file.exists()){
                logger.debug("Downloaded Excel file does not exist!");
                printWriter = response.getWriter();
                printWriter.print(new RJson(false, "error:  Downloaded Excel file does not exist!"));
                printWriter.flush();
                printWriter.close();
            }else{
                String filenames = file.getName();
                try {
                    OfficeDownloadUtil officeDownloadUtil = new OfficeDownloadUtil();
                    officeDownloadUtil.download(file,filenames,response,request);
                } catch (Exception e) {
                    logger.debug(e.getMessage());
                    printWriter = response.getWriter();
                    printWriter.print(new RJson(false, "error:  "+e.getMessage()));
                    printWriter.flush();
                    printWriter.close();
                    e.printStackTrace();
                }
            }
        } else {
            logger.debug("Excel file not exists!");
        }
    }

    /**
     * Excel2Pdf
     * @param MapName
     * @param wjUrl
     * @param Excel
     * @param request
     * @param response
     * @throws IOException
     */
    public static void Excel2Pdf(String MapName,String wjUrl,Map Excel,HttpServletRequest request,HttpServletResponse response) throws IOException {
        PrintWriter printWriter=null;
        //===================ExcelMap=导出Excel文件=================
        if("ExcelMap".equals(MapName)) {
            //resEvent.getResMap()获取传过来的map值
            Map ExcelMap = (Map) Excel.get("ExcelMap");

            response.setContentType("application/octet-stream");
            //==============设置导出文件名称======================
            //Excel文件名
            String filename = ExcelMap.get("fileName").toString() + ".xls";
            //PDF文件名
            String pdfname = ExcelMap.get("fileName").toString()+ ".pdf";
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("UTF-8"), "ISO8859-1"));
            //===============获得数据============================
            //获取内容
            List listContent = (List) ExcelMap.get("listContent");
            File file;
            File file1;
            String filenames;
            try {
                //进入OfficeDownloadUtil方法执行导出excel并放入临时文件夹
                OfficeDownloadUtil.exportExcle(ExcelMap,request,response,"1");
                //excel文件地址
                String inputFile =  wjUrl+ filename;
                //pdf存放文件地址
                String pdfFile = wjUrl + pdfname;
                //==================================================================================================================
                OfficeToPdfUtil office = new OfficeToPdfUtil();
                String flag = office.doOffice(inputFile,pdfFile);
                //导出excel成功
                if("1".equals(flag)){
                    file = new File(pdfFile);
                    filenames = file.getName();
                    try {
                        OfficeDownloadUtil officeDownloadUtil = new OfficeDownloadUtil();
                        officeDownloadUtil.download(file,filenames,response,request);
                    } catch (Exception e) {
                        printWriter = response.getWriter();
                        printWriter.print(new RJson(false, "error:  "+e.getMessage()));
                        printWriter.flush();
                        printWriter.close();
                        e.printStackTrace();
                    }
                }else {
                    printWriter = response.getWriter();
                    printWriter.print(new RJson(false, "error:  Excel2Pdf error"));
                    printWriter.flush();
                    printWriter.close();
                }
                //删除临时excel文件
                file1 = new File(inputFile);
                if (file1.exists()) {
                    file1.delete();
                }
                //删除临时pdf文件
                File file2 = new File(pdfFile);
                if (file2.exists()) {
                    file2.delete();
                }
            } catch (NoSuchMethodException e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
            }
            //==================================================================================================================
            //下载Excel文件
        } else if("ExcelPath".equals(MapName)){
            //获取下载Excel文件的url
            String ExcelPath = Excel.get("ExcelPath").toString();
            String pdfFile = "";
            File file = new File(ExcelPath);
            if(!file.exists()){
                logger.debug("Downloaded Excel file does not exist!");
                printWriter = response.getWriter();
                printWriter.print(new RJson(false, "error:  Downloaded Excel file does not exist!"));
                printWriter.flush();
                printWriter.close();
            }else{
                //获取excel文件名
                String filenames = file.getName();
                //生成pdf文件名
                String pdfname = filenames.substring(0,filenames.indexOf(".")) + ".pdf";
                //生成pdf存放的目录
                pdfFile = wjUrl + pdfname;
                try {
                    //执行excel转pdf方法
                    OfficeToPdfUtil office = new OfficeToPdfUtil();
                    String flag = office.doOffice(ExcelPath,pdfFile);
                    File file1 = new File(pdfFile);
                    if("1".equals(flag)){
                        //下载pdf文件
                        OfficeDownloadUtil officeDownloadUtil = new OfficeDownloadUtil();
                        officeDownloadUtil.download(file1,pdfname,response,request);
                        printWriter.flush();
                    }else {
                        printWriter = response.getWriter();
                        printWriter.print(new RJson(false, "error:  Excel2Pdf error"));
                        printWriter.flush();
                        printWriter.close();
                    }
                    //在临时文件的pdf删除
                    if (file1.exists()) {
                        file1.delete();
                    }
                } catch (Exception e) {
                    logger.debug(e.getMessage());
                    printWriter = response.getWriter();
                    printWriter.print(new RJson(false, "error:  "+e.getMessage()));
                    printWriter.flush();
                    printWriter.close();
                    e.printStackTrace();
                }
            }
        } else {
            logger.debug("Excel file not exists!");
        }
    }

}
