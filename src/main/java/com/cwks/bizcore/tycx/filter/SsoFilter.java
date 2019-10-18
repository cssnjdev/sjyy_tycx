package com.cwks.bizcore.tycx.filter;

import javax.servlet.*;
import java.io.IOException;

//@WebFilter(urlPatterns = "/*",filterName = "ssoFilter")
public class SsoFilter implements Filter {

	private FilterConfig filterConfig;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		return;
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		//SsoFilterHelper.doFilter(servletRequest,servletResponse,filterChain,this.filterConfig);
		return;
	}

	@Override
	public void destroy() {

	}

}
