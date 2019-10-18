package com.cwks.bizcore.log;

import com.alibaba.fastjson.JSONObject;
import com.cwks.common.core.systemConfig.BizContext;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletException;
import javax.sql.DataSource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 初始化类，将数据交换平台前置服务需要的东西放到缓存里
 * <p>Title: JhQzFwContext.java</p>
 * <p>Description: 初始化类，将数据交换平台前置服务需要的东西放到缓存里</p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: cssnj</p>
 * @author 胡锐
 * @version 1.0
 */
@Configuration
public class JhQzFwContext {
	private static Logger logger = LoggerFactory.getLogger(JhQzFwContext.class);

	private static ConcurrentHashMap<String, ConcurrentHashMap<String, String>> resourceRulesMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, String>>();
	private static ConcurrentHashMap<String, ConcurrentHashMap<String, String>> dataSourceMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, String>>();
	private static ConcurrentHashMap<String, ConcurrentHashMap<String, Object>> resourceClientsMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, Object>>();
	private ConcurrentHashMap dBPoolConnectionMap = new ConcurrentHashMap();
//		@Autowired
//		RestTemplate restTemplate;
//	private RestTemplate restTemplate = (RestTemplate) SpringContextUtil.getBean("restTemplate");

	private static String JHDL_RESTAPI_NBFW_ZX_CONFIG_URL = BizContext.singleton().getValueAsString("biz.sjjhpt.qzfw.core.nbfw.config.restful.url");

	private static String JHDL_QZFW_ACCESS_CLIENT_ID = BizContext.singleton().getValueAsString("biz.sjjhpt.qzfw.core.access.client.id");

	//mq
	private static String JHDL_CORE_WBFW_SECRET_PWD = BizContext.singleton().getValueAsString("biz.sjjhpt.core.secret.pwd");
	
	private static String appHost = "";

	/**
	 * 表示系统参数是否已经被初始化了. false:表示系统参数没有初始化 true :表示系统已经初始化了参数
	 * 通过这个参数判断是否要重新加载系统的初始化参数。
	 */
	private boolean initialized = false;


	/**
	 * ApplicationContext的静态变量,经过类的初始化后成为全局的一个实例.
	 */
	private static JhQzFwContext applicationContext = null;

    /**
     * ApplicationContext的构造器，初始化创建ApplicationContext的实例和存放系统参数 的Map的实例。
     * 输入参数：无 返回：无
     */
	public JhQzFwContext() {
        try {
        	init();
		} catch (Exception e) {
			logger.info("初始化系统异常："+e.getMessage());
		}
    }

	/**
	 * Initialization of the servlet. <br>
	 * Init application and using it in class that named "SpringContextUtil".
	 * @throws ServletException
	 */
	public void init() throws ServletException {
		logger.info("===============JhQzFwContext starting Initialize (jhdl)==================>>>>>");
		if (this.initialized) {
			return;
		}
		startDataSourcesByLocal();
		setHost();
		this.setInitialized(true);
		logger.info("===============JhQzFwContext end Initialized (jhdl)==================!!!!!");
	}

	/**
	 * 设置系统参数是否已经初始化。
	 * 输入参数： flag:boolean. true:系统已经初始化 false:系统没有初始化 返回：无
	 */
	private void setInitialized(boolean flag) {
		this.initialized = flag;
	}

	/**
	 * 重新加载系统配置参数
	 * @throws ServletException
	 */
	public void reLoadConfig() throws ServletException {
		this.clear();
		this.init();
	}

	/**
	 * 通过此方法返回访问系统参数的全局实例,并且通过这个实例可以访问系统的所有参数.
	 *
	 * 输入参数：无 返回：JhQzFwContext的实例
	 */
	public static JhQzFwContext singleton() {
		if (applicationContext == null) {
			applicationContext = new JhQzFwContext();
			// applicationContext.init();
		}
		return applicationContext;
	}

