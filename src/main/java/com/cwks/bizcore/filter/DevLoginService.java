package com.cwks.bizcore.filter;

import com.cwks.bizcore.comm.controller.TykfptController;
import com.cwks.bizcore.comm.utils.MyProperties;
import com.cwks.common.api.dto.ext.RequestUtils;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.api.dto.ext.UserContext;
import com.cwks.common.dao.JdbcDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
@Service
public class DevLoginService {
    private static Logger logger = LoggerFactory.getLogger(DevLoginService.class);
    @Autowired
    MyProperties myProperties;
    private boolean enableDefDev = true;
    private String nofilterpath = "";
    @Autowired
    JdbcDao jdbcDao;
    public void login(ServletRequest request, ServletResponse response,
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

                UserContext ctx = (UserContext) session.getAttribute("UserContext");
                if(ctx==null||"".equals(ctx)){
                    UserContext userContext1 = new UserContext();
                    String swry_dm = req.getParameter("swry_dm");
                    String swrysf_dm = req.getParameter("swrysf_dm");
                    if(swry_dm==null||swrysf_dm==null){
                        swry_dm=myProperties.getSwry_dm();
                        swrysf_dm=myProperties.getSwrysf_dm();
                    }
                    String sql = jdbcDao.getSql("SQL_SYS_LOGIN_INFO_BY_RYSFDM");
                    ArrayList param = new ArrayList();
                    param.add(swry_dm);
                    param.add(swrysf_dm);
                    Map map1 = jdbcDao.queryformap(sql,param);
                    userContext1.setUserinfo(map1);
                    session.setAttribute("UserContext",userContext1);
                }

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
}
