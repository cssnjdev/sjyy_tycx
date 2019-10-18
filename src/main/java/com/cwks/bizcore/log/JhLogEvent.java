package com.cwks.bizcore.log;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
@Scope("prototype")
public class JhLogEvent implements Serializable {
    private static final long serialVersionUID = 2093331683336500554L;

    /*交易流水ID  accessClientId+uuid*/
    protected String jh_transactionId;
    /*调用资源ID*/
    protected String jh_resourceId;
    /*log生成时间*/
    protected Date jh_time;
    /*log等级*/
    protected String jh_level;
    /*信息简要*/
    protected String jh_msg;
    /*应用名称*/
    protected String jh_app;
    /*日志一级分类*/
    protected String jh_group;
    /*日志二级分类*/
    protected String jh_object;
    /*应用主机地址*/
    protected String jh_host;
    /*状态码*/
    protected String jh_status;
    /*执行毫秒*/
    protected Long jh_elapsed;
    /*错误的完整信息*/
    protected String jh_errorMsg;
    /*调用服务的厂商ID*/
    protected String jh_accessClientId;
    /*调用服务的厂商应用IP*/
    protected String jh_accessClientIp;

    public String getJh_transactionId() {
        return jh_transactionId;
    }

    public void setJh_transactionId(String jh_transactionId) {
        this.jh_transactionId = jh_transactionId;
    }

    public String getJh_resourceId() {
        return jh_resourceId;
    }

    public void setJh_resourceId(String jh_resourceId) {
        this.jh_resourceId = jh_resourceId;
    }

    public Date getJh_time() {
        return jh_time;
    }

    public void setJh_time(Date jh_time) {
        this.jh_time = jh_time;
    }

    public String getJh_level() {
        return jh_level;
    }

    public void setJh_level(String jh_level) {
        this.jh_level = jh_level;
    }

    public String getJh_msg() {
        return jh_msg;
    }

    public void setJh_msg(String jh_msg) {
        this.jh_msg = jh_msg;
    }

    public String getJh_app() {
        return jh_app;
    }

    public void setJh_app(String jh_app) {
        this.jh_app = jh_app;
    }

    public String getJh_group() {
        return jh_group;
    }

    public void setJh_group(String jh_group) {
        this.jh_group = jh_group;
    }

    public String getJh_object() {
        return jh_object;
    }

    public void setJh_object(String jh_object) {
        this.jh_object = jh_object;
    }

    public String getJh_host() {
        return jh_host;
    }

    public void setJh_host(String jh_host) {
        this.jh_host = jh_host;
    }

    public String getJh_status() {
        return jh_status;
    }

    public void setJh_status(String jh_status) {
        this.jh_status = jh_status;
    }

    public Long getJh_elapsed() {
        return jh_elapsed;
    }

    public void setJh_elapsed(Long jh_elapsed) {
        this.jh_elapsed = jh_elapsed;
    }

    public String getJh_errorMsg() {
        return jh_errorMsg;
    }

    public void setJh_errorMsg(String jh_errorMsg) {
        this.jh_errorMsg = jh_errorMsg;
    }

    public String getJh_accessClientId() {
        return jh_accessClientId;
    }

    public void setJh_accessClientId(String jh_accessClientId) {
        this.jh_accessClientId = jh_accessClientId;
    }

    public String getJh_accessClientIp() {
        return jh_accessClientIp;
    }

    public void setJh_accessClientIp(String jh_accessClientIp) {
        this.jh_accessClientIp = jh_accessClientIp;
    }
}
