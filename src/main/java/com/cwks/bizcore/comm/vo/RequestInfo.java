package com.cwks.bizcore.comm.vo;

import java.io.Serializable;
import java.util.HashMap;

import com.cwks.common.api.dto.ext.RequestEvent;

/**
 * <p>Title:RequestInfo</p>
 * <p>Description:webService接口请求bean</p>
 * <p>Copyright: 2019</p>
 * <p>Company:cwks</p>
 * @author H.R
 * @version 1.0 
 */
public class RequestInfo implements Serializable {
	private static final long serialVersionUID = -1L;
	private String reqUrl;

	private String SERVICE_NAME;
	private long REQUEST_TIMEOUT;
	private String REQUEST_USER;
	private String REQUEST_PASSWORD;
	private RequestEvent reqevn;
	
	public String getReqUrl() {
		return reqUrl;
	}

	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}

	public RequestEvent getReqevn() {
		return reqevn;
	}

	public void setReqevn(RequestEvent reqevn) {
		this.reqevn = reqevn;
	}

	private HashMap reqMap = new HashMap();
	
	public String getREQUEST_USER() {
		return REQUEST_USER;
	}

	public void setREQUEST_USER(String rEQUEST_USER) {
		REQUEST_USER = rEQUEST_USER;
	}

	public String getREQUEST_PASSWORD() {
		return REQUEST_PASSWORD;
	}

	public void setREQUEST_PASSWORD(String rEQUEST_PASSWORD) {
		REQUEST_PASSWORD = rEQUEST_PASSWORD;
	}

	public long getREQUEST_TIMEOUT() {
		return REQUEST_TIMEOUT;
	}

	public void setREQUEST_TIMEOUT(long rEQUEST_TIMEOUT) {
		REQUEST_TIMEOUT = rEQUEST_TIMEOUT;
	}

	public String getSERVICE_NAME() {
		return SERVICE_NAME;
	}

	public void setSERVICE_NAME(String sERVICE_NAME) {
		SERVICE_NAME = sERVICE_NAME;
	}

	public HashMap getReqMap() {
		return reqMap;
	}

	public void setReqMap(HashMap reqMap) {
		this.reqMap = reqMap;
	}
}