	private void setHost(){
		try {
			InetAddress inetAddr = InetAddress.getLocalHost();
			byte[] addr = inetAddr.getAddress();
			// Convert to dot representation
			String ipAddr = "";
			for (int i = 0; i < addr.length; i++) {
				if (i > 0) {
					ipAddr += ".";
				}
				ipAddr += addr[i] & 0xFF;
			}
			appHost = ipAddr;
		} catch (UnknownHostException e) {
			logger.info("Host not found: " + e.getMessage());
		}
	}

//	private void startResourceAndDataSourcesInfoByCenter() {
//		logger.info("--------------- 开始从交换管理中心加载前置服务资源处理规则和数据源信息------------->");
//		try {
//			String result=null;
//			JSONObject obj=null;
//			RestfulHttpClient.HttpClient client = RestfulHttpClient.getClient(JHDL_RESTAPI_NBFW_ZX_CONFIG_URL);
//			LogWritter.bizDebug("\n client:::::::::::::::::"+client.getUrl());
//			client.post();
//			client.connectTimeout(20000);
//			client.readTimeout(60*10000);
//			client.addHeader("Content-type", "application/json;charsert=UTF-8");
//			client.addHeader("ACCESS-CLIENT-ID",JHDL_QZFW_ACCESS_CLIENT_ID);
//
//
//			ConcurrentHashMap reqmap = new ConcurrentHashMap();
//			client.body(JsonUtil.toJson(reqmap));
//			//发起请求，获取响应结果
//			RestfulHttpClient.HttpResponse response = null;
//			try {
//				response = client.request();
//			} catch (IOException e) {
//				logger.error("\n调用restful接口异常::::::::::::::::::::::",e);
//			}
//			//根据状态码判断请求是否成功
//			ConcurrentHashMap resourcesMap = null;
//			ConcurrentHashMap datasourcesMap =null;
//			if(response != null) {
//				if (response.getCode() == 200) {
//					//获取响应内容
//					result = response.getContent();
//					obj = (JSONObject) JSONObject.parse(result);
//					if(obj != null){
//						JSONObject o1 = (JSONObject)obj.get("JHQZFW_RESOURCES_MAP");
//						resourcesMap=reSourceJsonObjctToMap(o1);
//						JSONObject o2 = (JSONObject)obj.get("JHQZFW_DATASOURCE_MAP");
//						datasourcesMap=dataSourceJsonObjctToMap(o2);
//						if ((resourcesMap == null || resourcesMap.size()<=0) &&(datasourcesMap == null || datasourcesMap.size()<=0)) {
//							logger.error("ERROR:交换管理中心推送的配置信息[接入点前置资源信息和数据源信息]为空",null);
//							System.exit(0);
//						}else{
//							if (resourcesMap != null && resourcesMap.size()>0) {
//								resourceRulesMap = resourcesMap;
//							}
//							if (datasourcesMap != null && datasourcesMap.size()>0) {
//								dataSourceMap = datasourcesMap;
//							}
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			logger.error("从交换管理中心加载前置服务资源处理规则和数据源信息错误：",e);
//		}
//		logger.info("--------------- 加载交换管理中心前置服务资源处理规则和数据源信息结束------------->");
//	}


//	private void startResourceDealRulesByLocal() {
//		logger.info("--------------- 开始加载前置服务资源处理规则------------->");
//		SAXBuilder builder = new SAXBuilder();
//		try {
//			Document doc = builder.build(this.getClass().getResource("/config/jhdl/jhqzfw_resources.xml"));
//			Element root = doc.getRootElement();
//			List<?> list = root.getChildren();
//			Element ruleElm = null;
//			String id, des;
//			ConcurrentHashMap<String, String> tempMap = null;
//			resourceRulesMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, String>>();
//			List<Element> listE=null;
//			try {
//				for (int i = 0; i < list.size(); i++) {
//					ruleElm = (Element) list.get(i);
//					id = ruleElm.getAttributeValue("id");
//					des = ruleElm.getAttributeValue("des");
//					logger.info("加载资源id：" + id + "(" + des + ")交换服务配置规则.....");
//						tempMap = new ConcurrentHashMap<String, String>();
//						tempMap.put("resourceId", id);
//						if (des == null) {des = "";}
//						tempMap.put("des", des);
//						listE=ruleElm.getChildren();
//						for(Element e:listE) {
//							tempMap.put(e.getName(), e.getText());
//						}
//						resourceRulesMap.put(id, tempMap);
//				}
//			} catch (Exception e) {
//				logger.info("加载资源id：\" + id + \"(\" + des + \")交换服务配置规则出现错误!");
//			}
//		} catch (Exception e) {
//			logger.info("加载resources/config/jhdl/jhqzfw_resources.xml出现错误："+e.getMessage());
//		}
//		logger.info("--------------- 加载前置服务资源处理规则结束------------->");
//	}
	private void startDataSourcesByLocal() {
		logger.info("--------------- 开始加载前置服务资源处理规则------------->");
		SAXBuilder builder = new SAXBuilder();
		try {
			Document doc = builder.build(this.getClass().getResource("/config/datasources.xml"));
			Element root = doc.getRootElement();
			List<?> list = root.getChildren();
			Element ruleElm = null;
			String id, des,type;
			ConcurrentHashMap<String, String> tempMap = null;
			dataSourceMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, String>>();
			List<Element> listE=null;
			try {
				for (int i = 0; i < list.size(); i++) {
					ruleElm = (Element) list.get(i);
					id = ruleElm.getAttributeValue("id");
					des = ruleElm.getAttributeValue("des");
					type = ruleElm.getAttributeValue("type");
					logger.info("加载资源id：" + id + "(" + des + ")交换服务配置规则.....");
					tempMap = new ConcurrentHashMap<String, String>();
					tempMap.put("dataSourceId", id);
					if (des == null) {des = "";}
					tempMap.put("des", des);
					tempMap.put("type", type);
					listE=ruleElm.getChildren();
					for(Element e:listE) {
						tempMap.put(e.getName(), e.getText());
					}
					dataSourceMap.put(id, tempMap);
				}
			} catch (Exception e) {
				logger.info("加载资源id：\" + id + \"(\" + des + \")交换服务配置规则出现错误!");
			}
		} catch (Exception e) {
			logger.info("加载resources/config/jhdl/datasources.xml出现错误："+e.getMessage());
		}
		logger.info("--------------- 加载前置服务资源处理规则结束------------->");
	}

