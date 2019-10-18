package com.cwks.bizcore.tycx.core.utils;

import com.cwks.bizcore.comm.utils.DataTypeUtil;
import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx001CxCxjgdyPojo;
import com.cwks.bizcore.tycx.core.vo.ExtNodeVO;
import com.cwks.common.core.cache.CacheUtil;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TycxUtils {
	/**
	 * @name 判断对象是否为空
	 * @description 相关说明
	 * @time 创建时间:2015-6-24上午10:54:36
	 * @param obj
	 *            obj
	 * @return true or false
	 * @author jinln
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static boolean isEmpty(Object obj) {
		boolean isEmpty = true;
		if (obj != null) {
			isEmpty = false;
			if (obj instanceof List) {
				isEmpty = ((List) obj).size() == 0;
			} else if (obj instanceof Map) {
				isEmpty = ((Map) obj).size() == 0;
			} else if (obj instanceof String) {
				isEmpty =((String) obj).length()==0;
			}
		}
		return isEmpty;
	}

	/**
	 * @name 判断字符串是否为空,把空指针,""空字符串,"null"统统看作为空值
	 * @description 相关说明
	 * @time 创建时间:2017年8月31日
	 * @param str
	 *            字符串
	 * @return true or false
	 * @author jinln
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static boolean isEmptyString(String str) {
		boolean isEmpty = true;
		if (str != null && !"".equals(str) && !"null".equals(str)) {
			isEmpty = false;
		}
		return isEmpty;
	}
	 /**
     * 
     *@name  boolean TRUE 检查通过,无注入关键字 FALSE 检查不通过,有注入关键字
     *@description 相关说明
     *@time    创建时间:2015年6月29日上午11:16:21
     *@param cxtjValue cxtjValue
     *@return boolean
     *@author  jinln
     *@history 修订历史（历次修订内容、修订人、修订时间等）
     */
    public static boolean checkCxtj(String cxtjValue) {// DELETE//UPDATE
        boolean isFind;
        if (cxtjValue == null || cxtjValue.equals("")) {
            return true;
        }
        Pattern p = Pattern.compile("\\b[Dd][Rr][Oo][Pp]\\b");// DROP
        Matcher m = p.matcher(cxtjValue);
        isFind = m.find();
        if (isFind) {
            return false;
        }
        p = Pattern.compile("\\b[Dd][Ee][Ll][Ee][Tt][Ee]\\b");// DEIETE
        m = p.matcher(cxtjValue);
        isFind = m.find();
        if (isFind) {
            return false;
        }
        p = Pattern.compile("\\b[Uu][Pp][Dd][Aa][Tt][Ee]\\b");// UPDATE
        m = p.matcher(cxtjValue);
        isFind = m.find();
        if (isFind) {
            return false;
        }
        p = Pattern.compile("\\b[Ss][Ee][Ll][Ee][Cc][Tt]\\b");// SELECT
        m = p.matcher(cxtjValue);
        isFind = m.find();
        if (isFind) {
            return false;
        }
        p = Pattern.compile("\\b[Cc][Rr][Ee][Aa][Tt][Ee]\\b");// CREATE
        m = p.matcher(cxtjValue);
        isFind = m.find();
        if (isFind) {
            return false;
        }
        return true;
    }
    /**
     * 
     *@name    防注入检查
     *@description 相关说明
     *@time    创建时间:2015-6-24上午11:06:35
     *@param sqlStr sqlStr
     *@return true or false
     *@author   jinln
     *@history 修订历史（历次修订内容、修订人、修订时间等）
     */
    public static boolean checkSql(String sqlStr) {
        boolean isFind;
        if (sqlStr == null || sqlStr.equals("")) {
            return true;
        }
        //DROP检查
        Pattern p = Pattern.compile("\\b[Dd][Rr][Oo][Pp]\\b");
        Matcher m = p.matcher(sqlStr);
        isFind = m.find();
        if (isFind) {
            return false;
        }
        //DEIETE检查
        p = Pattern.compile("\\b[Dd][Ee][Ll][Ee][Tt][Ee]\\b");
        m = p.matcher(sqlStr);
        isFind = m.find();
        if (isFind) {
            return false;
        }
        //UPDATE检查
        p = Pattern.compile("\\b[Uu][Pp][Dd][Aa][Tt][Ee]\\b");
        m = p.matcher(sqlStr);
        isFind = m.find();
        if (isFind) {
            return false;
        }
        return true;
    }
    /**
	 * 
	 * @name 获得当前时间
	 * @description 相关说明
	 * @time 创建时间:2015-6-24上午11:15:12
	 * @return Calendar
	 * @author 胡必武
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getNow() {
		return  DataTypeUtil.getCurrentDateTime();
	}
	/**
	 * @name 得到缓存代码表数据
	 * @description 相关说明
	 * @time 创建时间:2015年6月29日上午10:54:50
	 * @param dmName
	 *            dmName
	 * @param key
	 *            key
	 * @param mc
	 *            mc
	 * @return String
	 * @author 林全
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	@SuppressWarnings("unchecked")
	public static String getDmFromCache(String dmName, String key, String mc,String dmzd) {
		tycxUtil tycxUtil = new tycxUtil();
		String valueStr = "";
		if (key == null || key.equals("")) {
			return "";
		}
		if (dmName == null || dmName.equals("")) {
			return key;
		}
		if (mc == null || mc.equals("")) {
			return key;
		}
		// 解决多个代码用逗号隔开的情况转换
		final String[] keyArr = key.split(",");
		for (int i = 0; i < keyArr.length; i++) {
			try {
				//List<Map> list = CacheUtil.getCodeTable(dmName, dmzd+"="+keyArr[i]);

				 final String real_value= CacheUtil.getMcByDm(dmName, "'"+keyArr[i]+"'",dmzd);//(String) list.get(0).get(mc);
				

				if (TycxUtils.isEmptyString(real_value)) {
					keyArr[i] = key;
				} else {
					keyArr[i] = real_value;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return key;
			}
		}
		valueStr = getStrFromArr(keyArr, ",");
		return valueStr;
	}

	/**
	 * 
	 * @name 数组转字符串
	 * @description 数组转换为用特殊字符分割的字符串
	 * @time 创建时间:2015-6-24上午11:10:00
	 * @param inArr
	 *            inArr
	 * @param str
	 *            str
	 * @return outStr
	 * @author 胡必武
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getStrFromArr(String[] inArr, String str) {
		String outStr = "";
		for (int i = 0; i < inArr.length; i++) {
			outStr = outStr + inArr[i] + str;
		}
		if (outStr != null && outStr.length() > 0) {
			outStr = outStr.substring(0, outStr.length() - 1);
		}
		return outStr;
	}
	public static void writeMsgToExt(HttpServletResponse response,
			String infoStr) {
		response.setContentType("text/xml; charset=UTF-8");
		//ThreadLocalManager.add("download", true);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.println(infoStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.close();
	}
	/**
	 * 根据参数分类从cache中获取参数内容
	 * 
	 * @param csfl
	 *            参数分类
	 * @param isFuzzy
	 *            是否使用模糊查询匹配开头
	 * @return 包含参数名，参数值与备注的参数内容集合
	 * @throws SwordBaseCheckedException
	 *             异常
	 */
	public static List<Map> getCSByCsfl(String csfl,
			boolean isFuzzy) throws Exception {
		final List<Map<String, Object>> csList = new ArrayList<Map<String, Object>>();
		List<Map> list= CacheUtil.getCodeTable("CX_CS_CSB","CSFL ='"+csfl+"'");
//		final Iterator it = CacheUtil.getCodeTable("CX_CS_CSB","CSFL ='"+csfl+"'")
//				.iterator();
//		final List<Map<String, Object>> csList = new ArrayList<Map<String, Object>>();
//		if (isFuzzy) {
//			csfl = "^" + csfl + "\\w*";
//		}
//		while (it.hasNext()) {
//
//			final Map cacheMap = (Map) it.next();
//				final HashMap<String, Object> csMap = new HashMap<String, Object>();
//				csMap.put("csm", cacheMap.get("CSM"));
//				csMap.put("csz", cacheMap.get("CSZ"));
//				csMap.put("bz", cacheMap.get("BZ"));
//				csList.add(csMap);
//		
//		}
		return list;
	}
	/**
	 * @name 将MapList转换为树型列表
	 * @description 相关说明
	 * @time 创建时间:2015年6月29日上午11:02:12
	 * @param resultList
	 *            resultList
	 * @param dm
	 *            dm
	 * @param mc
	 *            mc
	 * @param pdm
	 *            pdm
	 * @return List<ExtNodeVO>
	 * @author jinln
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static List<ExtNodeVO> convertMapListToExtTree(
			List<Map<String, Object>> resultList, String dm, String mc,
			String pdm,boolean isChecked) {
		final List<ExtNodeVO> resList = new ArrayList<ExtNodeVO>();
		if (!TycxUtils.isEmpty(resultList)) {
			for (Map<String, Object> map : resultList) {
				final ExtNodeVO vo = new ExtNodeVO();
				vo.setId((String) map.get(dm));
				vo.setText((String) map.get(mc));
				vo.setName((String) map.get(mc));
				vo.setChecked(isChecked);
				vo.setPid((String) map.get(pdm));			
			//	vo.setIsParent(true);
				resList.add(vo);
			}
		}
		return resList;
	}
    /**
     * 
     *@name 将sql语句增加分组统计功能
     *@description 相关说明
     *@time 创建时间:2015-6-24上午11:22:34
     *@param sqlStr sqlStr
     *@param wd wd
     *@param zb zb
     *@return String
     *@author jinln
     * @param cxjgList 
     *@history 修订历史（历次修订内容、修订人、修订时间等）
     */
    public static String addWdAndzbToSql(String sqlStr, String wd, String zb, List<Tycx001CxCxjgdyPojo> cxjgList) {
        if (wd != null && !wd.equals("") && zb != null && !zb.equals("")) {
            String tjSelect = "";
            String tjOrder = "";
            final String[] nwd = wd.split(",");
            for (int i = 0; i < nwd.length; i++) {
                tjSelect = tjSelect + nwd[i] + ",";
                tjOrder = tjOrder + nwd[i] + ",";
            }
            
            final String[] nzb = zb.split(",");
            for (int i = 0; i < nzb.length; i++) { // 添加统计列
            	 
            	String nz = nzb[i];
            	
            	Tycx001CxCxjgdyPojo pojo=null;
            	String tjlx = "2"; // 默认添加类型 2 求和
            	if(cxjgList!=null){
	            	for(int k=0;k<cxjgList.size();k++){ // 遍历出统计类型
	            		pojo = cxjgList.get(k);
	            		if(pojo.getLmc().equals(nz)){
	            			tjlx = pojo.getTjlx();
	            			break;
	            		}
	            	}
            	} 
            	
            	String ywkj = pojo.getYwkj();
            	if (!isEmpty(ywkj)) {
            		tjSelect = tjSelect + ywkj + " " + nz + ",";
            	}else if("2".equals(tjlx)){		
            		tjSelect = tjSelect + "SUM(" +nz + ")" + " " + nz + ",";
            	}else if("3".equals(tjlx)){
            		tjSelect = tjSelect + "AVG(" + nz + ")" + " " + nz + ",";
            	}else if("4".equals(tjlx)){
            		tjSelect = tjSelect + "MAX(" + nz + ")" + " " + nz + ",";
            	}else if("5".equals(tjlx)){
            		tjSelect = tjSelect + "MIN(" + nz + ")" + " " + nz + ",";
            	}
            	
            }
            if (tjSelect.length() > 0){
                tjSelect = tjSelect.substring(0, tjSelect.length() - 1);
            }
            if (tjOrder.length() > 0){
                tjOrder = tjOrder.substring(0, tjOrder.length() - 1);
            }
            sqlStr = "SELECT " + tjSelect + " FROM (" + sqlStr + ")" + " GROUP BY " + wd + " ORDER BY " + tjOrder;
        }
        return sqlStr;
    }
    public static String getNewSqlxh(String sqlxh, String ywfl) throws Exception {
        final String swjg_dm ="00000000999";//SwordSessionUtils.getOrgID();
        String newSqlxh = "";
        final String oldSqlxh = sqlxh;
        if (ywfl != null && !"".equals(ywfl)) {
            if (oldSqlxh.length() > 4) {
                newSqlxh = "10" + ywfl + oldSqlxh;
            } else {
                newSqlxh = "10" + ywfl + TycxUtils.leftPad(oldSqlxh, 4, '0');
            }
        } else {
            newSqlxh = oldSqlxh;
        }
         newSqlxh = swjg_dm.substring(0, 3) + newSqlxh;

        
        return newSqlxh;
    }
    /**
	 * 
	 * @name 中文名称
	 * @description 如果字符串长度小于size，则在左边补字符串padStr使其长度等于size，然后返回
	 *              。如果字符串长度大于等于size，则返回它本身
	 * @time 创建时间:2015-6-24上午11:20:11
	 * @param str
	 *            str
	 * @param size
	 *            size
	 * @param padStr
	 *            padStr
	 * @return String
	 * @author 胡必武
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String leftPad(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}
		if (isEmpty(padStr)) {
			padStr = " ";
		}
		final int padLen = padStr.length();
		final int strLen = str.length();
		final int pads = size - strLen;
		if (pads <= 0) {
			return str;
		}
		if ((padLen == 1) && (pads <= 8192)) {
			return leftPad(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return padStr.concat(str);
		}
		if (pads < padLen) {
			return padStr.substring(0, pads).concat(str);
		}
		final char[] padding = new char[pads];
		final char[] padChars = padStr.toCharArray();
		for (int i = 0; i < pads; ++i) {
			padding[i] = padChars[i % padLen];
		}
		return new String(padding).concat(str);
	}
	public static String leftPad(String str, int size, char padChar) {
		if (str == null) {
			return null;
		}
		final int pads = size - str.length();
		if (pads <= 0) {
			return str;
		}
		if (pads > 8192) {
			return leftPad(str, size, String.valueOf(padChar));
		}
		return padding(pads, padChar).concat(str);
	}
	/**
	 * 
	 * @name 中文名称
	 * @description 相关说明
	 * @time 创建时间:2015-6-24上午11:20:44
	 * @param repeat
	 *            repeat
	 * @param padChar
	 *            padChar
	 * @return String
	 * @throws IndexOutOfBoundsException
	 * @author 胡必武
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	private static String padding(int repeat, char padChar)
			throws IndexOutOfBoundsException {
		if (repeat < 0) {
			throw new IndexOutOfBoundsException(
					"Cannot pad a negative amount: " + repeat);
		}
		final char[] buf = new char[repeat];
		for (int i = 0; i < buf.length; ++i) {
			buf[i] = padChar;
		}
		return new String(buf);
	}
	 public static String toJsonForJqGrid(PageInfo<?> page)
	    {
	    	
	      Map map = new HashMap();
	      
	      map.put("page", Integer.valueOf(page.getPageNum()));
	      map.put("total", page.getTotal());
	      map.put("count",page.getTotal());
	      map.put("iTotalDisplayRecords",page.getTotal());
	      map.put("iTotalRecords",page.getTotal());
	      
	      map.put("records", Long.valueOf(page.getTotal()));
	      
	      map.put("data", page.getList());
	      return JsonUtil.toJson(map);
	      
	    }
	 public static ArrayList getCxCsb(String bds){
		 String hcb_bds="XYBJ='Y'";
		 if(!isEmpty(bds)){
			 hcb_bds=hcb_bds+" AND "+bds;
		 }
		 List dbtypes = CacheUtil.getTable("CX_CS_CSB", hcb_bds) ;
	    	ArrayList dmbList = new ArrayList();
	    	for(int i=0;i<dbtypes.size();i++){
	    		Map map =(Map) dbtypes.get(i);
	    		Map codeMap=new HashMap();
	    		codeMap.put("code",map.get("CSZ"));
	    		codeMap.put("caption",map.get("CSM"));
	    		dmbList.add(codeMap);
			}
	    	return dmbList;
	 }
	 public static ArrayList getSessionCxCsb(String bds){
		 String hcb_bds="XYBJ='Y'";
		 if(!isEmpty(bds)){
			 hcb_bds=hcb_bds+" AND "+bds;
		 }
		 List dbtypes = CacheUtil.getTable("CX_CS_CSB", hcb_bds) ;
	    	ArrayList dmbList = new ArrayList();
	    	for(int i=0;i<dbtypes.size();i++){
	    		Map map =(Map) dbtypes.get(i);
	    		Map codeMap=new HashMap();
	    		codeMap.put("code",map.get("BZ"));
	    		codeMap.put("caption",map.get("CSZ")+"@"+map.get("CSM"));
	    		dmbList.add(codeMap);
			}
	    	return dmbList;
	 }
	 public static List idToCodeList(List list){
		 List resultList=new ArrayList();
		 for(int i=0;i<list.size();i++){
			 Map<String,Object> map=(Map<String, Object>) list.get(i);
			 Map<String,Object> newMap=new HashMap();
			 newMap.put("code", map.get("id"));
			 newMap.put("caption", map.get("text"));
			 resultList.add(newMap);
		 }
		 return resultList;
		 
	 }
   public static <T> Map<String,Object> convert2Map(T t) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
	   Map<String,Object> resultMap=new HashMap<String,Object>();
	   Method[] methods=t.getClass().getMethods();
		   for(Method method:methods){
			   Class<?>[] paramClass=method.getParameterTypes();
			   if(paramClass.length>0){
				   continue;
			   }
			   String methodName=method.getName();
			   if(methodName.startsWith("get")){
				   Object value=method.invoke(t);
				   String name=methodName.substring(3, methodName.length());
				   String newName=name.toLowerCase();
				   resultMap.put(newName, value);
			   }
		   }
		return resultMap;
	  
   }
}
