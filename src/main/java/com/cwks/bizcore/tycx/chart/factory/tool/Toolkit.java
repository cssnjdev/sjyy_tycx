package com.cwks.bizcore.tycx.chart.factory.tool;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Clob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Toolkit implements Serializable{

	private static final long serialVersionUID = 1L;
	public static int MAX_LEN = 10000;

	private Toolkit() {
		super();
	}

	/**
	 * @description 判断Object是否为空
	 * @param Object
	 *            value
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmpty(Object value) {
		if (value == null) {
			return true;
		}
		if ((value instanceof String)
				&& (((String) value).trim().length() <= 0)) {
			return true;
		}
		if ((value instanceof Object[]) && (((Object[]) value).length <= 0)) {
			return true;
		}
		if (value instanceof Object[]) {
			Object[] t = (Object[]) value;
			for (int i = 0; i < t.length; i++) {
				if (t[i] != null) {
					return false;
				}
			}
			return true;
		}
		if ((value instanceof Collection) && ((Collection) value).size() <= 0) {
			return true;
		}
		if ((value instanceof Dictionary) && ((Dictionary) value).size() <= 0) {
			return true;
		}
		if ((value instanceof Map) && ((Map) value).size() <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * @description 判断两个Object是否相等
	 * @param Object
	 *            o1
	 * @param Object
	 *            o2
	 * @return boolean
	 */
	public static boolean isEqual(Object o1, Object o2) {
		if (o1 == null && o2 == null)
			return true;
		if (o1 == null && o2 != null)
			return false;
		if (o1 != null && o2 == null)
			return false;
		return o1.toString().equals(o2.toString());
	}

	/**
	 * @description 判断两个String[]是否相等
	 * @param String []
	 *            as1
	 * @param String []
	 *            as2
	 * @return boolean
	 */
	public static boolean isEqual(String[] as1, String[] as2) {
		if (isEmpty(as1) && isEmpty(as2))
			return true;
		if (isEmpty(as1) && !isEmpty(as2))
			return false;
		if (!isEmpty(as1) && isEmpty(as2))
			return false;
		if (as1.length != as2.length)
			return false;
		for (int i = 0; i < as1.length; i++) {
			if (!isEqual(as1[i], as2[i]))
				return false;
		}
		return true;
	}

	/**
	 * @description 判断两个Double是否相等
	 * @param Double
	 *            o1
	 * @param Double
	 *            o2
	 * @return boolean
	 */
	public static boolean isEqual(Double o1, Double o2) {
		if (o1 == null && o2 == null)
			return true;
		if (o1 == null && o2 != null)
			return false;
		if (o1 != null && o2 == null)
			return false;
		return o1.equals(o2);
	}

	public static boolean isInDebug() {
		return false;
	}

	public static String[] addTwoStringArray(String[] s1, String[] s2) {
		if (s2 == null || s2.length == 0)
			return s1;
		if (s1 == null)
			s1 = new String[0];
		String[] s = new String[s1.length + s2.length];
		System.arraycopy(s1, 0, s, 0, s1.length);
		System.arraycopy(s2, 0, s, s1.length, s2.length);
		return s;
	}

	public static int binarySearch(Object[] ary, Object o) {
		if (isEmpty(ary))
			return -1;
		Arrays.sort(ary);
		return Arrays.binarySearch(ary, o);
	}

	/**
	 * 
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * 
	 * @param v2
	 *            加数
	 * 
	 * @return 两个参数的和
	 */

	public static double add(double v1, double v2) {

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.add(b2).doubleValue();

	}

	/**
	 * 
	 * 3个数精确相加
	 * 
	 * @param v1
	 *            被加数
	 * 
	 * @param v2
	 *            加数
	 * 
	 * @return 两个参数的和
	 */

	public static String sumThreeString(String v1, String v2, String v3) {

		if (isEmpty(v1)) {
			v1 = "0.0";
		}
		if (isEmpty(v2)) {
			v2 = "0.0";

		}
		if (isEmpty(v3)) {
			v3 = "0.0";

		}
		try {
			BigDecimal b1 = new BigDecimal(v1);

			BigDecimal b2 = new BigDecimal(v2);

			BigDecimal b3 = new BigDecimal(v3);

			return b1.add(b2).add(b3).toString();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param value
	 * @return double
	 */
	public static double getDoubleValue(Double value) {
		return value == null ? 0.0 : value.doubleValue();
	}

	public static Double getDoubleVal(Object oObj) {
		if (Toolkit.isEmpty(oObj)) {
			return new Double("0.00");
		} else {
			return new Double(oObj.toString());
		}
	}

	public static String getStringVal(Object oObj) {
		if (Toolkit.isEmpty(oObj)) {
			return "";
		} else {
			return oObj.toString();
		}
	}

	public static String getStringVal(Object oObj, String defaultStr) {
		if (Toolkit.isEmpty(oObj)) {
			return defaultStr;
		} else {
			return oObj.toString();
		}
	}

	public static String getString10Length(Object oObj) {
		String str = getStringVal(oObj);
		if (str.length() >= 25) {
			str = str.substring(0, 20);
			str = str + "......";
		}
		return str;
	}
	
	public static String getStringVal(Object oObj, int len) {
		String str = getStringVal(oObj);
		if (str.length() > len) {
			str = str.substring(0, len);
			str = str + "...";
		}
		return str;
	}

	public static Double getUFDoubleVal(Double ufObj) {
		if (Toolkit.isEmpty(ufObj)) {
			return new Double("0.00");
		} else {
			return ufObj;
		}
	}

	/**
	 * 替换全部空格（包括空格、制表符、换页符等空白字符的其中任意一个）
	 * 
	 * @param so
	 * @return
	 */
	public static String replaceAllEmpty(String so) {
		so = so.replaceAll("\\s*", "");
		return so;
	}

	public static String getWherePartByKeys(String[] arsKey) {
		if (isEmpty(arsKey)) {
			return " is null";
		}
		if (arsKey.length == 1) {
			return "='" + arsKey[0] + "'";
		}
		return " in " + getWherePartByKeys(null, arsKey, false);

	}

	/**
	 * 拼接NOT IN 语句  李思奇 2015年10月12日 17:09:58
	 * @param arsKey
	 * @return
	 */
	public static String getWhereNotInByKeys(String[] arsKey) {
		if (arsKey.length == 1) {
			return "!='" + arsKey[0] + "'";
		}
		return " not in " + getWherePartByKeys(null, arsKey, false);

	}
	
	
	public static String getWherePartByKeys(String fld, Object[] arsKey) {
		return getWherePartByKeys(fld, arsKey, false);
	}

	public static String getWherePartByKeys(String fld, String[] arsKey) {
		return getWherePartByKeys(fld, arsKey, false);
	}

	@SuppressWarnings("unchecked")
	public static String getWherePartByKeys(String fld, String[] arsKey,
			boolean bNullAll) {
		if (isEmpty(arsKey)) {
			if (isEmpty(fld)) {
				return "'XXXX@@##1'";
			}
			return bNullAll ? " 1 = 1 " : "1>2";
		}
		if (arsKey.length == 1) {
			if (isEmpty(fld)) {
				return "'" + arsKey[0] + "'";
			}
			return fld + "='" + arsKey[0] + "'";
		}
		if (arsKey.length <= MAX_LEN) {

			if (isEmpty(fld)) {
				return organizeWhere(arsKey);
			} else {

				String where = "";
				ArrayList al = new ArrayList(arsKey.length);
				for (int i = 0; i < arsKey.length; i++) {
					if (arsKey[i] != null) {
						al.add(arsKey[i]);
					}
				}
				if (al.size() != arsKey.length) {
					where += fld + " is null or ";
				}
				where += fld + " in "
						+ organizeWhere((String[]) al.toArray(new String[0]));
				return where;
			}
		} else {
			if (isEmpty(fld)) {
				return "'XXXX@@##2'";
			}
		}
		ArrayList al = cutArray(arsKey);
		StringBuffer inpart = new StringBuffer("(");
		for (int i = 0; i < al.size(); i++) {
			inpart.append(fld + " in " + al.get(i));
			if (i < al.size() - 1) {
				inpart.append(" or ");
			}
		}
		inpart.append(")");
		return inpart.toString();
	}

	@SuppressWarnings("unchecked")
	public static String getWherePartByKeys(String fld, Object[] arsKey,
			boolean bNullAll) {
		if (isEmpty(arsKey)) {
			if (isEmpty(fld)) {
				return "'XXXX@@##3'";
			}
			return bNullAll ? " 1 = 1 " : "1>2";
		}
		if (arsKey.length == 1) {
			if (isEmpty(fld)) {
				return "'" + arsKey[0] + "'";
			}
			return fld + "='" + arsKey[0] + "'";
		}
		if (arsKey.length <= MAX_LEN) {

			if (isEmpty(fld)) {
				return organizeWhere(arsKey);
			} else {
				String where = "";
				ArrayList al = new ArrayList(arsKey.length);
				for (int i = 0; i < arsKey.length; i++) {
					if (arsKey[i] != null) {
						al.add(arsKey[i]);
					}
				}
				if (al.size() != arsKey.length) {
					where += fld + " is null or ";
				}
				where += fld + " in "
						+ organizeWhere((String[]) al.toArray(new String[0]));
				return where;
			}
		} else {
			if (isEmpty(fld)) {
				return "'XXXX@@##4'";
			}
		}
		ArrayList al = cutArray(arsKey);
		StringBuffer inpart = new StringBuffer("(");
		for (int i = 0; i < al.size(); i++) {
			inpart.append(fld + " in " + al.get(i));
			if (i < al.size() - 1) {
				inpart.append(" or ");
			}
		}
		inpart.append(")");
		return inpart.toString();
	}

	@SuppressWarnings("unchecked")
	public static ArrayList cutArray(String[] in) {
		ArrayList al = new ArrayList();
		if (in.length > MAX_LEN) {
			int current_pos = 0;
			while (current_pos < in.length) {
				int last_len = in.length - current_pos;
				int len = MAX_LEN;
				if (last_len < MAX_LEN) {
					len = last_len;
				}
				String[] strArray = new String[len];
				System.arraycopy(in, current_pos, strArray, 0, len);
				al.add(organizeWhere(strArray));
				current_pos += len;
			}
		} else {
			al.add(organizeWhere(in));
		}

		return al;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList cutArray(Object[] in) {
		ArrayList al = new ArrayList();
		if (in.length > MAX_LEN) {
			int current_pos = 0;
			while (current_pos < in.length) {
				int last_len = in.length - current_pos;
				int len = MAX_LEN;
				if (last_len < MAX_LEN) {
					len = last_len;
				}
				String[] strArray = new String[len];
				System.arraycopy(in, current_pos, strArray, 0, len);
				al.add(organizeWhere(strArray));
				current_pos += len;
			}
		} else {
			al.add(organizeWhere(in));
		}

		return al;
	}

	public static String organizeWhere(String[] in) {

		StringBuffer where = new StringBuffer();
		for (int i = 0; i < in.length; i++) {
			where.append("'" + in[i] + "',");
		}
		where.insert(0, "(");
		where.deleteCharAt(where.length() - 1);
		where.append(")");

		return where.toString();
	}

	public static String organizeWhere(Object[] in) {

		StringBuffer where = new StringBuffer();
		for (int i = 0; i < in.length; i++) {
			where.append("'" + in[i] + "',");
		}
		where.insert(0, "(");
		where.deleteCharAt(where.length() - 1);
		where.append(")");

		return where.toString();
	}

	public static String organizeWhere(int[] in) {

		StringBuffer where = new StringBuffer();
		for (int i = 0; i < in.length; i++) {
			where.append(in[i] + ",");
		}
		where.insert(0, "(");
		where.deleteCharAt(where.length() - 1);
		where.append(")");

		return where.toString();
	}

	@SuppressWarnings("unchecked")
	public static String organizeWhere(List list) {
		String[] in = (String[]) list.toArray(new String[0]);
		return organizeWhere(in);
	}

	public static boolean isEmptyOrZero(Object value) {
		if (isEmpty(value))
			return true;
		return (new BigDecimal(value.toString()).doubleValue() == 0.0);
	}

	public static boolean isStatusIn(int status, int[] area) {
		for (int i = 0; i < area.length; i++) {
			if (status == area[i])
				return true;
		}
		return false;
	}

	public static boolean isYear(String year) {
		if (isEmpty(year))
			return false;
		try {
			int iYear = Integer.parseInt(year);
			if (iYear < 1965 || iYear > 9999)
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public static void packList(List list) {
		if (list.isEmpty())
			return;
		for (int i = 0; i < list.size(); i++) {
			if (isEmpty(list.get(i))) {
				list.remove(i);
				i--;
			}
		}
	}

	public static String converseToRefCodeRule(String code) {
		String str_return = "";
		StringTokenizer st = new StringTokenizer(code, "/");
		while (st.hasMoreTokens()) {
			str_return = str_return + st.nextToken();
		}
		return str_return;
	}

	@SuppressWarnings("unchecked")
	public static List getLastYearCycle(List cyclecodes) {
		List lastyearcode = new ArrayList();
		for (Iterator iter = cyclecodes.iterator(); iter.hasNext();) {
			String cyclecode = (String) iter.next();
			if (!isEmpty(cyclecode)) {
				String cycle = getLastYearCycle(cyclecode);
				lastyearcode.add(cycle);
			}
		}
		return lastyearcode;
	}

	public static String getLastYearCycle(String cyclecode) {
		char[] chars = cyclecode.toCharArray();
		int year_1 = chars[2];
		int year_2 = chars[3];
		if (year_2 == 0) {
			year_1--;
			year_2 = 9;
		} else {
			year_2--;
		}
		chars[2] = (char) year_1;
		chars[3] = (char) year_2;
		String cycle = new String(chars);
		return cycle;
	}

	/**
	 * @param str_par
	 * @param old_item
	 * @param new_item
	 * @return
	 */
	public static synchronized String replaceAll(String str_par,
			String old_item, String new_item) {
		String str_return = "";
		String str_remain = str_par;
		boolean bo_1 = true;
		while (bo_1) {
			int li_pos = str_remain.indexOf(old_item);
			if (li_pos < 0) {
				break;
			}
			String str_prefix = str_remain.substring(0, li_pos);
			str_return = str_return + str_prefix + new_item;
			str_remain = str_remain.substring(li_pos + old_item.length(),
					str_remain.length());
		}
		str_return = str_return + str_remain;
		return str_return;
	}

	@SuppressWarnings("unchecked")
	public static synchronized String[] split(String str_par, String item) {
		String str_remain = str_par;
		boolean bo_1 = true;
		Vector aVector = new Vector();
		while (bo_1) {
			int li_pos = str_remain.indexOf(item);
			if (li_pos < 0) {
				aVector.add(str_remain);
				break;
			}
			String str_prefix = str_remain.substring(0, li_pos);
			aVector.add(str_prefix);
			str_remain = str_remain.substring(li_pos + item.length(),
					str_remain.length());
		}
		return (String[]) aVector.toArray(new String[0]);
	}

	@SuppressWarnings("unchecked")
	public static synchronized String[] changeVeToStr(Vector vector) {

		try {
			return (String[]) vector.toArray(new String[0]);
		} catch (Exception e) {
			if (isEmpty(vector)) {
				return null;
			}
			String[] aa = new String[vector.size()];
			for (int i = 0; i < vector.size(); i++) {
				aa[i] = vector.get(i).toString();

			}
			return aa;
		}

	}

	public static String setMark(String str) {
		if ((str == null) || (str.trim().length() < 3))
			return str;
		str = str.trim();
		String str0 = "";
		String str1 = str;
		if (str.startsWith("-")) {
			str0 = "-";
			str1 = str.substring(1);
		}
		int pointIndex = str1.indexOf(".");
		String str2 = "";
		String newStr = "";

		if (pointIndex != -1) {
			str2 = str1.substring(pointIndex);
			str1 = str1.substring(0, pointIndex);
		}
		int ii = (str1.length() - 1) % 3;
		for (int i = 0; i < str1.length(); i++) {
			newStr += str1.charAt(i);
			if (ii <= 0) {
				newStr += ",";
				ii += 2;
			} else
				ii--;
			ii = ii % 3;
		}
		if (newStr.length() > 0)
			newStr = newStr.substring(0, newStr.length() - 1) + str2;
		else
			newStr = str2;
		newStr = str0 + newStr;
		return newStr;
	}

	public static String combineStr(int count, char c) {

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < count; i++) {
			sb.append(c);
		}
		return sb.toString();
	}

	public static int[] convertInt(Integer[] par_int) {
		int[] li_return = new int[par_int.length];
		for (int i = 0; i < par_int.length; i++) {
			li_return[i] = par_int[i].intValue();
		}
		return li_return;
	}

	public static String convert(String[] str_pks) {
		String str_return = "";
		for (int i = 0; i < str_pks.length; i++) {
			if (str_pks[i] != null && str_pks[i].trim().length() > 0) {
				str_return = str_return + "'" + str_pks[i] + "',";
			}
		}
		str_return = str_return.substring(0, str_return.length() - 1);
		return str_return;
	}

	/**
	 * 将String对象转换成Double对象
	 * 
	 * @param so
	 *            String对象
	 * @return 转换后的Double对象
	 */

	public static Double stringToDouble(String so) {
		try {
			return new Double(so);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将String对象转换成Integer对象
	 * 
	 * @param so
	 *            String对象
	 * @return 转换后的Integer对象
	 */

	public static Integer stringToInteger(String so) {
		try {
			return new Integer(so);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将String对象转换成Double对象
	 * 
	 * @param so
	 *            String对象
	 * @return 转换后的Double对象，默认为0
	 */

	public static Double objectgToDouble(Object so) {
		return objectgToDouble(so, 0D);
	}
	
	public static Double objectgToDouble(Object so, Double defaultValue) {
		if (isEmpty(so))
			return defaultValue;
		try {
			return new Double(Toolkit.getStringVal(so));
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * 将String对象转换成Float对象
	 * 
	 * @param so
	 *            String对象
	 * @return 转换后的Double对象，默认返回0
	 */
	public static Float objectToFloat(Object so) {
		return objectToFloat(so, 0F);
	}
	
	public static Float objectToFloat(Object so, Float defaultValue) {
		if (isEmpty(so))
			return defaultValue;
		try {
			return new Float(Toolkit.getStringVal(so));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 将Object对象转换成Long对象
	 * 
	 * @param so
	 *            String对象
	 * @return 转换后的Long对象 默认返回0
	 */

	public static Long objectgToLong(Object so) {
		return objectgToLong(so,0L);
	}
	
	public static Long objectgToLong(Object so, Long defaultValue) {
		if (isEmpty(so))
			return defaultValue;
		try {
			return new Long(getStringVal(so));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 将Object对象转换成Double对象
	 * 
	 * @return 转换后的Long对象 默认返回null
	 */
	public static Long getLongValue(Object so) {
		if (isEmpty(so))
			return null;
		try {
			return new Long(getStringVal(so));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将String对象转换成Integer对象
	 * 
	 * @param so
	 *            String对象
	 * @return 转换后的Integer对象 默认返回0
	 */
	public static Integer objectgToInteger(Object so) {
		return objectgToInteger(so,0);
	}
	
	public static Integer objectgToInteger(Object so,Integer defaultValue) {
		if (isEmpty(so))
			return defaultValue;
		try {
			return new Integer(getStringVal(so));
		} catch (Exception e) {
			return new Integer(0);
		}
	}

	/**
	 * 字符串格式化为strLength个字节长度(不足则补半角空格)的方法
	 * 
	 * @param targetStr
	 * @param strLength
	 * @return
	 */
	public static String formatString(String targetStr, int strLength) {

		int curLength = 0;
		try {
			curLength = targetStr.getBytes("GBK").length;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (targetStr != null && curLength > strLength) {
			targetStr = SubStringByte(targetStr, strLength);
		}

		String newString = "";
		int cutLength = strLength - targetStr.getBytes().length;
		for (int i = 0; i < cutLength; i++)
			newString += " ";

		return targetStr + newString;
	}

	/**
	 * @param targetStr
	 * @param strLength
	 * @param encode
	 * @param IsBeforePlace 是否在前补
	 * @return
	 */
	public static String formatString(String targetStr, int strLength,
			String encode, boolean IsBeforePlace, String backup) {
		if (isEmpty(targetStr))
			targetStr = "";

		int curLength = 0;
		try {
			curLength = targetStr.getBytes(encode).length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (targetStr != null && curLength > strLength) {
			targetStr = SubStringByte(targetStr, strLength);
		}
		String newString = "";
		int cutLength = strLength - targetStr.getBytes().length;

		for (int i = 0; i < cutLength; i++)
			newString += backup;
		if (IsBeforePlace) {
			return newString + targetStr;
		} else {
			return targetStr + newString;
		}
	}

	/**
	 * 截取特定长度的字符
	 * 
	 * @param targetStr
	 * @param strLength
	 * @return
	 */
	public static String SubStringByte(String targetStr, int strLength) {
		while (targetStr.getBytes().length > strLength)
			targetStr = targetStr.substring(0, targetStr.length() - 1);
		return targetStr;
	}

	/**
	 * 按字节位置截取指定字节数长度的字符串
	 * 
	 * @param str
	 *            截取字符串
	 * @param offset
	 *            设置要截取的开始位置
	 * @param len
	 *            要截取得字节长度
	 * @param charset
	 *            字符集解码
	 * @return 返回处理后的字符串
	 */
	public static String interceptStr(String str, int offset, int len,
			String charset) throws Exception {
		byte[] byt = str.getBytes(charset);
		int setCount = 0;
		int lenCount = 0;
		int i;
		for (i = offset - 1; i >= 0; i--) {
			if (byt[i] < 0) {
				setCount++;
			}
		}
		if (setCount % 2 == 0) {
			i = offset;
		} else {
			i = offset + 1;
		}

		for (; i < (offset + len); i++) {
			if (byt[i] < 0) {
				lenCount++;
			}
		}

		if (setCount % 2 == 0 && lenCount % 2 == 0) {
			return new String(byt, offset, len, charset);
		} else if (setCount % 2 != 0 && lenCount % 2 == 0) {
			return new String(byt, offset - 1, len + 1, charset);
		} else if (setCount % 2 == 0 && lenCount % 2 != 0) {
			return new String(byt, offset, len + 1, charset);
		} else {
			return new String(byt, offset - 1, len + 2, charset);
		}
	}

	public static Long[] strings2longs(String[] ss) {
		if (isEmpty(ss)) {
			return null;
		}
		int len = ss.length;
		Long[] l = new Long[len];
		int i = 0;
		for (String s : ss) {
			l[i++] = Toolkit.objectgToLong(s);
		}
		return l;
	}
	
	public static Long[] string2longs(String s) {
		if (isEmpty(s)) {
			return null;
		}
		return strings2longs(s.split(","));
	}

	/*
	 * @author CXY date 2009-07-01 把Long型数组转换成为String 返回结果如： (1,2,3,4,5,6,7)
	 */
	public static String longs2string(Long[] ids) {
		String str = "(";
		for (int i = 0; i < ids.length; i++) {
			str += ids[i];
			if (i != ids.length - 1) {
				str += ",";
			}
		}
		str += ")";
		return str;
	}
	
	/*
	 * @author CXY date 2009-07-01 把Long型数组转换成为String 返回结果如： (1,2,3,4,5,6,7)
	 */
	public static String strArray2string(String[] ids) {
		String str = "(";
		for (int i = 0; i < ids.length; i++) {
			str += ids[i];
			if (i != ids.length - 1) {
				str += ",";
			}
		}
		str += ")";
		return str;
	}



	/**
	 * @author CXY
	 * @date:2009-8-12
	 * @param arr1
	 * @param arr2
	 * @return
	 * @description: 对两个Long型数组进行比较，若arr1中的元素存在arr2中，则将该元素放入到List中
	 *               可重载该方法，以实现对其它数据类型数组的比较
	 */
	public static List<Long> compareArray(Long[] arr1, Long[] arr2) {
		List<Long> list = new ArrayList<Long>();
		for (Long i : arr1) {
			for (Long j : arr2) {
				if (i == j) {
					list.add(i);
				}
			}
		}
		return list;
	}

	public static String clob2string(Object obj) {
		String result = null;
		try {
			if (obj != null) {
				if (obj instanceof Clob) {
					Clob clob = (Clob) obj;
					result = clob.getSubString((long) 1, (int) clob.length());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * @author CXY
	 * @date:2009-7-31
	 * @param clob
	 * @return String
	 * @description:把Clob类型转换成String类型
	 */
	public static String clob2string(Clob clob) {
		String result = null;
		try {
			if (clob != null) {
				result = clob.getSubString((long) 1, (int) clob.length());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}



	/**
	 * @author CXY
	 * @date:2009-9-1
	 * @param date
	 * @return
	 * @description:使用正则表达式验证日期格式是否正确
	 */
	public static boolean checkDate(String date) {
		String eL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?"
				+ "((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
				+ "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|"
				+ "(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|"
				+ "([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|"
				+ "([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
				+ "(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		Pattern p = Pattern.compile(eL);
		if (Toolkit.isEmpty(date)) {
			return true;
		} else {
			Matcher m = p.matcher(date);
			return m.matches();
		}
	}

	// email格式验证
	public static boolean checkEmail(String email) {
		String regex = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	// password格式验证
	public static boolean checkPassword(String password) {
		String regex = "^[A-Za-z]+$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(password);
		boolean isValid = m.matches();
		String regex2 = "^[0-9]+$";
		Pattern p2 = Pattern.compile(regex2);
		Matcher m2 = p2.matcher(password);
		boolean isValid2 = m2.matches();
		if (true == isValid && true == isValid2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @author CXY
	 * @date:2009-10-12
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 * @description:计算两个给定日期之间相差的天数
	 */
	public static int getDaysBetween(String beginDate, String endDate)
			throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date bDate;
		bDate = format.parse(beginDate);
		Date eDate = format.parse(endDate);
		Calendar d1 = new GregorianCalendar();
		d1.setTime(bDate);
		Calendar d2 = new GregorianCalendar();
		d2.setTime(eDate);
		int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2) {
			d1 = (Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);// 得到当年的实际天数
				d1.add(Calendar.YEAR, 1);
			} while (d1.get(Calendar.YEAR) != y2);
		}
		return days;
	}



	public static BigDecimal objectToBigDecimal(Object data) {
		if (data == null) {
			return new BigDecimal(0);
		} else {
			try {
				return new BigDecimal(Toolkit.getStringVal(data));
			} catch (Exception e) {
				//e.printStackTrace();
				return new BigDecimal(0);
			}
		}

	}
	
    /**
     * @param srcString
     * @param flag
     * @return
     */
    public static String toLowerCaseInitial(String srcString, boolean flag) {
		StringBuilder sb = new StringBuilder();
		if (flag) {
			sb.append(Character.toLowerCase(srcString.charAt(0)));
		} else {
			sb.append(Character.toUpperCase(srcString.charAt(0)));
		}
		sb.append(srcString.substring(1));
        return sb.toString();
    }
    
	
	/**
	 * 拼接id用于SQL查询
	 * @author kavonne
	 * @date Apr 19, 2011
	 * @time 7:38:14 PM
	 * @param ids
	 * @return
	 */
	public static String ids2String(Object[] ids) {
		if (Toolkit.isEmpty(ids)) {
			return "('-1')";
		}
		StringBuffer idBuffer = new StringBuffer();
		idBuffer.append("('-1'");
		for (int i = 0; i < ids.length; i++) {
			idBuffer.append(",");
			idBuffer.append("'");
			idBuffer.append(ids[i]);
			idBuffer.append("'");
		}
		idBuffer.append(")");
		return idBuffer.toString();
	}
	
	/**
	 * 将字符串取掉所有空格
	 * @param str
	 * @return
	 */
	public static String stringTrim(String str) {
		if (Toolkit.isEmpty(str)) {
			return null;
		} 
		return str.replace(" ", "");
	}
	
	public static void main(String[] args) {
		
	}
}