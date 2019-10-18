package com.cwks.bizcore.tycx.core.utils;

import com.cwks.bizcore.comm.utils.DataTypeUtil;
import com.cwks.bizcore.comm.utils.JsonUtil;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YyfbUtils {
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
				isEmpty = ((String) obj).length()==0 ;
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
		if (!TycxUtils.isEmpty(str) && !"null".equals(str)) {
			isEmpty = false;
		}
		return isEmpty;
	}
	 
    /**
	 * 
	 * @name 获得当前时间
	 * @description 相关说明
	 * @time 创建时间:2015-6-24上午11:15:12
	 * @return Calendar
	 * @author 金立楠
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getNow() {
		return  DataTypeUtil.getCurrentDate();
	}
	/**
	 * 添加参数
	 * @param param
	 * @param value
	 * @param operType
	 */
	 public static void addSqlParam(ArrayList param, String value, String operType) {
	        //条件值为空，添加参数使该条件永真：对于条件操作符为=,in,like，将‘1’，‘1’两个值添加到param；对于操作符为 is null，只添加一个参数'1'
	        if (YyfbUtils.isEmpty(value)) {
	            param.add(Constant.DYNAMIC_SQL_PARAM_IS_EMPTY_PREFIX);
	            if (Constant.SQLPARAM_IN.equals(operType)) {
	                param.add(Constant.DYNAMIC_SQL_PARAM_IS_EMPTY_SUFFIX_FOR_MACRO);
	            } else if (Constant.SQLPARAM_LIKE.equals(operType) || Constant.SQLPARAM_EQUAL.equals(operType)) {
	                param.add(Constant.DYNAMIC_SQL_PARAM_IS_EMPTY_SUFFIX);
	            }
	        } else {//条件值为空，将条件值添加的参数中：对于条件操作符为=,in,like，第一个参数添加‘0’，第二个参数添加条件值；对于操作符为 is null，只添加一个参数'0'
	            param.add(Constant.DYNAMIC_SQL_PARAM_IS_NOT_EMPTY_PREFIX);
	            if (Constant.SQLPARAM_EQUAL.equals(operType)) {
	                param.add(value.trim());
	            } else if (Constant.SQLPARAM_LIKE.equals(operType)) {
	                param.add(Constant.PERCENTAGE_SYMBOL + value.trim()
	                        + Constant.PERCENTAGE_SYMBOL);
	            } else if (Constant.SQLPARAM_IN.equals(operType)) {
	                param.add(value.trim());
	            }
	        }
	    }
	 public static List hcbListToDmb(List dataList,String dm,String mc){
		 List resultList=new ArrayList();
		 for(int i=0;i<dataList.size();i++){
			 Map<String,Object> map=(Map<String, Object>) dataList.get(i);
			 Map<String,Object> resultMap=new HashMap<String,Object>();
			 resultMap.put("code", map.get(dm));
			 resultMap.put("caption", map.get(mc));
			 resultList.add(resultMap);
		 }
		 return resultList;
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
	 
}
