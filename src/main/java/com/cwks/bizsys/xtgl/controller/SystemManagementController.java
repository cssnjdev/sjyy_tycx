package com.cwks.bizsys.xtgl.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * PortalController
 * <p>Title: PortalController.java</p>
 * <p>Description:门户ctrl</p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: cssnj</p>
 * @author cssnj
 * @version 1.0
 */
@Controller
@RequestMapping("/systemManagement")
public class SystemManagementController {
	private static Logger logger = LoggerFactory.getLogger(SystemManagementController.class);

	/**
	 * <p>url: /model</p>
	 * <p>Description:系统加载gis模块页面</p>
	 * @version 1.0
	 */
	@RequestMapping("/gohtml")
	public String model(HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("SystemManagementController/gohtml");
		String path="/system/xtgl/";
		if(request.getParameter("p") != null && !"".equals(request.getParameter("p"))){
			path= path+request.getParameter("p");
		}
		return path;
	}
}
