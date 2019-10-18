package com.cwks.bizcore.filter;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cwks.common.api.dto.ext.UserContext;
import com.google.gson.Gson;

import cn.org.cssnj.centralportal.sso.client.DisposeAppSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p>
 * Description: SSO单点登录接口实现
 * </p>
 * 
 * @author H.R
 */
public class SsoLoginFilterImp implements DisposeAppSession {
	private static final Logger logger = LoggerFactory.getLogger(SsoLoginFilterImp.class);

	public void doUserLogon(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			String userJsonstr) throws Exception {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(true);
		logger.debug("执行sso方法，SSO登录========传入用户信息的json:" + userJsonstr);
		if (session != null) {
			UserContext ctx = null;
			if(session.getAttribute("UserContext") != null){
				ctx = (UserContext)session.getAttribute("UserContext");
			}else{
				this.doSetUserInfoSession(userJsonstr,request, httpResponse);
			}
			if (ctx == null) {
				this.doSetUserInfoSession(userJsonstr,request, httpResponse);
			}
		}
		if (session == null) {
			httpResponse.sendRedirect("404/timeout.jsp");
		}
	}

	public boolean isAuthenticated(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) {
		boolean res = false;
		HttpSession session = request.getSession(true);
		Object obj = session.getAttribute("UserContext");
		if (session != null && obj != null) {
			res = true;
			logger.debug("======调用  HPsso,当前会话存在用户对象===" + obj);
		}
		return res;
	}

	@SuppressWarnings("rawtypes")
	public void doSetUserInfoSession(String userJsonstr ,HttpServletRequest req,
			HttpServletResponse response) {
		HttpSession session = req.getSession(true);
		UserContext ctx=new UserContext();
		Gson gson = new Gson();
		Map jsonMap = gson.fromJson(userJsonstr,Map.class);
		String authUserInfo=(String) jsonMap.get("authUserInfo");
		Map authUserInfoMap = gson.fromJson(authUserInfo,Map.class);
		ctx.setUserinfo(authUserInfoMap);
		session.setAttribute("UserContext", ctx);
	}
}