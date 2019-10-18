package com.cwks.bizcore.sys.xtcs.service;

import com.cwks.bizcore.daoUtil.CssnjDao;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("XtcsUtlService")
public class XtcsUtlService  {

	@Autowired
    private CssnjDao jdbcDao;
	
	public ResponseEvent setNull(RequestEvent requestEvent) {
		jdbcDao.setXtcsInfoNull();
		return null;
	}

}
