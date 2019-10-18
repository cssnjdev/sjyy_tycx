package com.cwks.bizcore.comm.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class AddressLngLatExchange {
	
	//写到配置文件中
	private static final String KEY = "8c7d0686b8c7a3e207065c5f7961f4dc";
	private static final String OUTPUT = "JSON";
	private static final String GET_LNG_LAT_URL = "http://restapi.amap.com/v3/geocode/geo";
	private static final String GET_ADDR_FROM_LNG_LAT = "http://restapi.amap.com/v3/geocode/regeo";
	private static final String EXTENSIONS_ALL = "all";

	private static Logger LOGGER = LoggerFactory.getLogger(AddressLngLatExchange.class);

	/**
	 * 
	 * @description 根据传进来的一个地址，查询对应的经纬度
	 * @param 
	 * @return Pair<BigDecimal,BigDecimal> 左节点值为经度，右节点值为纬度
	 * @author jxp
	 * @date 2017年7月12日
	 */
	public static Pair<BigDecimal, BigDecimal> getLngLatFromOneAddr(String address) {
 
		if (StringUtils.isBlank(address)) {
			LOGGER.error("地址（" + address + "）为null或者空");
			return null;
		}
 
		Map params = new HashMap();
		params.put("address", address);
		params.put("output", OUTPUT);
		params.put("key", KEY);
 
		String result = HttpClientHelper.httpClientPost(GET_LNG_LAT_URL,params,"utf-8");
		Pair<BigDecimal, BigDecimal> pair = null;
 
		// 解析返回的xml格式的字符串result，从中拿到经纬度
		// 调用高德API，拿到json格式的字符串结果
		JSONObject jsonObject = JSONObject.parseObject(result);
		// 拿到返回报文的status值，高德的该接口返回值有两个：0-请求失败，1-请求成功；
		int status = Integer.valueOf(jsonObject.getString("status"));
 
		if (status == 1) {
			JSONArray jsonArray = jsonObject.getJSONArray("geocodes");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				String lngLat = json.getString("location");
				String[] lngLatArr = lngLat.split(",");
				// 经度
				BigDecimal longitude = new BigDecimal(lngLatArr[0]);
				// System.out.println("经度" + longitude);
				// 纬度
				BigDecimal latitude = new BigDecimal(lngLatArr[1]);
				// System.out.println("纬度" + latitude);
				pair = new MutablePair<BigDecimal, BigDecimal>(longitude, latitude);
			}
 
		} else {
			String errorMsg = jsonObject.getString("info");
			LOGGER.error("地址（" + address + "）" + errorMsg);
		}
 
		return pair;
	}
	
	
	/**
	 * 
	 * @description 根据经纬度查地址
	 * @param lng：经度，lat：纬度
	 * @return 地址
	 * @author jxp
	 * @date 2017年7月12日
	 */
	public static String getAddrFromLngLat(String lng, String lat) {
		Map params = new HashMap();
		params.put("location", lng + "," + lat);
		params.put("output", OUTPUT);
		params.put("key", KEY);
		params.put("extensions", EXTENSIONS_ALL);
		String result = HttpClientHelper.httpClientPost(GET_ADDR_FROM_LNG_LAT,params,"utf-8");
		JSONObject json = JSONObject.parseObject(result);
		String status = json.getString("status");
		String address = null;
		if ("1".equals(status)) {
			JSONObject regeocode = JSONObject.parseObject((String)json.get("regeocode"));
			address = regeocode.getString("formatted_address");
		}
		return address;
	}

	public static void main(String[] args) {
		System.out.println(AddressLngLatExchange.getLngLatFromOneAddr("秦淮路8号"));
	}
}