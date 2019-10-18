package com.cwks.bizcore.filter;



import cn.org.cssnj.centralportal.sso.client.SsoFilterHelper;
import com.cwks.bizcore.comm.utils.MyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
@WebFilter(filterName = "SsoFilter",urlPatterns = "/*")
public class SsoFilter implements Filter {

	@Autowired
	private Environment environment;
	@Autowired
	MyProperties myProperties;
	private FilterConfig filterConfig;
	@Autowired
	private DevLoginService devLoginService;
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("ssoFilter>>>>>>>>");
		this.filterConfig = filterConfig;
		return;
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		if(myProperties.isDev()){
			devLoginService.login(servletRequest,servletResponse,filterChain);
			return;
		}else {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
			String requestURI = request.getRequestURI();
			String queryStr = request.getQueryString();
			if ((queryStr != null && queryStr.contains("ssoFlag=portal")) || requestURI.contains("/portalIndex") || requestURI.contains("/tykf") || requestURI.contains("REDIRECT")) {
			SsoFilterHelper.doFilter(servletRequest,servletResponse,filterChain,this.filterConfig);
		}else{
			filterChain.doFilter(servletRequest,servletResponse);
			return;
		}
		}
	}

	@Override
	public void destroy() {

	}

}