	public ConcurrentHashMap getResourceRuleMapById(String resourceid) {
		ConcurrentHashMap ruleMap = null;
		if(resourceRulesMap != null){
			if(resourceRulesMap.get(resourceid) != null){
				ruleMap = (ConcurrentHashMap)resourceRulesMap.get(resourceid);
			}else{
				String tmpkey = "";
				for(String key : resourceRulesMap.keySet()) {
					if(key.indexOf("*") != -1){
						tmpkey = key.replaceAll("[*]","");
						if(resourceid.indexOf(tmpkey) != -1){
							ruleMap = (ConcurrentHashMap)resourceRulesMap.get(key);
						}
					}
				}
			}
		}
		return ruleMap;
	}
	@SuppressWarnings("rawtypes")
	public ConcurrentHashMap getResourceClientMapById(String tranId) {
		ConcurrentHashMap ruleMap = null;
		if(resourceClientsMap != null){
			if(resourceClientsMap.get(tranId) != null){
				ruleMap = (ConcurrentHashMap)resourceClientsMap.get(tranId);
			}
		}
		return ruleMap;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ConcurrentHashMap dataSourceJsonObjctToMap(JSONObject obj) {
		ConcurrentHashMap ruleMap = new ConcurrentHashMap();
		 Set<String> set = obj.keySet();
		 Iterator<String> it=set.iterator();
		 String key=null;
		 JSONObject o=null;
		 ConcurrentHashMap tempMap =null;
		 while(it.hasNext()) {
			 tempMap=new ConcurrentHashMap();
			 key=it.next();
			 o=(JSONObject) obj.get(key);
			 tempMap.put("ClassMethodName", o.get("CLASSMETHODNAME")+"");
			 tempMap.put("driverClassName",o.get("DRIVERCLASSNAME")+"");
			 tempMap.put("dataSourceId", o.get("ID")+"");
			 tempMap.put("conntype", o.get("CONNTYPE")+"");
			 tempMap.put("initialSize", o.get("INITIALSIZE")+"");
			 tempMap.put("logAbandoned", o.get("LOGABANDONED")+"");
			 tempMap.put("maxActive", o.get("MAXACTIVE")+"");
			 tempMap.put("maxPoolPreparedStatementPerConnectionSize", o.get("MAXPOOLPREPAREDRCONNECTIONSIZE")+"");
			 tempMap.put("maxWait", o.get("MAXWAIT")+"");
			 tempMap.put("minEvictableIdleTimeMillis", o.get("MINEVICTABLEIDLETIMEMILLIS")+"");
			 tempMap.put("minIdle", o.get("MINIDLE")+"");
			 tempMap.put("des", o.get("MS")+"");
			 tempMap.put("password", o.get("PASSWORD")+"");
			 tempMap.put("poolPreparedStatements",o.get("POOLPREPAREDSTATEMENTS")+"");
			 tempMap.put("removeAbandoned", o.get("REMOVEABANDONED")+"");
			 tempMap.put("removeAbandonedTimeout", o.get("REMOVEABANDONEDTIMEOUT")+"");
			 tempMap.put("testOnBorrow", o.get("TESTONBORROW")+"");
			 tempMap.put("testOnReturn", o.get("TESTONRETURN")+"");
			 tempMap.put("testWhileIdle", o.get("TESTWHILEIDLE")+"");
			 tempMap.put("timeBetweenEvictionRunsMillis", o.get("TIMEBETWEENEVICTIONRUNSMILLIS")+"");
			 tempMap.put("type",o.get("TYPE")+"");
			 tempMap.put("url", o.get("URL")+"");
			 tempMap.put("username", o.get("USERNAME")+"");
			 tempMap.put("validationQuery",o.get("VALIDATIONQUERY")+"");
			 tempMap.put("soapHead",o.get("SOAPHEAD")+"");
			 tempMap.put("soapEnd",o.get("SOAPEND")+"");
			 tempMap.put("soapFilterHead",o.get("SOAPFILTERHEAD")+"");
			 tempMap.put("soapFilterEnd",o.get("SOAPFILTEREND")+"");
			 ruleMap.put(key, tempMap);
		 }
		return ruleMap;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ConcurrentHashMap reSourceJsonObjctToMap(JSONObject obj) {
		ConcurrentHashMap ruleMap  = new ConcurrentHashMap();
		Set<String> set = obj.keySet();
		 Iterator<String> it=set.iterator();
		 String key=null;
		 JSONObject o=null;
		 ConcurrentHashMap tempMap =null;
		 while(it.hasNext()) {
			 tempMap=new ConcurrentHashMap();
			 key=it.next();
			 o=(JSONObject) obj.get(key);
			 tempMap.put("isDataCompleteTips", o.get("ISDATACOMPLETETIPS")+"");
			 tempMap.put("isDataConfirmation", o.get("ISDATACONFIRMATION")+"");
			 tempMap.put("isScheduler", o.get("ISSCHEDULER")+"");
			 tempMap.put("mbid", o.get("MBID")+"");
			 tempMap.put("processTemplete", o.get("PROCESSTEMPLETE")+"");
			 tempMap.put("reqTemplete", o.get("REQTEMPLETE")+"");
			 tempMap.put("resourceId", o.get("RESOURCE_ID")+"");
			 tempMap.put("resTemplete", o.get("RESTEMPLETE")+"");
			 tempMap.put("templeteDataSources", o.get("TEMPLETEDATASOURCES")+"");
			 tempMap.put("des", o.get("ZYMS")+"");
			 tempMap.put("isActivation", o.get("ZYZT")+"");
			 tempMap.put("timeout", o.get("TIMEOUT")+"");
			 tempMap.put("allowReqIp", o.get("ALLOWREQIP")+"");
			 tempMap.put("allowResIp", o.get("ALLOWRESIP")+"");
			 tempMap.put("isRollback", o.get("ISROLLBACK")+"");
			 tempMap.put("localRestFulApi", o.get("LOCALRESTFULAPI")+"");
			 tempMap.put("wfProcessDefinitionID", o.get("WFPROCESSDEFINITIONID")+"");
			 tempMap.put("schedulerCommont", o.get("SCHEDULERCOMMONT")+"");
			 ruleMap.put(key, tempMap);
		 }
		return ruleMap;
	}

	//获取系统缓存表
	public ConcurrentHashMap getResourceRulesMap() {
		return resourceRulesMap;
	}
	public ConcurrentHashMap getDataSourceMap() {
		return dataSourceMap;
	}


	public void setResourceRulesMap(ConcurrentHashMap<String, ConcurrentHashMap<String, String>> resourceRulesMap) {
		this.resourceRulesMap = resourceRulesMap;
	}

	public void setDataSourceMap(ConcurrentHashMap<String, ConcurrentHashMap<String, String>> dataSourceMap) {
		this.dataSourceMap = dataSourceMap;
	}
	public String getAppHost() {
		return appHost;
	}


	public String getJhdlRestapiNbfwZxConfigUrl() {
		return JHDL_RESTAPI_NBFW_ZX_CONFIG_URL;
	}

	public void setJhdlRestapiNbfwZxConfigUrl(String jhdlRestapiNbfwZxConfigUrl) {
		JHDL_RESTAPI_NBFW_ZX_CONFIG_URL = jhdlRestapiNbfwZxConfigUrl;
	}

	public String getJhdlQzfwAccessClientId() {
		return JHDL_QZFW_ACCESS_CLIENT_ID;
	}

	public void setJhdlQzfwAccessClientId(String jhdlQzfwAccessClientId) {
		JHDL_QZFW_ACCESS_CLIENT_ID = jhdlQzfwAccessClientId;
	}


	public ConcurrentHashMap<String, DataSource> getDBPoolConnectionMap() {
		System.out.println("--------------------------------------------dBPoolConnectionMap"+dBPoolConnectionMap);
		return dBPoolConnectionMap;
	}

	public void setDBPoolConnectionMap(ConcurrentHashMap<String, DataSource> dBPoolConnectionMap) {
		this.dBPoolConnectionMap = dBPoolConnectionMap;
	}


	public  String getJhdlCoreWbfwSecretPwd() {
		return JHDL_CORE_WBFW_SECRET_PWD;
	}

	private static String noEmpty(String str) {
		if ("null".equals(str)) {
			return "";
		}
		return str;
	}


	/**
	 * 请除参数缓存中所有的参数数据.
	 * 输入参数：无 返回：无
	 */
	public void clear() {
		this.initialized = false;
		logger.info("清除参数缓存中所有的参数数据");
	}

	/**
	 * 启动方法
	 * @param args
	 */
	public static void main(String[] args) {
		JhQzFwContext.singleton();
	}
}
