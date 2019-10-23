package com.cwks.bizcore.comm.controller;



import com.cwks.bizcore.comm.utils.CommUtil;
import com.cwks.bizcore.comm.utils.JsonUtil;

import com.cwks.bizcore.tycx.core.utils.CusAccessObjectUtil;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.RequestUtils;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.api.dto.ext.UserContext;
import com.cwks.common.core.exception.BizException;
import com.cwks.common.core.systemConfig.BizErrorMsgContext;
import com.cwks.common.core.systemConfig.SystemApplicationContext;
import com.cwks.common.util.SpringContextUtils;
import com.cwks.core.util.OfficeDownloadUtil;
import com.cwks.delegate.ServiceDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * 核心Controller
 * <p>Title: TykfptController.java</p>
 * <p>Description:TykfptController</p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: cwks</p>
 * @author cssnj123
 * @version 1.0
 */
@Controller
@RequestMapping("/tykf")
public class TykfptController {

	private static Logger logger = LoggerFactory.getLogger(TykfptController.class);
	private UserContext ctx;

	@RequestMapping(value = "/request", method = {RequestMethod.POST, RequestMethod.GET})
	public void request_ajax(HttpServletRequest request,
							 HttpServletResponse response) throws Exception {
		String encode = (String) SystemApplicationContext.singleton().getValueAsString("ctp.web.json.encode");
		response.setCharacterEncoding(encode);
		response.setContentType("text/html;charset=UTF-8");
		Map<String, Object> statuts = new HashMap<String, Object>();
		// 判断是否存在用户会话

		HttpSession session = request.getSession();
		ctx = (UserContext) session.getAttribute("UserContext");
		ResponseEvent resEvent = new ResponseEvent();
		RequestEvent req = new RequestEvent();
		String tld = request.getParameter("tld");
		req.setHandleCode("init");
		if (tld == null || "".equals(tld)) {
			statuts.put("messageCode","-1");
			statuts.put("message","no biz [tld] request param.");
			JsonUtil.toWriter(statuts, response);
			return;
		}
		String BeanID = tld.substring(0,tld.indexOf("_"));
		req.setHandleCode(tld.substring(tld.indexOf("_")+1));
		req.setBeanId(BeanID);
		req.setRequestMap(RequestUtils.requestToMap(request));
		req.setCtx(ctx);
		//req.setRequest(request);
		String errmsg = "";
		PrintWriter printWriter = null;
		printWriter = response.getWriter();
		try{
			Object obj = SpringContextUtils.getBean(BeanID);
			// Object obj = Class.forName("com.rw.article.service.pay.impl.WithdrawalsServiceProxyImpl").newInstance();
//			logger.info("obj [ {} ] " ,obj);
			Object resobj = obj.getClass().getDeclaredMethod(req.getHandleCode(), RequestEvent.class).invoke(obj,req);
			if(resobj != null){
				resEvent = (ResponseEvent)resobj;
			}
		}catch(Exception e){
			logger.error("resEvent error:", e);
			statuts.put("errmsg",e.getMessage());
			statuts.put("messageCode","-1");
			statuts.put("message",e.getMessage());
			printWriter.print(JsonUtil.toJson(statuts));
			printWriter.flush();
			printWriter.close();
			return;
		}
		try {
			if(resEvent == null || resEvent.getResMap() == null || resEvent.getResMap().get("JSONDATA")==null || "".equals(resEvent.getResMap().get("JSONDATA"))){
				statuts.put("messageCode","-1");
				statuts.put("message","error no return data.");
				printWriter.print(JsonUtil.toJson(statuts));
			}else{
				printWriter.print(resEvent.getResMap().get("JSONDATA").toString());
			}
			printWriter.flush();
			printWriter.close();
			return;
		} catch (Exception e) {
			logger.error("ajax error:", e);
			statuts.put("messageCode","-1");
			statuts.put("message",e.getMessage());
			printWriter.print(JsonUtil.toJson(statuts));
			printWriter.flush();
			printWriter.close();
			return;
		}
	}


