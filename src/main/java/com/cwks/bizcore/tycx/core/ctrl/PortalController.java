package com.cwks.bizcore.tycx.core.ctrl;

import com.cwks.bizcore.tycx.core.service.PortalService;
import com.cwks.common.api.dto.ext.UserContext;
import com.cwks.common.core.systemConfig.BizContext;
import com.cwks.common.core.systemConfig.SystemApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


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
@RequestMapping("/portal")
public class PortalController{
	private static Logger logger = LoggerFactory.getLogger(PortalController.class);
	private UserContext ctx;

	@Autowired
	private PortalService portalService;


	/**
	 * <p>url: /portal/loginOut.action</p>
	 * <p>Description:用户注销登出门户</p>
	 * @version 1.0
	 */
	@RequestMapping(value = "/loginOut", method = {RequestMethod.POST, RequestMethod.GET})
	public String loginOut(HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
		String encode = (String) SystemApplicationContext.singleton().getValueAsString("ctp.web.json.encode");
		response.setCharacterEncoding(encode);
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		//SsoPortalHelper.portalLogout(request, response);
		session.invalidate();
		String timstr = String.valueOf(System.currentTimeMillis());
		String language = BizContext.singleton().getValueAsString("biz.cssnjworks.biz.core.local.language");
		response.sendRedirect(basePath+"?locale="+language+"&time="+timstr);
		return null;
	}

	/**
	 * <p>url: /model</p>
	 * <p>Description:系统加载gis模块页面</p>
	 * @version 1.0
	 */

//	/tgycx/model?p=cx_xsss
	@RequestMapping("/model")
	public String model(HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
		String showModel = BizContext.singleton().getValueAsString("biz.cssnjworks.biz.core.portal.theme");
		String path="biz/bizcore/portal/model/"+showModel+"/";
		if(request.getParameter("p") != null && !"".equals(request.getParameter("p"))){
			path= path+request.getParameter("p");
		}
		//request.getRequestDispatcher(path).forward(request, response);
		return path;
	}

}
