package com.cwks.bizcore.comm.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Json转换帮助类
 * <p>
 * Title: JsonUtil.java
 * </p>
 * <p>
 * Description: JsonUtil
 * </p>
 * <p>
 * Copyright: Copyright (c) 2019
 * </p>
 * <p>
 * Company: cwks
 * </p>
 * 
 * @author H.R
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class JsonUtil {
	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	/**
	 * mybatis分页返回easyui-datagrid格式json
	 * 
	 * @param page
	 * @return
	 */
	public static String toJson(PageInfo<?> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotal());
		map.put("rows", page.getList());
		try {
			return JSON.toJSONString(map);
		} catch (Exception e) {
			logger.error("###[Error] toJson Exception：", e);
		}
		return "";
	}

	/**
	 * mybatis分页返回JqGrid格式json
	 * 
	 * @param page
	 * @return
	 */
	public static String toJsonForJqGrid(PageInfo<?> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page.getPageNum());
		map.put("total", page.getPages());
		map.put("records", page.getTotal());
		map.put("rows", page.getList());
		try {
			return JSON.toJSONString(map);
		} catch (Exception e) {
			logger.error("###[Error] toJsonForJqGrid Exception：", e);
		}
		return "";
	}

	/**
	 * object转json
	 * 
	 * @param ob
	 * @return
	 */
	public static String toJson(Object ob) {
		try {
			return JSON.toJSONString(ob);
		} catch (Exception e) {
			logger.error("###[Error] toJson Object Exception：", e);
		}
		return "";
	}

	/**
	 * 字符串转List
	 * 
	 * @param ob
	 * @return
	 */
	public static List<Map> toListMap(String ob) {
		try {
			return JSON.parseObject(ob, List.class);
		} catch (Exception e) {
			logger.error("###[Error] toListMap Exception：", e);
		}
		return null;
	}

	/**
	 * 字符串转JavaBean
	 * 
	 * @param type
	 * @param ob
	 * @param <T>
	 * @return
	 */
	public static <T> Class<T> toJavaBean(Class<?> type, String ob) {
		try {
			return (Class<T>) JSON.parseObject(ob, type);
		} catch (Exception e) {
			logger.error("###[Error] toJavaBean Exception：", e);
		}
		return null;
	}

	/**
	 * 字符串转List
	 * 
	 * @param type
	 * @param ob
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> toListJavaBean(Class<?> type, String ob) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, type);
			return mapper.readValue(ob, javaType);
		} catch (Exception e) {
			logger.error("###[Error] toListJavaBean Exception：", e);
		}
		return null;
	}

	/**
	 * 字符串转MAP
	 * 
	 * @param ob
	 * @return
	 */
	public static Map<?, ?> toMap(String ob) {
		try {
			return JSON.parseObject(ob, Map.class);
		} catch (Exception e) {
			logger.error("###[Error] toMap Exception：", e);
		}
		return null;
	}

	/**
	 * 字符串转LinkedHashMap
	 * 
	 * @param ob
	 * @return
	 */
	public static LinkedHashMap<?, ?> toLinkedHashMap(String ob) {
		try {
			return JSON.parseObject(ob, LinkedHashMap.class);
		} catch (Exception e) {
			logger.error("###[Error] LinkedHashMap Exception：", e);
		}
		return null;
	}

	/**
	 * 写出Object
	 * 
	 * @param ob
	 * @param resp
	 */
	public static void toWriter(Object ob, HttpServletResponse resp) {
		try {
			resp.setContentType("application/json;charset=UTF-8");
			resp.setCharacterEncoding("UTF-8");
			JSON.toJSONString(ob.toString());
			// for json return
			// JSON.parseObject(resp.getWriter(), ob);
		} catch (Exception e) {
			logger.error("###[Error] toWriter Exception：", e);
		}
	}


	public static void main(String[] args) {
		List<Map> map = JsonUtil.toListMap(
				"[{'phone' : '','businessType' : 4,'registrationNo' : '',	'companyName' : '感觉可不能入','registeredCapital' : null,'business' : '发把附表二额二人','addresss' : '','id' : 3,	'businessTerm' : '','registeredAddress' : '',	'createDate' : '2013-08-20','legalRepresentative' : '分设备股份巴塞罗那','registrationAgency' : null}]"
						.toString().replaceAll("'", "\""));
		System.out.println(map);
		Map testmap = new HashMap();
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, -1);
		date = c.getTime();
		testmap.put("mydata", date);
		//JsonUtil.setFormatYYYSS();
		String resjson = JsonUtil.toJson(testmap);
		System.out.println(resjson);
	}
}