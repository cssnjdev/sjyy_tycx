package com.cwks.delegate;

import java.io.Serializable;

/**
 * <p>Title: RequestServerInfoVo</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017 cssnj</p>
 * <p>Company: cssnj</p>
 * @author 胡锐
 * @version 1.0
 */
public class RequestServerInfoVo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String key = "";
	private String name = "";
	private String url = "";
	private String timeout = "";
	private String username = "";
	private String password = "";

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

}
