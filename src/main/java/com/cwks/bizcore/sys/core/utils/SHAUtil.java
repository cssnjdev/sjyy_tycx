package com.cwks.bizcore.sys.core.utils;

import java.security.MessageDigest;

/**
 * 对给定的字符串,采用SHA进行加密
 * <p>
 * Title: SHAUtil.java
 * </p>
 * <p>
 * Description: 办公自动化平台
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
public abstract class SHAUtil {
	/**
	 * 采用SHA算法对字符串进行加密。
	 * @param strSrc 需要进行加密处理的字符串
	 * @return 返回加密后的字符串,加密失败返回null。
	 */
	public static String encrypt(String strInit) {
		String strDes = null;
		if (null == strInit)// null字符加密后为null
			return null;
		try {
			// 获取MessageDigest对象，指定加密算法为SHA。
			MessageDigest md = MessageDigest.getInstance("SHA");
			// 添加需要加密的字符串
			md.update(strInit.getBytes());
			// 进行加密处理，获得加密后的二进制字节数组
			byte[] digest = md.digest();
			// 将二进制字节数组转换成十六进制字符串
			strDes = byte2hex(digest);
			// logger.debug("加密后的字符串：" + strDes);
		} catch (Exception e) {
			return null;
		}
		return strDes;
	}

	/**
	 * 验证未加密的字符串与加密后的字符串是否匹配，作验证密码用
	 * @param str 未加密的字符串
	 * @param passwd 加密后的字符串
	 * @return
	 */
	public static boolean varify(String strInit, String strPwd) {
		if (null == strPwd) {
			if (null == strInit) {// 原始字符串和加密后的字符串都为null，返回true
				return true;
			} else {
				return false;
			}
		} else if (strPwd.equals(encrypt(strInit))) {
			return true;
		} else
			return false;
	}

	/**
	 * 将二进制字节数组转换成十六进制字符串
	 * @param b 二进制字节数组
	 * @return 十六进制字符串，以"："进行分割
	 */
	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				// 不足两位的末尾补零
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				// ":"作为分割符
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}

	public static void main(String[] args) {
//		if (args.length < 1) {
//			System.out.println("Usage:SHATest aString");
//			System.exit(0);
//		}
		String strSrc = "admin_85F830CE-6B6A-4C73-8AD5-A21747C278D6";
		String strDes = SHAUtil.encrypt(strSrc);
		System.out.println("The input string is:" + strSrc);
		System.out.println("The output string is:" + strDes);
		if (SHAUtil.varify(strSrc, strDes)) {
			System.out.println(strDes + " is the encoded string of " + strSrc + " by SHA");
		} else {
			System.out.println("Encode error");
		}
		return;
	}
}
