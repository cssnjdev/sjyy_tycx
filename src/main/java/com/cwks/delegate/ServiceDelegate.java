package com.cwks.delegate;

import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.comm.vo.RequestInfo;
import com.cwks.bizcore.comm.vo.ResponseInfo;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.core.exception.BizException;
import com.cwks.common.core.systemConfig.SystemApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
/**
 * <p>Title: ServiceDelegate.java</p>
 * <p>Description: cssnj</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: cssnj</p>
 * @author 胡锐
 * @version 1.0
 */
public class ServiceDelegate {
	private static Logger logger = LoggerFactory.getLogger(ServiceDelegate.class);

	public static ResponseEvent delegate(RequestEvent requestevent) throws Exception {
		ResponseEvent responseEvent = null;
		Object open = SystemApplicationContext.singleton().getValueAsString("ctp.distributed.open");
		Object type = SystemApplicationContext.singleton().getValueAsString("ctp.distributed.type");
		try {
			if(open != null && "true".equals(open)){
				if(type != null && "MQ".equals(type)){

				}else if(type != null && "HTTP".equals(type)){
					RequestInfo reqInfo = new RequestInfo();
					reqInfo.setREQUEST_TIMEOUT(36000);//10分钟 此处不设置则为1分钟
					reqInfo.setREQUEST_USER("cssnjworks");
					reqInfo.setREQUEST_PASSWORD("cssnj");
					reqInfo.setReqevn(requestevent);
					ResponseInfo resInfo = CssWebSeriviceHelper.singleton().invokeTask("CSSNJ.SERVICE_LOCAL",reqInfo);
					responseEvent = resInfo.getResevn();
				}else if(type != null && "SOCKET".equals(type)){

				}
			}else{
				responseEvent = (ResponseEvent) LocalServiceLocator.delegate(requestevent);
			}
		}catch (IllegalArgumentException e) {
			logger.error("in biz [" + requestevent.getHandleCode()+ " error:",e);
			throw new Exception("500 Service runing exception:"+getStackMsg(e));
		} catch (IllegalAccessException e) {
			logger.error("in biz [" + requestevent.getHandleCode()+ " error:",e);
			throw new Exception("500 Service runing exception:"+getStackMsg(e));
		} catch (InvocationTargetException e) {
			if(e.getTargetException() == null || e.getTargetException().getCause()==null){
				logger.error("500 Service runing exception:"+getStackMsg(e.getTargetException()),e);
				throw new Exception("500 Service runing exception:"+getStackMsg(e.getTargetException()));
			}else{
				logger.error("in biz [" + requestevent.getHandleCode()+ " error:",e);
				if(e.getTargetException().getCause().getClass() != null && e.getTargetException().getCause().getClass().getCanonicalName() != null
						&& "com.cssnj.core.exception.BizException".equals(e.getTargetException().getCause().getClass().getCanonicalName())){
					throw new BizException(e.getTargetException().getCause().getMessage(),e);
				}else{
					throw new Exception("500 Service runing exception:"+getStackMsg(e.getTargetException().getCause()));
				}
			}
		} catch (RuntimeException e) {
			logger.error("in biz [" + requestevent.getHandleCode()+ " error:",e);
			throw e;
		} catch (NoSuchMethodException e) {
			logger.error("in biz [" + requestevent.getHandleCode()+ " error:",e);
			logger.error("500 Service runing exception:"+getStackMsg(e));
			throw new Exception("500 Service runing exception:"+getStackMsg(e));
		}catch (BizException e) {
			logger.error("in biz [" + requestevent.getHandleCode()+ " biz error:",e);
			throw e;
		}catch (Exception e) {
			logger.error("in biz [" + requestevent.getHandleCode()+ " error:",e);
			throw e;
		}
		return responseEvent;
	}

	public static String createSendSOAPPackage(RequestEvent requestevent) {
		//本地方式请求报文模版
		StringBuffer sb_demo_local= new StringBuffer(); // 用于接收返回的XML格式数据
		sb_demo_local.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb_demo_local.append("<reqws>");
		sb_demo_local.append("<tran_id></tran_id>");
		sb_demo_local.append("<channel_id>"+requestevent.getBeanId()+"_"+requestevent.getHandleCode()+"</channel_id>");
		sb_demo_local.append("<tran_seq></tran_seq>");
		sb_demo_local.append("<body>");
		sb_demo_local.append("<![CDATA[");
		sb_demo_local.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb_demo_local.append("<reqMap>"+ JsonUtil.toJson(requestevent.getReqMap())+"</reqMap>");
		sb_demo_local.append("<requestMap>"+JsonUtil.toJson(requestevent.getRequestMap())+"</requestMap>");
		sb_demo_local.append("<ctx>"+JsonUtil.toJson(requestevent.getCtx())+"</ctx>");
		sb_demo_local.append("]]>");
		sb_demo_local.append("</body>");
		sb_demo_local.append("</reqws>");
		return sb_demo_local.toString();
	}

	private static String getStackMsg(Exception e) {
		StringBuffer sb = new StringBuffer();
		StackTraceElement[] stackArray = e.getStackTrace();
		sb.append(e.getMessage());
		for (int i = 0; i < stackArray.length; i++) {
			StackTraceElement element = stackArray[i];
			sb.append("\t"+element.toString() + "\n");
		}
		return sb.toString();
	}

	private static String getStackMsg(Throwable e) {
		StringBuffer sb = new StringBuffer();
		StackTraceElement[] stackArray = e.getStackTrace();
		sb.append(e.getMessage());
		for (int i = 0; i < stackArray.length; i++) {
			StackTraceElement element = stackArray[i];
			sb.append("\t"+element.toString() + "\n");
		}
		return sb.toString();
	}
}
