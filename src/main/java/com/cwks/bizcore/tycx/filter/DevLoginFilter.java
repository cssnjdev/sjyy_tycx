package com.cwks.bizcore.tycx.filter;

import com.cwks.bizcore.comm.controller.TykfptController;
import com.cwks.common.api.dto.ext.RequestUtils;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.api.dto.ext.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>Title: DevLoginFilter.java</p>
 * <p>Description: 开发模式下直接URL传权限资源构建用户身份会话模式</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: cssnj</p>
 * @author 胡锐
 * @version 1.0
 */
//@Configuration
public class DevLoginFilter implements Filter {
	private boolean enableDefDev = true;
	private String nofilterpath = "";
	private static Logger logger = LoggerFactory.getLogger(DevLoginFilter.class);
	public void destroy() {
		logger.debug("devfilter destroy......");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
		logger.debug("执行开发模式过滤器方法，模拟登录!!");
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		request.setCharacterEncoding("UTF-8");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		if (request instanceof HttpServletRequest) {
			if (enableDefDev) {
				String curURL = req.getRequestURI();
				if(nofilterpath != null && !nofilterpath.equals("")){
					String[] urls = nofilterpath.split(",");
					String tmpurl=null;
					for(int i = 0;i<urls.length;i++){
						tmpurl = urls[i];
						if(tmpurl.indexOf("?") != -1){
							tmpurl = tmpurl.substring(0, tmpurl.indexOf("?"));
						}
						if(curURL.indexOf(tmpurl) != -1 || curURL.equals(req.getContextPath()+"/")){
							// 继续执行
							filterChain.doFilter(request, response);
							return;
						}
					}
				}
				UserContext userContext1 = new UserContext();
				Map map=new HashMap();
				map.put("swry_dm","admin");
				map.put("password","cssnj");
				map.put("swrysf_dm","admin");
				map.put("xb_dm","admin");
				map.put("rysfmc","admin");
//				map.put("sfzjhm","admin");
				map.put("usercode","admin");
				map.put("zw","admin");
				map.put("swryxm","admin");
				map.put("swrysfjg","23201000000");
				userContext1.setUserinfo(map);
				session.setAttribute("UserContext",userContext1);
				UserContext ctx = (UserContext) session.getAttribute("UserContext");
				//?p_userid=23200300212&p_sfdm=&p_gwdm=&p_znfwdm=&p_zndm=
		        String userid = req.getParameter("p_userid");
				if(userid!=null && !"".equals(userid)&&ctx!=null&&ctx.getUserinfo().get("swry_dm")!=null&&!userid.equals(ctx.getUserinfo().get("swry_dm"))){
					ctx = null;
				}
				// 判断是否存在用户会话
				if(ctx == null && userid!=null && !"".equals(userid)){
					ctx = new UserContext();
					try {
						ResponseEvent resEvent = new ResponseEvent();
						resEvent = TykfptController.delegate("toLoginService_devModeLogin","", RequestUtils.requestToMap(req),ctx);
						if (resEvent == null) {
							session.setAttribute("errmsg","request login user info error");
							httpResponse.sendRedirect(req.getContextPath()+"/sys/404/error.jsp");
							return;
						}else if("1".equals(resEvent.getResMap().get("code").toString())){
							UserContext userContext = (UserContext) resEvent.getResMap().get("userContext");
							//用户基本信息
							session.setAttribute("UserContext", userContext);
							filterChain.doFilter(request, response);
							return;
						}else {
							session.setAttribute("UserContext", null);
							httpResponse.sendRedirect(req.getContextPath()+"/sys/404/timeout.jsp");
							return;
						}
					}catch(Exception e) {
						logger.error(e.getMessage());
						session.setAttribute("errmsg",e.getMessage());
						httpResponse.sendRedirect(req.getContextPath()+"/sys/404/error.jsp");
						return;
					}
				}else{
					filterChain.doFilter(request, response);
					return;
				}
			}
		}
		filterChain.doFilter(request, response);
		return;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("devfilter  init......");
		logger.debug("devfilter  init......");
		String EnableDefaultDebug = filterConfig.getInitParameter("EnableDefaultDebug");
//		enableDefDev = EnableDefaultDebug != null && "true".equalsIgnoreCase(EnableDefaultDebug);
		enableDefDev = true;
//		nofilterpath = filterConfig.getInitParameter("nofilterpath");
		nofilterpath = "welcome.jsp,login.jsp,login.html,clogin,/sys";
	}

}