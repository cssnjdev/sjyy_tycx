package com.cwks.delegate;

import com.caucho.hessian.client.HessianProxyFactory;
import com.cwks.bizcore.comm.vo.RequestInfo;
import com.cwks.bizcore.comm.vo.ResponseInfo;

import java.net.MalformedURLException;

/**
 * <p>Title:CssWebSeriviceHelper</p>
 * <p>Description: CssWebSeriviceHelper</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: cssnj</p>
 * @author 胡锐
 * @version 1.0
 */
public class CssWebSeriviceHelper {

	private static boolean initialized = false;

	//经过类的初始化后成为全局的一个实例.
	private static CssWebSeriviceHelper helper = null;

	private CssWebSeriviceHelper() {
		init();
	}

	public static void reload() {
		helper = new CssWebSeriviceHelper();
	}

	/**
	 * 对外提供包装请求webservice的方法
	 */
	public ResponseInfo invokeTask(String requestKey, RequestInfo reqInfo) throws Exception {
		ResponseInfo resinfo = new ResponseInfo();
		if(reqInfo == null){
			return null;
		}
		Object obj = ClientContext.singleton().getServiceInfo(requestKey);
		if(obj == null){
			resinfo.setIssuccess(false);
			resinfo.setMessage(" requestKey ["+requestKey+"] exception:requestKey is null");
			return resinfo;
		}
		RequestServerInfoVo info = (RequestServerInfoVo)obj;
		if("".equals(info.getUrl())){
			resinfo.setIssuccess(false);
			resinfo.setMessage(" requestKey ["+requestKey+"] exception:url is null");
			return resinfo;
		}
		HessianProxyFactory factory = new HessianProxyFactory();
		if(reqInfo.getREQUEST_TIMEOUT() > 0){
			factory.setReadTimeout(reqInfo.getREQUEST_TIMEOUT());
		}else{
			factory.setReadTimeout(60000);//默认为1分钟
		}
		ICssServiceApi reqImpl = null;
		try {
			reqImpl = (ICssServiceApi) factory.create(ICssServiceApi.class,info.getUrl());
		} catch (MalformedURLException e) {
			resinfo.setIssuccess(false);
			resinfo.setMessage(" requestKey ["+requestKey+"]  connect exception: " + e.getMessage());
			//System.out.println("occur exception: " + e);
			throw new Exception(e.getMessage());
		}
		try {
			resinfo = reqImpl.invokeTask(reqInfo);
			System.out.println(" requestKey ["+requestKey+"] retuen msg：" + resinfo);
		} catch (Exception e) {
			resinfo.setIssuccess(false);
			resinfo.setMessage(" requestKey ["+requestKey+"] invokeTask exception: " + e.getMessage());
			//System.out.println("invokeTask exception: " + e);
			throw new Exception(e.getMessage());
		}
		return resinfo;

	}
	


	/**
	 * 对外提供包装请求webservice的方法
	 */
	public ResponseInfo invokeTask(RequestInfo reqInfo) throws Exception {
		ResponseInfo resinfo = new ResponseInfo();
		if(reqInfo == null){
			return null;
		}
		if("".equals(reqInfo.getReqUrl())){
			resinfo.setIssuccess(false);
			resinfo.setMessage(" requesturl ["+reqInfo.getReqUrl()+"] exception:url is null");
			return resinfo;
		}
		HessianProxyFactory  factory = new HessianProxyFactory();
		if(reqInfo.getREQUEST_TIMEOUT() > 0){
			factory.setReadTimeout(reqInfo.getREQUEST_TIMEOUT());
		}else{
			factory.setReadTimeout(60000);//默认为1分钟
		}
		ICssServiceApi reqImpl = null;
		try {
			reqImpl = (ICssServiceApi) factory.create(ICssServiceApi.class,reqInfo.getReqUrl());
		} catch (MalformedURLException e) {
			resinfo.setIssuccess(false);
			resinfo.setMessage(" requestUrl ["+reqInfo.getReqUrl()+"]  connect exception: " + e.getMessage());
			//System.out.println("occur exception: " + e);
			throw new Exception(e.getMessage());
		}
		try {
			resinfo = reqImpl.invokeTask(reqInfo);
			System.out.println(" requestUrl ["+reqInfo.getReqUrl()+"] retuen msg：" + resinfo);
		} catch (Exception e) {
			resinfo.setIssuccess(false);
			resinfo.setMessage(" requestUrl ["+reqInfo.getReqUrl()+"] invokeTask exception: " + e.getMessage());
			//System.out.println("invokeTask exception: " + e);
			throw new Exception(e.getMessage());
		}
		return resinfo;

	}

	/**
	 * 通过此方法返回访问系统参数的全局实例
	 */
	public static CssWebSeriviceHelper singleton() {
		if (helper == null) {
			helper = new CssWebSeriviceHelper();
		}
		return helper;
	};

	private void init() {
		if (initialized) {
			return;
		}
		this.setInitialized(true);
	}

	/**
	 * 请除参数缓存中所有的参数数据.
	 * 
	 * 输入参数：无 返回：无
	 */
	public void clear() {
		initialized = false;
	}

	/**
	 * 判断系统是否已经初始化
	 * 
	 * 输入参数：无 返回： true:已经初始化 false:没有初始化
	 */
	public boolean isInitialized() {
		return initialized;
	}

	/**
	 * 设置系统参数是否已经初始化。
	 * 
	 * 输入参数： flag:boolean. true:系统已经初始化 false:系统没有初始化 返回：无
	 */
	private void setInitialized(boolean flag) {
		initialized = flag;
	}

	/**
	 * 重新加载系统配置参数
	 */
	public void reLoadConfig() {
		this.clear();
		this.init();
	}

	/**
	 * 启动方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CssWebSeriviceHelper.singleton();
	}
}