package com.cwks.bizcore.tycx.core.utils;

import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.util.JsonUtil;

import java.util.HashMap;

public class WfReqResUtil {

	/**
	 * @name transformReqMap
	 * @Description 转换 js 请求 or webservice xml 请求，转为 Map集合
	 * @param requestEvent
	 * @return HashMap
	 * @throws Exception
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String, String> transformReqMap(
			RequestEvent requestEvent) {

		HashMap<String, String> reqMap = new HashMap<String, String>();
		reqMap = requestEvent.getRequestMap();
		return reqMap;

	}

	/**
	 * @name transformResMap
	 * @Description 转换 返回数据 js 转[json格式] or webservice 转xml 报文
	 * @param requestEvent
	 * @param resEvent
	 * @param Object
	 * @return ResponseEvent
	 * @throws Exception
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static ResponseEvent transformResMap(RequestEvent requestEvent,
                                                ResponseEvent resEvent, Object object, String callBack) {

		HashMap map = new HashMap();
		String json = "";
		// 如果是jsonp调用 返回json 需要有 'callBack'('json')格式
		if (callBack == null || callBack.equals("")) {
			json = JsonUtil.toJsonString(object);
		} else {
			json = callBack + "(" + JsonUtil.toJsonString(object) + ")";
		}

		map.put("JSONDATA", json);
		return resEvent;

	}


/*	public List<?> xml2List(String xml) {
		try {
			List list = new ArrayList();
			Document document = DocumentHelper.parseText(xml);
			Element nodesElement = document.getRootElement();
			List nodes = nodesElement.elements();
			for (Iterator its = nodes.iterator(); its.hasNext();) {
				Element nodeElement = (Element) its.next();
				if (("l").equals(nodeElement.attributeValue("type"))) {
					List s = xml2List(nodeElement.asXML());
					list.add(s);
					s = null;
				} else if (("o").equals(nodeElement.attributeValue("type"))) {
					Map map = xml2Map(nodeElement.asXML());
					list.add(map);
					map = null;
				} else {
					list.add(nodeElement.getText());
				}
			}
			nodes = null;
			nodesElement = null;
			document = null;
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Map xml2Map(String xml) {
		try {
			Map map = new HashMap();
			Document document = DocumentHelper.parseText(xml);
			Element nodeElement = document.getRootElement();
			List node = nodeElement.elements();
			for (Iterator it = node.iterator(); it.hasNext();) {
				Element elm = (Element) it.next();
				if ("l".equals(elm.attributeValue("type"))) {
					map.put(elm.getName(), xml2List(elm.asXML()));
				} else if ("o".equals(elm.attributeValue("type"))) {
					map.put(elm.getName(), xml2Map(elm.asXML()));
				} else {
					map.put(elm.getName(), elm.getText());
				}
				elm = null;
			}
			node = null;
			nodeElement = null;
			document = null;
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/
}
