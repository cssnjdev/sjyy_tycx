package com.cwks.delegate;

import com.aspose.words.Document;
import com.aspose.words.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * 文档转PDF帮助类
 * <p>Title: OfficeToPdfUtil.java</p>
 * <p>Description: 文档转PDF帮助类 </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: cssnj</p>
 * @author yyz
 * @version 1.0
 */
public class OfficeToPdfUtil {
    private static Logger logger = LoggerFactory.getLogger(OfficeToPdfUtil.class);

    private static final int wdFormatPDF = 17;
    private static final int xlTypePDF = 0;
    private static final int ppSaveAsPDF = 32;

    public static void main(String[] args) throws IOException {
        OfficeToPdfUtil pdf = new OfficeToPdfUtil();
    }

    public String doOffice(String inputFile, String pdfFile){
        String kind = getFileSufix(inputFile);
        File file = new File(inputFile);
        String flager ="";
        if (!file.exists()) {
            return "文件不存在!";
        }
        if (kind.equals("pdf")) {
            return "文件已经是PDF格式了";
        }
        if (kind.equals("doc")||kind.equals("docx")||kind.equals("txt")) {
            flager = OfficeToPdfUtil.word2pdf(inputFile,pdfFile);
            return flager;
        }else if (kind.equals("ppt")||kind.equals("pptx")) {
            flager = OfficeToPdfUtil.ppt2pdf(inputFile,pdfFile);
            return flager;
        }else if(kind.equals("xls")||kind.equals("xlsx")){
            flager = OfficeToPdfUtil.excel2pdf(inputFile,pdfFile);
            return flager;
        }else {
            return "0";
        }
    }

    /***
     * 判断文件类型
     *
     * @param fileName
     * @return
     */
    public static String getFileSufix(String fileName) {
        int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(splitIndex + 1);
    }

    //判断word
    public static boolean getLicenseWord() {
        boolean result = false;
        try {
            InputStream is = OfficeToPdfUtil.class.getClassLoader().getResourceAsStream("license.xml");
            //  wordlicense.xml应放在..\WebRoot\WEB-INF\classes路径下
            com.aspose.words.License aposeLic = new com.aspose.words.License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
        	logger.error("###[Error] OfficeToPdfUtil getLicenseWord() Exception：", e);
        }
        return result;
    }

    /***
     *
     * Word转PDF
     *
     * @param inputFile
     * @param pdfFile
     * @return
     */
    public static String word2pdf(String inputFile, String pdfFile){
        // TODO Auto-generated method stub
        if (!getLicenseWord()) {  // 验证License 若不验证则转化出的PDP文档会有水印产生
            return "-1";
        }
        try {
            FileOutputStream os = new FileOutputStream(pdfFile);
            Document doc = new Document(inputFile);     //Address是将要被转化的word文档
            logger.debug("========Word 转 PDF 开始 ===========");
            doc.save(os, com.aspose.words.SaveFormat.PDF);             //全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            logger.debug("========Word 转 PDF  结束  ===========");
            //删除临时excel文件
            File file = new File(inputFile);
            if (file.exists()) {
                file.delete();
            }
            os.close();
            return "1";
        } catch (Exception e) {
        	logger.error("###[Error] OfficeToPdfUtil word2pdf() Exception：", e);
            return "-1";
        }

    }

    /***
     * ppt转化成PDF
     *
     * @param inputFile
     * @param pdfFile
     * @return
     */
    public static String ppt2pdf(String inputFile, String pdfFile){
        try {

            return "1";
        } catch (Exception e) {
        	logger.error("###[Error] OfficeToPdfUtil ppt2pdf() Exception：", e);
            return "-1";
        }
    }

    //判断Excel
    public static boolean getLicenseExcel() {
        boolean result = false;
        try {
            InputStream is = OfficeToPdfUtil.class.getClassLoader().getResourceAsStream("license.xml");
            //  license.xml应放在..\WebRoot\WEB-INF\classes路径下
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
        	logger.error("###[Error] OfficeToPdfUtil getLicenseExcel() Exception：", e);
        }
        return result;
    }

    /***
     *
     * Excel转化成PDF
     *
     * @param inputFile
     * @param pdfFile
     * @return
     */
    public static String excel2pdf(String inputFile, String pdfFile) {
        if (!getLicenseExcel()) {          // 验证License 若不验证则转化出的pdf文档会有水印产生
            return "-1";
        }
        try {
//            Workbook wb = new Workbook(inputFile);// 原始excel路径
            FileOutputStream fileOS = new FileOutputStream(pdfFile);// 输出路径
            logger.debug("========Excel 转 PDF 开始 ===========");
//            wb.save(fileOS, com.aspose.cells.SaveFormat.PDF);
            logger.debug("========Excel 转 PDF 结束 ===========");
            fileOS.close();
            return "1";
        } catch (Exception e) {
        	logger.error("###[Error] OfficeToPdfUtil excel2pdf() Exception：", e);
            return "-1";
        }
    }

}