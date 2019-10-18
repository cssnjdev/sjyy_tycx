package com.cwks.delegate;


import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * <p>Title: ClientContext</p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: cssnj</p>
 * @author 胡锐
 * @version 1.0
 */
public class ClientContext {
	public static final String CLIENT_PRO = "core/ws_config.properties";
	public static final String CLIENT_REQUEST_KEY = "service.request.key";
	public static final String CLIENT_REQUEST_NAME = "service.request.name";
	public static final String CLIENT_REQUEST_URL = "service.request.url";
	public static final String CLIENT_REQUEST_TIMEOUT = "service.request.timeout";

	/**
	 * 表示service参数是否已经被初始化了. false:表示service参数没有初始化 true :表示service已经初始化了参数
	 * 通过这个参数判断是否要重新加载service的初始化参数。
	 */
	private static boolean initialized = false;
	
	/**
	 * 是一个静态的Map变量,存放service的参数值,是以key=value的方式来存放的.
	 */
	private static Map<String, String> contextPool = null;
	
	/**
	 * 是一个静态的Map变量,存放webservice的参数Map,是以key=service.request.key的方式来存放的.
	 */

	/**
	 * 标记了service是否处于Debug状态: true, 表示为处于Debug状态 false,表示为处于非Debug状态
	 */
	private static boolean appDebug = true;

	/**
	 * LoadContext的静态变量,经过类的初始化后成为全局的一个实例.
	 */
	private static ClientContext ClientContext = null;

	public static void reload() {
		ClientContext = new ClientContext();
	}




	/**
	 * 通过此方法返回访问service参数的全局实例,并且通过这个实例可以访问service的所有参数.
	 * 
	 * 输入参数：无 返回：LoadContext的实例. modified 周红江 since 2008-08-19
	 * description 修改程序的逻辑判断
	 */
	public static ClientContext singleton() {
		if (ClientContext == null) {
			ClientContext = new ClientContext();
		}
		return ClientContext;
	};
	/**
	 * 是一个静态的Map变量,存放webservice的参数Map,是以key=service.request.key的方式来存放的.
	 */
	private static Map<String, RequestServerInfoVo> clientPool = null;

	/**
	 * 根据给定的参数key值返回相应的参数值对象。
	 *
	 * 输入参数： key:Object,参数关键字 返回：参数值对象。
	 */
	public Object getServiceInfo(String key) {
		return clientPool.get(key);
	}
	/**
	 * 通过输入service的参数key值来在LoadContext对象实例中查找参数列表中是否存在此参数值。
	 * 
	 * 输入参数： key:Object,参数关键字 返回： false:不存在 true :存在
	 */
	private boolean containsKey(Object key) {
		return contextPool.containsKey(key);
	}

	/**
	 * 输入service的参数关键字对应的值，通过LoadContext对象实例在参数列表中查找是否存在此参数值。
	 * 
	 * 输入参数： value:Object 参数关键字的对应值 返回： false:不存在 true :存在
	 */
	private boolean containsValue(Object value) {
		return contextPool.containsValue(value);
	}

	/**
	 * 根据给定的参数key值返回相应的参数值对象。
	 * 
	 * 输入参数： key:Object,参数关键字 返回：参数值对象。
	 */
	public Object getValue(String key) {
		return contextPool.get(key);
	}

	/**
	 * 根据给定的key值以字符串的方式返回值.
	 * 
	 * 输入参数： key:String,参数关键字 返回：字符串类型的参数值
	 */
	public String getValueAsString(String key) {
		return (String) this.getValue(key);
	}

	/**
	 * 获取含有键值key的property集合
	 * 
	 * @param key
	 *            键值
	 * @return 含有键值key的property集合
	 */
	public Map getValWith(String key) {
		Map vals = new HashMap();
		Set keys = contextPool.keySet();
		Iterator itKeys = keys.iterator();
		while (itKeys.hasNext()) {
			Object objKey = itKeys.next();
			if (objKey instanceof String) {
				String strKey = (String) objKey;
				if (strKey.indexOf(key) != -1) {
					vals.put(strKey, contextPool.get(strKey));
				}
			}
		}
		return vals;
	}

	/**
	 * 判断service是否已经初始化
	 * 
	 * 输入参数：无 返回： true:已经初始化 false:没有初始化
	 */
	public boolean isInitialized() {
		return initialized;
	}

	/**
	 * serviceDegub开关是否已经打开。
	 * 
	 * 输入参数：无 返回： true:debug开关打开 false:debug开关没有打开
	 */
	public boolean isAppDebug() {
		return appDebug;
	}

	/**
	 * 设置service参数是否已经初始化。
	 * 
	 * 输入参数： flag:boolean. true:service已经初始化 false:service没有初始化 返回：无
	 */
	private void setInitialized(boolean flag) {
		initialized = flag;
	}

	/**
	 * 设置service的debug状态值。
	 * 
	 * 输入参数： flag:boolean. true: service处于debug状态 false:service没有非debug状态 返回：无
	 */
	public void setIsDebug(boolean flag) {
		appDebug = flag;
	}


	/**
	 * 覆盖方法 toString
	 * 
	 * @return
	 */
	public String toString() {
		return "{ appDebug = " + appDebug + ", initialized = "
				+ initialized + ", contextPool = [" + contextPool
				+ "] }";
	}

	/**
	 * 启动方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ClientContext.singleton();
		Object obj = ClientContext.singleton().getValue("service");
//		String s_c = com.ctp.core.commutils.StringUtils.isoToUTF8(map.getServiceClass());
//		System.out.println(s_c);
	}
}