	@RequestMapping(value = "/request_http", method = {RequestMethod.POST, RequestMethod.GET})
	public String http(HttpServletRequest request,
					   HttpServletResponse response) throws Exception {
		String encode = (String) SystemApplicationContext.singleton().getValueAsString("ctp.web.json.encode");
		response.setCharacterEncoding(encode);
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		Map<String, Object> statuts = new HashMap<String, Object>();
		ctx = (UserContext) request.getSession().getAttribute("UserContext");

		ResponseEvent resEvent = new ResponseEvent();
		RequestEvent req = new RequestEvent();
		String tld = request.getParameter("tld");
		req.setHandleCode("init");
		if (tld == null || "".equals(tld)) {
			throw new Exception("没有找到请求方法参数.");
		}
		String BeanID = tld.substring(0,tld.indexOf("_"));
		req.setHandleCode(tld.substring(tld.indexOf("_")+1));
		req.setBeanId(BeanID);
		req.setRequestMap(RequestUtils.requestToMap(request));
		req.setCtx(ctx);
		//req.setRequest(request);
		String errmsg = "";
		PrintWriter printWriter = null;
		printWriter = response.getWriter();
		try{
			Object obj = SpringContextUtils.getBean(BeanID);
			// Object obj = Class.forName("com.rw.article.service.pay.impl.WithdrawalsServiceProxyImpl").newInstance();
//			logger.info("obj [ {} ] " ,obj);
			Object resobj = obj.getClass().getDeclaredMethod(req.getHandleCode(), RequestEvent.class).invoke(obj,req);
			if(resobj != null){
				resEvent = (ResponseEvent)resobj;
				if(resEvent != null && resEvent.getResMap() != null){
					Map map = resEvent.getResMap();
					Set set = map.keySet();
					String key = "";
					Object value = null;
					for (Iterator it = set.iterator(); it.hasNext();) {
						key = (String) it.next();
						value = map.get(key);
						request.setAttribute(key,value);
					}
				}
			}
		}catch(Exception e){
			logger.error("请求异常:",e);
			errmsg = e.getMessage();
			errmsg = errmsg.replace("\n","&&");
			throw new Exception(errmsg);
		}
		Map map = resEvent.getResMap();
		Set set = map.keySet();
		String key = "";
		Object value = null;
		for (Iterator it = set.iterator(); it.hasNext();) {
			key = (String) it.next();
			value = map.get(key);
			request.setAttribute(key,value);
		}
		if (resEvent == null) {
			throw new Exception("返回结果为空。");
		}
		if (resEvent.getFwordPath() == null) {
			throw new Exception("获取跳转路径为空。");
		}
		return resEvent.getFwordPath();
	}
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportExcle", method = {RequestMethod.POST, RequestMethod.GET})
	public void ExportExcle(HttpServletRequest request,
							HttpServletResponse response) throws Exception {
		String encode = (String) SystemApplicationContext.singleton().getValueAsString("ctp.web.json.encode");
		response.setCharacterEncoding(encode);
		response.setContentType("text/html;charset=UTF-8");
		String path = request.getContextPath();
		//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		Map<String, Object> statuts = new HashMap<String, Object>();
		// 判断是否存在用户会话
		ctx = (UserContext) request.getSession().getAttribute("UserContext");

		//=====================跳转Service方法======================
		ResponseEvent resEvent = new ResponseEvent();
		RequestEvent req = new RequestEvent();
		String tld = request.getParameter("tld");
		String tld_tx = request.getParameter("tld_tx");// 事务属性
		req.setHandleCode("init");
		if (tld == null || "".equals(tld)) {
			statuts.put("messageCode","-1");
			statuts.put("message","no biz [tld] request param.");
			JsonUtil.toWriter(statuts, response);
			return;
		}else {
			if ("longtx".equals(tld_tx)) {
				req.setCallMethodName("performTaskLongTx");
			} else if ("notx".equals(tld_tx)) {
				req.setCallMethodName("performTaskNoTx");
			} else {
				req.setCallMethodName("performTask");
			}
		}
		String BeanID = tld.substring(0,tld.indexOf("_"));
		req.setHandleCode(tld.substring(tld.indexOf("_")+1));
		req.setBeanId(BeanID);
		req.setRequestMap(RequestUtils.requestToMap(request));
		req.setCtx(ctx);
		//req.setRequest(request);
		try{
			resEvent = ServiceDelegate.delegate(req);
		}catch(BizException ex){
//			LoggerFactory.("CssnjController ExportExcle biz error:",ex);
			String errmsg = ex.getMessage();
			errmsg = errmsg.replace("\n","&&");
			Object obj = BizErrorMsgContext.singleton().getValueAsString(errmsg);
			if(obj != null && !"".equals(obj)){
				errmsg = (String)obj;
			}
			statuts.put("errmsg",errmsg);
			statuts.put("messageCode","-1");
			statuts.put("message",errmsg);
		}catch(Exception e){
//			logger.error("CssnjController ExportExcle error:",e);
			statuts.put("errmsg",e.getMessage());
			statuts.put("messageCode","-1");
			statuts.put("message",e.getMessage());
		}
		if (resEvent == null) {
			statuts.put("messageCode","-1");
			statuts.put("message","res null");
			JsonUtil.toWriter(statuts, response);
			return;
		}
		//==================================================================================================================
		//获取map的名称：ExcelMap=导出Excel文件    ExcelPath=下载Excel文件
		String MapName = resEvent.getResMap().keySet().toString().replace("[","").replace("]","");
		Map Excel = (Map) resEvent.getResMap();
		/*
		执行导出excel方法
		 */
		try {
			OfficeDownloadUtil officeDownloadUtil = new OfficeDownloadUtil();
			officeDownloadUtil.export2Excle(MapName,Excel,request,response);
		} catch (IOException e) {
//			logger.error("CssnjController ExportExcle error:",e);
		}
	}

