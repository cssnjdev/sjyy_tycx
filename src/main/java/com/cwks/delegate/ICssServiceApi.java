package com.cwks.delegate;

import com.cwks.bizcore.comm.vo.RequestInfo;
import com.cwks.bizcore.comm.vo.ResponseInfo;

/**
 * <p>Title:ICssServiceApi</p>
 * <p>Description:中软 webService接口</p>
 * <p>Copyright: 2017</p>
 * <p>Company:cssnj</p>
 * @author 胡锐
 * @version 1.0 
 */
public interface ICssServiceApi {
	public ResponseInfo invokeTask(RequestInfo req)throws Exception;
}
