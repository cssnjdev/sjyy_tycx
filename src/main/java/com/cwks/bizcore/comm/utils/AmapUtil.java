package com.cwks.bizcore.comm.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AmapUtil {

	private static String API = "http://restapi.amap.com/v3/geocode/geo?key=<key>&s=rsv3&address=<address>";

	private static String KEY = "aa4a48297242d22d2b3fd6eddfe62217";

	private static Pattern pattern = Pattern.compile("\"location\":\"(\\d+\\.\\d+),(\\d+\\.\\d+)\"");

	static {
		init();
	}

	private static void init() {
		System.out.println("高德地图工具类初始化");
		System.out.println("api: {}"+API);
		System.out.println("key: {}"+KEY);
		API = API.replaceAll("<key>", KEY);
	}

	public static double[] addressToGPS(String address) {
		try {
			String requestUrl = API.replaceAll("<address>", URLEncoder.encode(address, "UTF-8"));
			System.out.println("请求地址: {}" + requestUrl);
			requestUrl = HttpClientHelper.httpClientGet(requestUrl,null,"UTF-8");
			if (requestUrl != null ) {
				Matcher matcher = pattern.matcher(requestUrl);
				if (matcher.find() && matcher.groupCount() == 2) {
					double[] gps = new double[2];
					gps[0] = Double.valueOf(matcher.group(1));
					gps[1] = Double.valueOf(matcher.group(2));
					System.out.println("gps: {}" + Arrays.toString(gps));
					return gps;
				}
			}
		} catch (Exception e) {
		}

		return null;
	}

	public static void main(String[] args) {
		System.out.println(AmapUtil.addressToGPS("南京市江宁税务局"));
	}
}