	public static ResponseEvent delegate(HttpServletRequest request, UserContext ctx) throws Exception{
		RequestEvent req = new RequestEvent();
		String tld = request.getParameter("tld");
		String tld_tx = request.getParameter("tld_tx");// 事务属性
		if(request.getAttribute("tld") != null && !"".equals((String)request.getAttribute("tld"))){
			tld = (String)request.getAttribute("tld");
		}
		if(request.getAttribute("tld_tx") != null && !"".equals((String)request.getAttribute("tld_tx"))){
			tld_tx = (String)request.getAttribute("tld_tx");
		}
		req.setHandleCode("init");
		if (tld == null || "".equals(tld)) {
			logger.error("no biz [tld] request param.");
			throw new Exception("no biz [tld] request param.");
		}else {
			if ("longtx".equals(tld_tx)) {
				req.setCallMethodName("performTaskLongTx");
			} else if ("notx".equals(tld_tx)) {
				req.setCallMethodName("performTaskNoTx");
			} else {
				req.setCallMethodName("performTask");
			}
		}
		ResponseEvent resEvent = new ResponseEvent();
		String BeanID = tld.substring(0,tld.indexOf("_"));
		req.setHandleCode(tld.substring(tld.indexOf("_")+1));
		req.setBeanId(BeanID);
		req.setRequestMap(RequestUtils.requestToMap(request));
		req.setCtx(ctx);
		//req.setRequest(request);
		Object obj = SpringContextUtils.getBean(BeanID);
		// Object obj = Class.forName("com.rw.article.service.pay.impl.WithdrawalsServiceProxyImpl").newInstance();
//		logger.info("obj [ {} ] " ,obj);
		Object resobj = obj.getClass().getDeclaredMethod(req.getHandleCode(), RequestEvent.class).invoke(obj,req);
		if(resobj != null){
			resEvent = (ResponseEvent)resobj;
		}
		return resEvent;
	}


	public static ResponseEvent delegate(String tld, String tld_tx, HashMap<?, ?> reqmap, UserContext ctx) throws Exception{
		RequestEvent req = new RequestEvent();
		req.setHandleCode("init");
		if (tld == null || "".equals(tld)) {
			throw new Exception("no biz [tld] request param.");
		}else {
			if ("longtx".equals(tld_tx)) {
				req.setCallMethodName("performTaskLongTx");
			} else if ("notx".equals(tld_tx)) {
				req.setCallMethodName("performTaskNoTx");
			} else {
				req.setCallMethodName("performTask");
			}
		}
		ResponseEvent resEvent = new ResponseEvent();
		String BeanID = tld.substring(0,tld.indexOf("_"));
		req.setHandleCode(tld.substring(tld.indexOf("_")+1));
		req.setBeanId(BeanID);
		req.setRequestMap(reqmap);
		req.setCtx(ctx);
		Object obj = SpringContextUtils.getBean(BeanID);
		// Object obj = Class.forName("com.rw.article.service.pay.impl.WithdrawalsServiceProxyImpl").newInstance();
//		logger.info("obj [ {} ] " ,obj);
		Object resobj = obj.getClass().getDeclaredMethod(req.getHandleCode(), RequestEvent.class).invoke(obj,req);
		if(resobj != null){
			resEvent = (ResponseEvent)resobj;
		}
		return resEvent;
	}


	/*public static ResponseEvent remoteHttpDelegate(String remoteKey,long timeout,String tld,String tld_tx, HashMap<?, ?> reqmap,UserContext ctx) throws Exception{
		RequestEvent req = new RequestEvent();
		req.setHandleCode("init");
		if (tld == null || "".equals(tld)) {
			throw new Exception("no biz [tld] request param.");
		}else {
			if ("longtx".equals(tld_tx)) {
				req.setCallMethodName("performTaskLongTx");
			} else if ("notx".equals(tld_tx)) {
				req.setCallMethodName("performTaskNoTx");
			} else {
				req.setCallMethodName("performTask");
			}
		}
		String BeanID = tld.substring(0,tld.indexOf("_"));
		req.setHandleCode(tld.substring(tld.indexOf("_")+1));
		req.setBeanId(BeanID);
		req.setRequestMap(reqmap);
		req.setCtx(ctx);
		RequestInfo reqInfo = new RequestInfo();
		reqInfo.setREQUEST_TIMEOUT(timeout);//10分钟 此处不设置则为1分钟
		reqInfo.setREQUEST_USER("cssnjworks");
		reqInfo.setREQUEST_PASSWORD("cssnj");
		reqInfo.setReqevn(req);
		ResponseInfo resInfo = HessianSeriviceHelper.singleton().invokeTask(remoteKey,reqInfo);
		ResponseEvent resEvent = new ResponseEvent();
		resEvent = resInfo.getResevn();
		return resEvent;
	}*/

}
