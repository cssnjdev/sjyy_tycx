package com.cwks.bizcore.comm.vo;

import java.io.Serializable;
import java.util.HashMap;

import com.cwks.common.api.dto.ext.ResponseEvent;

/**
 * <p>Title:ResponseInfo</p>
 * <p>Description:webService接口响应bean</p>
 * <p>Copyright: 2019</p>
 * <p>Company:cwks</p>
 * @author H.R
 * @version 1.0 
 */
public class ResponseInfo implements Serializable {
	private static final long serialVersionUID = -1L;
	private boolean issuccess;
	private String message;
	private String responseXML;
	private ResponseEvent resevn;
	private HashMap resMap = new HashMap();
	
	public ResponseEvent getResevn() {
		return resevn;
	}

	public void setResevn(ResponseEvent resevn) {
		this.resevn = resevn;
	}
	
	public String getResponseXML() {
		return responseXML;
	}

	public void setResponseXML(String responseXML) {
		this.responseXML = responseXML;
	}

	public boolean isIssuccess() {
		return issuccess;
	}

	public void setIssuccess(boolean issuccess) {
		this.issuccess = issuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HashMap getResMap() {
		return this.resMap;
	}

	public void setResMap(HashMap resMap) {
		this.resMap = resMap;
	}
}