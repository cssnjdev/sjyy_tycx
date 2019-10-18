package com.cwks.delegate;

import java.lang.reflect.Method;

import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.core.systemConfig.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * LocalServiceLocator
 * @author 胡锐
 * @version 1.0
 */
public class LocalServiceLocator {
	private static Logger logger = LoggerFactory.getLogger(LocalServiceLocator.class);

	public static ClassPathXmlApplicationContext context;
	
	public static ResponseEvent delegate(RequestEvent requestEvent) throws Exception {
		if (requestEvent != null) {
			Object service =  getBeanObj(requestEvent.getBeanId());
			if (service == null) {
				throw new Exception("没有获取名字为 " + requestEvent.getBeanId() + " 的业务逻辑类");
			}
			Class[] paramType = new Class[] { RequestEvent.class };
			Object[] param = new Object[] { requestEvent };
			Method mthd = service.getClass().getMethod(
					requestEvent.getCallMethodName(), paramType);
			return (ResponseEvent) mthd.invoke(service, param);
		}
		return null;
	}

	public static Object getBeanObj(final String beanId) {
		try {
			Object obj = null;
			if(SpringContextUtil.getApplicationContext() != null){
				obj = (Object) SpringContextUtil.getBean(beanId);
			}else{
				if(context == null){
					context = new ClassPathXmlApplicationContext(
							"applicationContext*.xml");
					context.start();
				}
				obj = (Object) context.getBean(beanId);
			}
			return obj;
		} catch (Exception ex) {
			logger.error("###[Error] LocalServiceLocator getBeanObj Exception ", ex);
		}
		return null;
	}
	
	public static Object getDsBeanObj(final String beanId) {
		try {
			Object obj = null;
			context = new ClassPathXmlApplicationContext("spring/applicationContext_db.xml");
			context.start();
			obj = (Object) context.getBean(beanId);
			return obj;
		} catch (Exception ex) {
			logger.error("###[Error] LocalServiceLocator getDsBeanObj Exception ", ex);
		}
		return null;
	}

}
