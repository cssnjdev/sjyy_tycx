package com.cwks.bizcore.log;

import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.common.core.systemConfig.BizContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>File: JhLogWritter.java</p>
 * <p>Title: JhLogWritter</p>
 * <p>Description: 数据交换日志记录器-格式化存储，ELK监控</p>
 *
 * @author 胡锐
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
@Component
@Scope("prototype")
public class JhLogWritter {
    private static Logger jh_systemLogger = null;
    private static Logger jh_businessLogger = null;
    private static Logger jh_remoteLogger = null;
    private static String remote_open = BizContext.singleton().getValueAsString("biz.sjjhpt.core.log.recorde.remote.open");
    private static String local_open = BizContext.singleton().getValueAsString("biz.sjjhpt.core.log.recorde.local.open");
    private static String app_name = BizContext.singleton().getValueAsString("biz.sjjhpt.qzfw.core.name");
    private JhLogEvent logEvent = null;

    static {
        jh_systemLogger = LogManager.getLogger("jh_system");
        jh_businessLogger = LogManager.getLogger("jh_business");
        jh_remoteLogger = LogManager.getLogger("jh_remote");
    }

    public  void setSessionInfo(JhLogEvent event) {
        logEvent = event;
    }

    public JhLogWritter() {
    }

    public void sysDebug(String message) {
        String msg = message;
        if(logEvent != null){
            logEvent.setJh_app(app_name);
            logEvent.setJh_level("debug");
            logEvent.setJh_group("system");
            logEvent.setJh_msg(message);
            logEvent.setJh_errorMsg("");
            logEvent.setJh_time(new Date());
            msg = JsonUtil.toJson(logEvent);
        }
        if("true".equals(local_open)){
            jh_systemLogger.debug(msg);
        }
        if("true".equals(remote_open)){
            jh_remoteLogger.debug(msg);
        }
    }

    public void sysInfo(String message) {
        String msg = message;
        if(logEvent != null){
            logEvent.setJh_app(app_name);
            logEvent.setJh_level("info");
            logEvent.setJh_group("system");
            logEvent.setJh_msg(message);
            logEvent.setJh_errorMsg("");
            logEvent.setJh_time(new Date());
            msg = JsonUtil.toJson(logEvent);
        }
        if("true".equals(local_open)){
            jh_systemLogger.info(msg);
        }
        if("true".equals(remote_open)){
            jh_remoteLogger.info(msg);
        }
    }

    public void sysError(String message, Throwable exception) {
        String msg = message;
        if(logEvent != null){
            logEvent.setJh_app(app_name);
            logEvent.setJh_level("error");
            logEvent.setJh_group("system");
            logEvent.setJh_msg(message);
            logEvent.setJh_errorMsg(exception.getMessage());
            logEvent.setJh_time(new Date());
            msg = JsonUtil.toJson(logEvent);
        }
        if("true".equals(local_open)){
            jh_systemLogger.error(msg, exception);
        }
        if("true".equals(remote_open)){
            jh_remoteLogger.error(msg);
        }
    }

    public void sysFatal(String message, Throwable exception) {
        String msg = message;
        if(logEvent != null){
            logEvent.setJh_app(app_name);
            logEvent.setJh_level("fatal");
            logEvent.setJh_group("system");
            logEvent.setJh_msg(message);
            logEvent.setJh_errorMsg(exception.getMessage());
            logEvent.setJh_time(new Date());
            msg = JsonUtil.toJson(logEvent);
        }
        if("true".equals(local_open)){
            jh_systemLogger.fatal(msg, exception);
        }
        if("true".equals(remote_open)){
            jh_remoteLogger.fatal(msg);
        }
    }

    public void sysWarn(String message) {
        String msg = message;
        if(logEvent != null){
            logEvent.setJh_app(app_name);
            logEvent.setJh_level("warn");
            logEvent.setJh_group("system");
            logEvent.setJh_msg(message);
            logEvent.setJh_errorMsg("");
            logEvent.setJh_time(new Date());
            msg = JsonUtil.toJson(logEvent);
        }
        if("true".equals(local_open)){
            jh_systemLogger.warn(msg);
        }
        if("true".equals(remote_open)){
            jh_remoteLogger.warn(msg);
        }
    }

    public void bizDebug(String message) {
        String msg = message;
        if(logEvent != null){
            logEvent.setJh_app(app_name);
            logEvent.setJh_level("debug");
            logEvent.setJh_group("business");
            logEvent.setJh_msg(message);
            logEvent.setJh_errorMsg("");
            logEvent.setJh_time(new Date());
            msg = JsonUtil.toJson(logEvent);
        }
        if("true".equals(local_open)){
            jh_businessLogger.debug(msg);
        }
        if("true".equals(remote_open)){
            jh_remoteLogger.debug(msg);
        }
    }

    public void bizInfo(String message) {
        String msg = message;
        if(logEvent != null){
            logEvent.setJh_app(app_name);
            logEvent.setJh_level("info");
            logEvent.setJh_group("business");
            logEvent.setJh_msg(message);
            logEvent.setJh_errorMsg("");
            logEvent.setJh_time(new Date());
            msg = JsonUtil.toJson(logEvent);
        }
        if("true".equals(local_open)){
            jh_businessLogger.info(msg);
        }
        if("true".equals(remote_open)){
            jh_remoteLogger.info(msg);
        }
    }

    public void bizError(String message, Throwable exception) {
        String msg = message;
        if(logEvent != null){
            logEvent.setJh_app(app_name);
            logEvent.setJh_level("error");
            logEvent.setJh_group("business");
            logEvent.setJh_msg(message);
            logEvent.setJh_errorMsg(exception.getMessage());
            logEvent.setJh_time(new Date());
            msg = JsonUtil.toJson(logEvent);
        }
        if("true".equals(local_open)){
            jh_businessLogger.error(msg, exception);
        }
        if("true".equals(remote_open)){
            jh_remoteLogger.error(msg);
        }
    }

    public void bizFatal(String message, Throwable exception) {
        String msg = message;
        if(logEvent != null){
            logEvent.setJh_app(app_name);
            logEvent.setJh_level("fatal");
            logEvent.setJh_group("business");
            logEvent.setJh_msg(message);
            logEvent.setJh_errorMsg(exception.getMessage());
            logEvent.setJh_time(new Date());
            msg = JsonUtil.toJson(logEvent);
        }
        if("true".equals(local_open)){
            jh_businessLogger.fatal(msg, exception);
        }
        if("true".equals(remote_open)){
            jh_remoteLogger.fatal(msg);
        }
    }

    public void bizWarn(String message) {
        String msg = message;
        if(logEvent != null){
            logEvent.setJh_app(app_name);
            logEvent.setJh_level("fatal");
            logEvent.setJh_group("business");
            logEvent.setJh_msg(message);
            logEvent.setJh_errorMsg("");
            logEvent.setJh_time(new Date());
            msg = JsonUtil.toJson(logEvent);
        }
        if("true".equals(local_open)){
            jh_businessLogger.warn(msg);
        }
        if("true".equals(remote_open)){
            jh_remoteLogger.warn(msg);
        }
    }

    public static void main(String[] args) {
//        sysDebug("System Debug Information.");
//        sysDebug((String) null);
//        sysInfo("System Info Information.");
//        sysInfo((String) null);
//        bizInfo("Business Info");
//        bizInfo((String) null);
    }
}